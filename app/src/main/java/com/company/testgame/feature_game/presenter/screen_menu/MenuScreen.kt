package com.company.testgame.feature_game.presenter.screen_menu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.company.testgame.R
import com.company.testgame.components.MenuButton
import com.company.testgame.feature_game.presenter.screen_menu.components.ShopButton
import com.company.testgame.feature_game.presenter.util.Screen
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    rootActivity: Activity,
    navController: NavController
) {
    val menuButtonsWidth = 200.dp
    val menuButtonsHeight = 44.dp

    val interButtonsSpace = 32.dp

    Box(modifier = modifier) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.back_main),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            ShopButton(
                modifier = Modifier.align(Alignment.TopEnd).size(48.dp, 48.dp)
            ) {
                navController.navigate(Screen.ShopScreen.route)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(
                    R.string.start_game
                )
            ) {
                navController.navigate(Screen.GameScreen.route)
            }
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(
                    R.string.settings
                )
            ) {
                navController.navigate(Screen.SettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(interButtonsSpace))
            MenuButton(
                modifier = Modifier.size(menuButtonsWidth, menuButtonsHeight),
                text = stringResource(
                    R.string.quit
                )
            ) {
            rootActivity.finishAndRemoveTask()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    TestGameTheme {
//        MenuScreen(
//            modifier = Modifier.fillMaxSize(),
//            rememberNavController()
//        )
    }
}