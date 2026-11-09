package com.jarvis.ai.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.theme.Cyan
import com.jarvis.ai.ui.theme.Violet
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

/** Animated audio waveform. Bars dance when [active], and rest as a flat line otherwise. */
@Composable
fun Waveform(
    active: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    bars: Int = 28
) {
    val transition = rememberInfiniteTransition(label = "wave")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(height)) {
        val barW = size.width / (bars * 1.6f)
        val gap = (size.width - barW * bars) / (bars - 1)
        val maxH = size.height
        for (i in 0 until bars) {
            val level = if (active) {
                0.18f + 0.82f * abs(sin(phase + i * 0.55f) * sin(phase * 0.5f + i * 0.21f))
            } else {
                0.06f
            }
            val h = maxH * level
            val x = i * (barW + gap)
            drawRoundRect(
                brush = Brush.verticalGradient(
                    listOf(Cyan, Violet),
                    startY = (maxH - h) / 2f,
                    endY = (maxH + h) / 2f
                ),
                topLeft = Offset(x, (maxH - h) / 2f),
                size = Size(barW, h),
                cornerRadius = CornerRadius(barW / 2f, barW / 2f)
            )
        }
    }
}
