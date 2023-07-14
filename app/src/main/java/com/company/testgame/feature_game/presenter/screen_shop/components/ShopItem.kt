package com.company.testgame.feature_game.presenter.screen_shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.R
import com.company.testgame.components.SmartImage
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.presenter.screen_shop.ShopViewModel
import com.company.testgame.ui.theme.Gold
import com.company.testgame.ui.theme.Ivory
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ShopItem(
    modifier: Modifier = Modifier,
    skin: Skin,
    shopViewModel: ShopViewModel
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clickable {
                shopViewModel.selectSkin(skin)
            }
    ) {
        Column {
            SmartImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, if (skin.selected) Color.Green else Color.Black, CircleShape)
                    .background(Ivory),
                skin = skin
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (!skin.unlocked) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock",
                tint = Gold,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemPreview() {
    TestGameTheme {
        ShopItem(
            modifier = Modifier.width(96.dp),
            Skin(
                0,
                "Leon 0",
                com.company.testgame.feature_game.domain.model.ShopItem.RocketSkin.shopKey,
                true,
                R.drawable.rocket_idle.toString(),
                true,
                false,
                null
            ),
            hiltViewModel()
        )
    }
}