package com.company.testgame.feature_game.presenter.screen_shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.company.testgame.components.SmartImage
import com.company.testgame.feature_game.domain.model.ShopItem
import com.company.testgame.feature_game.domain.model.skin.SkinUnlockCondition
import com.company.testgame.feature_game.presenter.screen_shop.components.PurchaseDialog
import com.company.testgame.feature_game.presenter.screen_shop.components.RequirementDialog
import com.company.testgame.feature_game.presenter.screen_shop.components.ShopItemRow
import com.company.testgame.ui.theme.LightSkyBlue
import com.company.testgame.ui.theme.TestGameTheme

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    shopViewModel: ShopViewModel = hiltViewModel()
) {
    val playerAchievements = shopViewModel.achievements.collectAsState()

    val purchaseDialogShown = shopViewModel.purchaseDialogShown.collectAsState()
    val requirementDialogShown = shopViewModel.requirementDialogShown.collectAsState()

    val shownSkin = shopViewModel.shownSkin.collectAsState()

    val backgroundSkin = shopViewModel.backgroundSkin.collectAsState()

    Box(modifier = modifier) {
        SmartImage(
            modifier = Modifier.fillMaxSize(),
            skin = backgroundSkin.value,
            scale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.size(48.dp),
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
                Row {
                    Text(
                        modifier = Modifier.height(48.dp),
                        text = playerAchievements.value.playerGems.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        modifier = Modifier.size(48.dp),
                        bitmap = ImageBitmap.imageResource(id = R.drawable.gem),
                        contentDescription = "Player gems"
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
            ShopItemRow(
                modifier = Modifier.fillMaxWidth(),
                shopItemsType = ShopItem.RocketSkin.shopKey,
                shopViewModel = shopViewModel
            )
            Spacer(modifier = Modifier.height(8.dp))
            ShopItemRow(
                modifier = Modifier.fillMaxWidth(),
                shopItemsType = ShopItem.ObstacleSkin.shopKey,
                shopViewModel = shopViewModel
            )
            Spacer(modifier = Modifier.height(8.dp))
            ShopItemRow(
                modifier = Modifier.fillMaxWidth(),
                shopItemsType = ShopItem.BonusSkin.shopKey,
                shopViewModel = shopViewModel
            )
            Spacer(modifier = Modifier.height(8.dp))
            ShopItemRow(
                modifier = Modifier.fillMaxWidth(),
                shopItemsType = ShopItem.BackgroundSkin.shopKey,
                shopViewModel = shopViewModel
            )
        }
        if (purchaseDialogShown.value && SkinUnlockCondition.getConditionFromId(shownSkin.value.unlockConditionId) != null) {
            PurchaseDialog(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp, 180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LightSkyBlue)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                ,
                skin = shownSkin.value,
                condition = SkinUnlockCondition.getConditionFromId(shownSkin.value.unlockConditionId)!!,
                playerGems = playerAchievements.value.playerGems,
                shopViewModel = shopViewModel
            )
        }
        if (
            requirementDialogShown.value &&
            SkinUnlockCondition.getConditionFromId(shownSkin.value.unlockConditionId)?.achievementRequirement != null
        ) {
            RequirementDialog(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp, 180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LightSkyBlue)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                ,
                condition = SkinUnlockCondition.getConditionFromId(shownSkin.value.unlockConditionId)!!,
                shopViewModel = shopViewModel
            )
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