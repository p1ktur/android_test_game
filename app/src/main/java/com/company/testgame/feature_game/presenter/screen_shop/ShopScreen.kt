package com.company.testgame.feature_game.presenter.screen_shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.company.testgame.feature_game.presenter.screen_shop.components.ShopItemRow
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    shopViewModel: ShopViewModel = hiltViewModel()
) {
    Box(modifier = modifier) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.back_main),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.size(48.dp, 48.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        modifier = Modifier.scale(1.6f, 1.6f),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.shop),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.rocket_skins),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ShopItemRow(
                        modifier = Modifier.fillMaxWidth().height(96.dp),
                        shopItemsType = ShopItem.RocketSkin,
                        shopViewModel = shopViewModel
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.obstacle_skins),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ShopItemRow(
                        modifier = Modifier.fillMaxWidth().height(96.dp),
                        shopItemsType = ShopItem.ObstacleSkin,
                        shopViewModel = shopViewModel
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.bonus_skins),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ShopItemRow(
                        modifier = Modifier.fillMaxWidth().height(96.dp),
                        shopItemsType = ShopItem.BonusSkin,
                        shopViewModel = shopViewModel
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.background_skins),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ShopItemRow(
                        modifier = Modifier.fillMaxWidth().height(96.dp),
                        shopItemsType = ShopItem.BackgroundSkin,
                        shopViewModel = shopViewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    TestGameTheme {
        ShopScreen(
            modifier = Modifier.fillMaxSize(),
            rememberNavController()
        )
    }
}