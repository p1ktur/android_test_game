package com.company.testgame.feature_splash.presenter.screen_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.R
import com.company.testgame.ui.theme.FireBrickRed
import com.company.testgame.ui.theme.KhakiYellow
import com.company.testgame.ui.theme.LightBlue
import com.company.testgame.ui.theme.LightSkyBlue

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    onLoadingEnd: () -> Unit
) {
    val progressBar = viewModel.loadingProgress.collectAsState()

    LaunchedEffect(key1 = progressBar.value) {
        if (progressBar.value >= 1f) {
            onLoadingEnd()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.app_icon),
            contentDescription = "App Icon",
            modifier = Modifier.size(160.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier
            .size(240.dp, 20.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
            LinearProgressIndicator(
                progress = progressBar.value,
                color = MaterialTheme.colorScheme.primary,
                trackColor = LightSkyBlue,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(modifier = Modifier.fillMaxSize().background(LightBlue)) {

    }
}