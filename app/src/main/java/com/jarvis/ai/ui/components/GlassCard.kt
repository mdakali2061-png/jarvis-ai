package com.jarvis.ai.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.theme.VioletLight

/** Glassmorphism-style translucent card with a soft violet border. */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    corner: Dp = 20.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(corner)
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.10f),
                        Color.White.copy(alpha = 0.03f)
                    )
                )
            )
            .border(BorderStroke(1.dp, VioletLight.copy(alpha = 0.25f)), shape)
            .padding(contentPadding)
    ) {
        content()
    }
}
