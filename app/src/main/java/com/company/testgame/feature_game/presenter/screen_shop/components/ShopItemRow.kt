package com.company.testgame.feature_game.presenter.screen_shop.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.domain.model.ShopItem
import com.company.testgame.feature_game.presenter.screen_shop.ShopUiState
import com.company.testgame.feature_game.presenter.screen_shop.ShopViewModel
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ShopItemRow(
    modifier: Modifier = Modifier,
    shopItemsType: String,
    shopViewModel: ShopViewModel
) {
    val uiState = shopViewModel.state.collectAsState(ShopUiState())

    val skinsList: List<Skin>
    val rowText: String

    when (shopItemsType) {
        ShopItem.RocketSkin.shopKey -> {
            skinsList = uiState.value.rocketSkinsList
            rowText = stringResource(id = R.string.rocket_skins)
        }
        ShopItem.ObstacleSkin.shopKey -> {
            skinsList = uiState.value.obstacleSkinsList
            rowText = stringResource(id = R.string.obstacle_skins)
        }
        ShopItem.BonusSkin.shopKey -> {
            skinsList = uiState.value.bonusSkinsList
            rowText = stringResource(id = R.string.bonus_skins)
        }
        ShopItem.BackgroundSkin.shopKey -> {
            skinsList = uiState.value.backgroundSkinsList
            rowText = stringResource(id = R.string.background_skins)
        }
        else -> {
            skinsList = uiState.value.rocketSkinsList
            rowText = stringResource(id = R.string.rocket_skins)
        }
    }

    Row(modifier = modifier) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = rowText,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(modifier = Modifier.fillMaxWidth().height(96.dp)) {
                items(skinsList) { skin ->
                    ShopItem(
                        modifier = Modifier.width(96.dp),
                        skin = skin,
                        shopViewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemRowPreview() {
    TestGameTheme {
        ShopItemRow(
            shopItemsType = ShopItem.RocketSkin.shopKey,
            shopViewModel = hiltViewModel()
        )
    }
}