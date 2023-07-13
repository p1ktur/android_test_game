package com.company.testgame.feature_game.presenter.screen_settings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.company.testgame.R
import com.company.testgame.components.MenuButton
import com.company.testgame.feature_game.domain.model.Difficulty
import com.company.testgame.ui.theme.TestGameTheme
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val menuButtonsWidth = 200.dp
    val menuButtonsHeight = 44.dp

    val interButtonsSpace = 32.dp

    val difficulty = settingsViewModel.difficulty.collectAsState()

    Box(modifier = modifier) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.back_main),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(interButtonsSpace))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.difficulty),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Slider(
                    value = when (difficulty.value) {
                        Difficulty.Easy -> 0f
                        Difficulty.Medium -> 1f
                        Difficulty.Hard -> 2f
                    },
                    valueRange = 0f..2f,
                    steps = 1,
                    onValueChange = { value ->
                        Log.d("TAG", "$value")
                        val difficultyValue = when (value.roundToInt()) {
                            0 -> Difficulty.Easy
                            1 -> Difficulty.Medium
                            else -> Difficulty.Hard
                        }
                        settingsViewModel.setDifficulty(difficultyValue)
                    }
                )
            }
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(
                    R.string.back_to_menu
                )
            ) {
                navController.navigateUp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    TestGameTheme {
        SettingsScreen(
            modifier = Modifier.fillMaxSize(),
            rememberNavController()
        )
    }
}