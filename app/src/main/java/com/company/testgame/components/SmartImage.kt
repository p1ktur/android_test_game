package com.company.testgame.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import coil.compose.AsyncImage
import com.company.testgame.feature_game.domain.model.skin.Skin

@Composable
fun SmartImage(
    modifier: Modifier = Modifier,
    skin: Skin,
    scale: ContentScale = ContentScale.FillWidth
) {
    if (skin.locallyStored) {
        Image(
            bitmap = ImageBitmap.imageResource(id = skin.imageUrl.toInt()),
            contentDescription = skin.name,
            modifier = modifier,
            contentScale = scale
        )
    } else {
        AsyncImage(
            model = skin.imageUrl,
            contentDescription = skin.name,
            modifier = modifier,
            contentScale = scale
        )
    }
}