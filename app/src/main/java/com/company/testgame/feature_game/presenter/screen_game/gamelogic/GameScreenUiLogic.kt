package com.company.testgame.feature_game.presenter.screen_game.gamelogic

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.NavController
import com.company.testgame.R
import com.company.testgame.databinding.GameLayoutBinding
import com.company.testgame.feature_game.presenter.screen_game.*
import com.company.testgame.util.click
import com.company.testgame.util.fadeIn
import com.company.testgame.util.fadeOut
import com.company.testgame.util.float
import com.company.testgame.util.moveDown
import com.company.testgame.util.starsFadeIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

//TODO fix images not displaying????
@SuppressLint("ClickableViewAccessibility")
class GameScreenUiLogic(
    rootActivity: ComponentActivity,
    private val binding: GameLayoutBinding,
    private val gameViewModel: GameViewModel,
    private val randomInstance: Random,
    private val navController: NavController
) {

    private val displayMetrics: DisplayMetrics = rootActivity.resources.displayMetrics
    private val layoutInflater: LayoutInflater = rootActivity.layoutInflater

    private var gameObjectsQueue = mutableListOf<GameObject>()

    init {
        binding.run {
            scoreTextView.text = gameViewModel.score.value.toString()
            livesTextView.text = gameViewModel.lives.value.toString()

            exitImageButton.setOnClickListener {
                navController.navigateUp()
            }

            moveLeftButton.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        gameViewModel.setRocketAimDirection(0)
                    }

                    MotionEvent.ACTION_UP -> {
                        gameViewModel.setRocketAimDirection(-1)
                        binding.rocketImageView.rotation = 0f
                    }
                }

                return@setOnTouchListener true
            }

            moveRightButton.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        gameViewModel.setRocketAimDirection(1)
                    }

                    MotionEvent.ACTION_UP -> {
                        gameViewModel.setRocketAimDirection(-1)
                        binding.rocketImageView.rotation = 0f
                    }
                }

                return@setOnTouchListener true
            }

            tapToPlayTextView.setOnClickListener {
                it.click()
                it.fadeOut()

                livesTextView.fadeIn()
                livesImageView.fadeIn()
                scoreTextView.fadeIn()
                scoreImageView.fadeIn()
                starsImageView.starsFadeIn()
                landingPadImageView.moveDown()

                rocketImageView.setImageResource(R.drawable.rocket_fly)

                startGame()
            }
            tapToPlayTextView.float()
        }
    }

    private fun startGame() {
        gameViewModel.startGame()
    }

    @SuppressLint("InflateParams")
    fun spawnGameObject() {
        val randomNumber = randomInstance.nextInt(0, 101)
        val view = if (randomNumber > 35) {
            layoutInflater.inflate(R.layout.obstacle_layout, null)
        } else {
            layoutInflater.inflate(R.layout.bonus_layout, null)
        }
        view.id = View.generateViewId()
        binding.rootLayout.addView(view, 0)

        val set = ConstraintSet()

        var horizontalBias = evaluateRandomBias()
        if (gameObjectsQueue.isNotEmpty()) while (gameObjectsQueue.map { it.bias }.contains(horizontalBias)) {
            horizontalBias = evaluateRandomBias()
        }

        set.clone(binding.rootLayout)
        set.connect(view.id, ConstraintSet.BOTTOM, binding.rootLayout.id, ConstraintSet.TOP, 0)
        set.connect(view.id, ConstraintSet.LEFT, binding.rootLayout.id, ConstraintSet.LEFT, 0)
        set.connect(view.id, ConstraintSet.RIGHT, binding.rootLayout.id, ConstraintSet.RIGHT, 0)
        set.setHorizontalBias(view.id, horizontalBias)
        set.applyTo(binding.rootLayout)

        gameObjectsQueue.add(if (randomNumber > 35 ) GameObject.Obstacle(
            view,
            horizontalBias
        ) else GameObject.Bonus(view, horizontalBias))
    }

    //TODO optimize
    suspend fun moveGameObjects() {
        val flyCoefficient = 15000f
        val viewSpeed = displayMetrics.heightPixels / flyCoefficient * gameViewModel.frameRate

        if (gameObjectsQueue.isNotEmpty()) {
            var i = 0
            while (i < gameObjectsQueue.size) {
                val view = gameObjectsQueue[i].view
                view.translationY += viewSpeed

                if (view.parent == null) continue

                if (binding.rocketImageView.containsWithTranslation(view)) {
                    withContext(Dispatchers.Main) {
                        if (gameObjectsQueue[i] is GameObject.Bonus) {
                            val score = gameViewModel.score.value + 1
                            gameViewModel.setScore(score)
                            binding.scoreTextView.text = score.toString()
                        } else {
                            decreaseLives()
                        }
                        destroyGameObject(i)
                    }
                } else if (view.translationY >= displayMetrics.heightPixels * 1.05 + 96 * displayMetrics.density) {
                    withContext(Dispatchers.Main) {
                        binding.rootLayout.removeView(view)
                        gameObjectsQueue = gameObjectsQueue.drop(1).toMutableList()
                    }
                }
                i++
            }
        }
    }

    fun moveRocket(rocketAimDirection: Int, rocketAimBias: Float) {
        val edgeDistance = displayMetrics.widthPixels / 2 * 0.9f
        val aimSpeed = 0.01f

        if (rocketAimDirection == 0) {
            if (rocketAimBias > -1) {
                gameViewModel.setRocketAimBias(rocketAimBias - aimSpeed)
                binding.rocketImageView.translationX = edgeDistance * rocketAimBias
                binding.rocketImageView.rotation = -18f
            }
        } else {
            if (rocketAimBias < 1) {
                gameViewModel.setRocketAimBias(rocketAimBias + aimSpeed)
                binding.rocketImageView.translationX = edgeDistance * rocketAimBias
                binding.rocketImageView.rotation = 18f
            }
        }
    }

    private fun destroyGameObject(gameObjectIndex: Int) {
        val objectView = gameObjectsQueue[gameObjectIndex].view
        binding.rootLayout.removeView(objectView)
        gameObjectsQueue.removeAt(gameObjectIndex)
    }

    private fun decreaseLives() {
        val lives = gameViewModel.lives.value - 1
        gameViewModel.setLives(lives)
        binding.livesTextView.text = lives.toString()

        if (lives <= 0) {
            gameViewModel.stopGame(navController)
        }
    }

    private fun View.containsWithTranslation(other: View): Boolean {
        val containDistanceDp = 80

        val tCenter = (this.right + this.left) / 2f + this.translationX to (this.bottom + this.top) / 2f + this.translationY
        val oCenter = (other.right + other.left) / 2f + other.translationX to (other.bottom + other.top) / 2f + other.translationY

        val distance = sqrt((tCenter.first - oCenter.first).pow(2) + (tCenter.second - oCenter.second).pow(2))
        return distance <= containDistanceDp * displayMetrics.density
    }

    private fun evaluateRandomBias(): Float {
        val biasList = listOf(0.10f, 0.26f, 0.42f, 0.58f, 0.74f, 0.9f)
        val number = (0..5).shuffled().random()
        return biasList[number]
    }
}