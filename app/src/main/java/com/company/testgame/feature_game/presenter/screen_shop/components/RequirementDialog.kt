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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.domain.model.skin.SkinUnlockCondition
import com.company.testgame.feature_game.presenter.screen_shop.ShopViewModel

@Composable
fun RequirementDialog(
    modifier: Modifier = Modifier,
    condition: SkinUnlockCondition,
    shopViewModel: ShopViewModel
) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = condition.achievementRequirement!!.conditionText,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier,
            onClick = {
                shopViewModel.hideRequirementDialog()
            }
        ) {
            Text(
                text = stringResource(R.string.ok),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequirementDialogPreview() {
    RequirementDialog(
        modifier = Modifier,
        condition = SkinUnlockCondition.Condition21,
        shopViewModel = hiltViewModel(),
    )
}