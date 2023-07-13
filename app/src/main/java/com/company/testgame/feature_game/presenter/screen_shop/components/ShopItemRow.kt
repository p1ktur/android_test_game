package com.company.testgame.feature_game.presenter.screen_shop.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.feature_game.presenter.screen_shop.ShopItem
import com.company.testgame.feature_game.presenter.screen_shop.ShopViewModel
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ShopItemRow(
    modifier: Modifier = Modifier,
    shopItemsType: ShopItem,
    shopViewModel: ShopViewModel
) {
    val uiState = shopViewModel.state.collectAsState()

    val skinsList = when (shopItemsType) {
        ShopItem.RocketSkin -> uiState.value.rocketSkinsList
        ShopItem.ObstacleSkin -> uiState.value.obstacleSkinsList
        ShopItem.BonusSkin -> uiState.value.bonusSkinsList
        ShopItem.BackgroundSkin -> uiState.value.backgroundSkinsList
    }

    LazyRow(modifier = modifier) {
        items(skinsList) { skin ->
            ShopItem(
                modifier = Modifier.width(96.dp),
                unlocked = skin.unlocked,
                imageUrl = skin.imageUrl,
                locallyStored = skin.locallyStored,
                itemName = skin.name,
                selected = skin.selected
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemRowPreview() {
    TestGameTheme {
        ShopItemRow(
            shopItemsType = ShopItem.RocketSkin,
            shopViewModel = hiltViewModel()
        )
    }
}