package com.company.testgame.feature_game.presenter.screen_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.company.testgame.R
import com.company.testgame.components.MenuButton
import com.company.testgame.components.SmartImage
import com.company.testgame.feature_game.presenter.util.Screen
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    score: Int,
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    val menuButtonsWidth = 200.dp
    val menuButtonsHeight = 44.dp

    val interButtonsSpace = 32.dp

    val backgroundSkin = resultViewModel.backgroundSkin.collectAsState()
    val playerAchievements = resultViewModel.achievements.collectAsState()

    Box {
        SmartImage(
            modifier = Modifier.fillMaxSize(),
            skin = backgroundSkin.value,
            scale = ContentScale.FillHeight
        )
        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.total_distance_flied) + " ${playerAchievements.value.totalDistanceFlied}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(interButtonsSpace))
            Text(
                text = stringResource(R.string.total_score_gained) + " ${playerAchievements.value.totalScoreGained}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(interButtonsSpace))
            Text(
                text = stringResource(R.string.your_score_is) + " $score",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(R.string.back_to_menu)
            ) {
                navController.navigateUp()
            }
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(R.string.try_again)
            ) {
                navController.popBackStack()
                navController.navigate(Screen.GameScreen.route)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    TestGameTheme {
        ResultScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController(),
            score = 0
        )
    }
}