package com.company.testgame.feature_game.presenter.screen_shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.domain.model.skin.SkinUnlockCondition
import com.company.testgame.feature_game.presenter.screen_shop.ShopViewModel

@Composable
fun PurchaseDialog(
    modifier: Modifier = Modifier,
    skin: Skin,
    condition: SkinUnlockCondition,
    playerGems: Int,
    shopViewModel: ShopViewModel
) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(R.string.purchase_this_skin),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier,
                enabled = playerGems >= condition.buyAmount!!,
                onClick = {
                    shopViewModel.buySkin(skin, condition.buyAmount)
                    shopViewModel.hidePurchaseDialog()
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.purchase_for) + " ${condition.buyAmount}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Image(
                        modifier = Modifier.size(24.dp),
                        bitmap = ImageBitmap.imageResource(id = R.drawable.gem),
                        contentDescription = "Gem"
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier,
                onClick = {
                    shopViewModel.hidePurchaseDialog()
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseDialogPreview() {
    PurchaseDialog(
        modifier = Modifier,
        condition = SkinUnlockCondition.Condition21,
        playerGems = 5,
        shopViewModel = hiltViewModel(),
        skin = Skin(
            0,
            "Leon 0",
            com.company.testgame.feature_game.domain.model.ShopItem.RocketSkin.shopKey,
            true,
            R.drawable.rocket_idle.toString(),
            true,
            false,
            null
        ),
    )
}