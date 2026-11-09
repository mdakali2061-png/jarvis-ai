package com.jarvis.ai.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.theme.Blue
import com.jarvis.ai.ui.theme.Cyan
import com.jarvis.ai.ui.theme.Pink
import com.jarvis.ai.ui.theme.Purple
import com.jarvis.ai.ui.theme.Violet
import com.jarvis.ai.ui.theme.VioletLight

/**
 * Animated female AI avatar drawn with Canvas: glowing pulsing rings around a stylised
 * woman silhouette (long hair, face, shoulders). Later phases can swap this for a custom
 * image / animated talking avatar without touching the screens that use it.
 *
 * @param active true while Jarvis is listening/speaking: rings pulse faster and brighter.
 */
@Composable
fun AvatarOrb(
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
    active: Boolean = false
) {
    val transition = rememberInfiniteTransition(label = "avatar")
    val pulse by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (active) 900 else 2600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val spin by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spin"
    )

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size)) {
            val c = center
            val r = this.size.minDimension / 2f

            // Outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Violet.copy(alpha = 0.35f + 0.25f * pulse),
                        Color.Transparent
                    ),
                    center = c,
                    radius = r
                ),
                radius = r
            )

            // Rotating dashed ring
            val ringR = r * (0.88f + 0.04f * pulse)
            drawCircle(
                brush = Brush.sweepGradient(listOf(Cyan, Violet, Pink, Blue, Cyan), center = c),
                radius = ringR,
                center = c,
                style = Stroke(width = 3.dp.toPx())
            )
            // Orbiting dot
            val angle = Math.toRadians(spin.toDouble())
            val dot = Offset(
                c.x + (ringR * Math.cos(angle)).toFloat(),
                c.y + (ringR * Math.sin(angle)).toFloat()
            )
            drawCircle(color = Cyan, radius = 5.dp.toPx(), center = dot)

            // Inner disc
            val discR = r * 0.74f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF2A1450), Color(0xFF120A26)),
                    center = c,
                    radius = discR
                ),
                radius = discR,
                center = c
            )
            drawCircle(
                color = VioletLight.copy(alpha = 0.5f),
                radius = discR,
                center = c,
                style = Stroke(width = 1.5.dp.toPx())
            )

            // --- Stylised woman silhouette ---
            val headR = discR * 0.30f
            val headC = Offset(c.x, c.y - discR * 0.12f)

            // Long hair (behind head, flowing to shoulders)
            val hair = Path().apply {
                moveTo(headC.x - headR * 1.25f, headC.y)
                cubicTo(
                    headC.x - headR * 1.5f, headC.y - headR * 1.7f,
                    headC.x + headR * 1.5f, headC.y - headR * 1.7f,
                    headC.x + headR * 1.25f, headC.y
                )
                cubicTo(
                    headC.x + headR * 1.7f, headC.y + headR * 1.6f,
                    headC.x + headR * 1.4f, headC.y + headR * 2.6f,
                    headC.x + headR * 0.9f, headC.y + headR * 2.9f
                )
                lineTo(headC.x - headR * 0.9f, headC.y + headR * 2.9f)
                cubicTo(
                    headC.x - headR * 1.4f, headC.y + headR * 2.6f,
                    headC.x - headR * 1.7f, headC.y + headR * 1.6f,
                    headC.x - headR * 1.25f, headC.y
                )
                close()
            }
            drawPath(
                path = hair,
                brush = Brush.verticalGradient(
                    listOf(Purple, Violet, Blue),
                    startY = headC.y - headR * 1.5f,
                    endY = headC.y + headR * 3f
                )
            )

            // Shoulders / body
            val body = Path().apply {
                moveTo(c.x - discR * 0.62f, c.y + discR * 0.95f)
                cubicTo(
                    c.x - discR * 0.62f, c.y + discR * 0.45f,
                    c.x - discR * 0.30f, c.y + discR * 0.34f,
                    c.x, c.y + discR * 0.34f
                )
                cubicTo(
                    c.x + discR * 0.30f, c.y + discR * 0.34f,
                    c.x + discR * 0.62f, c.y + discR * 0.45f,
                    c.x + discR * 0.62f, c.y + discR * 0.95f
                )
                close()
            }
            drawPath(
                path = body,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF4C1D95), Color(0xFF1E1B4B)),
                    startY = c.y + discR * 0.3f,
                    endY = c.y + discR
                )
            )

            // Neck + face
            drawRect(
                color = Color(0xFFF3D2E6),
                topLeft = Offset(c.x - headR * 0.28f, headC.y + headR * 0.7f),
                size = androidx.compose.ui.geometry.Size(headR * 0.56f, headR * 1.0f)
            )
            drawCircle(color = Color(0xFFFBE3F1), radius = headR, center = headC)

            // Eyes + smile
            val eyeY = headC.y - headR * 0.05f
            val eyeDx = headR * 0.38f
            drawCircle(Color(0xFF3B0764), headR * 0.09f, Offset(headC.x - eyeDx, eyeY))
            drawCircle(Color(0xFF3B0764), headR * 0.09f, Offset(headC.x + eyeDx, eyeY))
            val smile = Path().apply {
                moveTo(headC.x - headR * 0.28f, headC.y + headR * 0.38f)
                quadraticBezierTo(
                    headC.x, headC.y + headR * (0.62f + 0.12f * pulse),
                    headC.x + headR * 0.28f, headC.y + headR * 0.38f
                )
            }
            drawPath(
                path = smile,
                color = Pink,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}
