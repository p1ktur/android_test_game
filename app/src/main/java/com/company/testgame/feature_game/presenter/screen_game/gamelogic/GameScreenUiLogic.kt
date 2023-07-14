package com.company.testgame.feature_game.presenter.screen_game.gamelogic

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.company.testgame.R
import com.company.testgame.databinding.GameLayoutBinding
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.presenter.screen_game.*
import com.company.testgame.util.click
import com.company.testgame.util.fadeIn
import com.company.testgame.util.fadeOut
import com.company.testgame.util.float
import com.company.testgame.util.moveDown
import com.company.testgame.util.moveGemAway
import com.company.testgame.util.showGemTextView
import com.company.testgame.util.starsFadeIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

//TODO fix images not displaying????
@SuppressLint("ClickableViewAccessibility")
class GameScreenUiLogic(
    private val rootActivity: ComponentActivity,
    private val binding: GameLayoutBinding,
    private val gameViewModel: GameViewModel,
    private val randomInstance: Random,
    private val navController: NavController
) {

    private val displayMetrics: DisplayMetrics = rootActivity.resources.displayMetrics
    private val layoutInflater: LayoutInflater = rootActivity.layoutInflater

    private var gameObjectsQueue = mutableListOf<GameObject>()

    private lateinit var obstacleDrawable: Drawable
    private lateinit var bonusDrawable: Drawable

    init {
        binding.run {
            scoreTextView.text = gameViewModel.score.value.toString()
            livesTextView.text = gameViewModel.lives.value.toString()
            gemTextView.text = gameViewModel.achievements.value.playerGems.toString()

            exitImageButton.setOnClickListener {
                gameViewModel.stopGame()
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
                livesImageView.setImageResource(R.drawable.heart)
                scoreTextView.fadeIn()
                scoreImageView.fadeIn()
                scoreImageView.setImageResource(R.drawable.star)
                starsImageView.starsFadeIn()
                landingPadImageView.moveDown()

//                rocketImageView.setImageResource(R.drawable.rocket_fly)

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
        val view = when(randomNumber) {
            in 0..8 -> layoutInflater.inflate(R.layout.gem_layout, null)
            in 9..40 -> {
                val v = layoutInflater.inflate(R.layout.bonus_layout, null)
                if (this::bonusDrawable.isInitialized) v.findViewById<ImageView>(R.id.bonusImageView).setImageDrawable(bonusDrawable)
                v
            }
            else -> {
                val v = layoutInflater.inflate(R.layout.obstacle_layout, null)
                if (this::obstacleDrawable.isInitialized) v.findViewById<ImageView>(R.id.obstacleImageView).setImageDrawable(obstacleDrawable)
                v
            }
        }
        view.id = View.generateViewId()
        binding.rootLayout.addView(view, 0)

        var horizontalBias = evaluateRandomBias()
        if (gameObjectsQueue.isNotEmpty()) while (gameObjectsQueue.map { it.bias }.contains(horizontalBias)) {
            horizontalBias = evaluateRandomBias()
        }

        ConstraintSet().apply {
            clone(binding.rootLayout)
            connect(view.id, ConstraintSet.BOTTOM, binding.rootLayout.id, ConstraintSet.TOP, 0)
            connect(view.id, ConstraintSet.LEFT, binding.rootLayout.id, ConstraintSet.LEFT, 0)
            connect(view.id, ConstraintSet.RIGHT, binding.rootLayout.id, ConstraintSet.RIGHT, 0)
            setHorizontalBias(view.id, horizontalBias)
            applyTo(binding.rootLayout)
        }

        gameObjectsQueue.add(
            when(randomNumber) {
                in 0..10 -> GameObject.Gem(view, horizontalBias)
                in 11..40 -> GameObject.Bonus(view, horizontalBias)
                else -> GameObject.Obstacle(view, horizontalBias)
            }
        )
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
                        when (gameObjectsQueue[i]) {
                            is GameObject.Obstacle -> {
                                decreaseLives()
                                destroyGameObject(i)
                            }
                            is GameObject.Bonus -> {
                                val score = gameViewModel.score.value + 1
                                gameViewModel.setScore(score)
                                binding.scoreTextView.text = score.toString()
                                destroyGameObject(i)
                            }
                            is GameObject.Gem -> {
                                val gameObjectView = gameObjectsQueue[i].view
                                gameObjectsQueue.removeAt(i)
                                binding.gemTextView.showGemTextView()
                                gameObjectView.moveGemAway(binding.gemTextView) {
                                    binding.rootLayout.removeView(gameObjectView)
                                }
                                gameViewModel.addGems()
                                binding.gemTextView.text = gameViewModel.achievements.value.playerGems.toString()
                            }
                        }
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
            gameViewModel.stopGameWithNavigating(navController)
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

    suspend fun setRocketSkin(skin: Skin) {
        withContext(Dispatchers.Main) {
            if (skin.locallyStored) {
                binding.rocketImageView.setImageResource(skin.imageUrl.toInt())
            } else {
                binding.rocketImageView.load(skin.imageUrl)
            }
        }
    }

    //TODO fix so images load properly (always)
    suspend fun setObstacleSkin(skin: Skin) {
        obstacleDrawable = if (skin.locallyStored) {
            ResourcesCompat.getDrawable(rootActivity.resources, skin.imageUrl.toInt(), null) ?: return
        } else {
            ImageLoader(rootActivity).execute(
                ImageRequest.Builder(rootActivity)
                    .data(skin.imageUrl)
                    .build()
            ).drawable ?: return
        }
    }

    suspend fun setBonusSkin(skin: Skin) {
        bonusDrawable = if (skin.locallyStored) {
            ResourcesCompat.getDrawable(rootActivity.resources, skin.imageUrl.toInt(), null) ?: return
        } else {
            ImageLoader(rootActivity).execute(
                ImageRequest.Builder(rootActivity)
                    .data(skin.imageUrl)
                    .build()
            ).drawable ?: return
        }
    }
}