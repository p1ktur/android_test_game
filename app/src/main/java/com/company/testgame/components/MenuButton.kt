package com.company.testgame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.testgame.ui.theme.TestGameTheme
import com.company.testgame.util.click

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.click().background(color = Color.Transparent),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            //Image
            Text(
                modifier = Modifier.fillMaxSize(),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuButtonPreview() {
    TestGameTheme {
        MenuButton(modifier = Modifier.size(180.dp, 40.dp), "Button") {

        }
    }
}