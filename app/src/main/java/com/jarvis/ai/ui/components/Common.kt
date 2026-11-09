package com.jarvis.ai.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jarvis.ai.core.BatteryInfo
import com.jarvis.ai.ui.theme.Blue
import com.jarvis.ai.ui.theme.OfflineRed
import com.jarvis.ai.ui.theme.OnlineGreen
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary
import com.jarvis.ai.ui.theme.Violet
import com.jarvis.ai.ui.theme.VioletLight

/** Small rounded status pill (ONLINE, battery, mic ...). */
@Composable
fun StatusChip(label: String, dot: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White.copy(alpha = 0.07f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dot)
        )
        Spacer(Modifier.width(6.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextPrimary)
    }
}

/** Top row: ONLINE/OFFLINE, microphone status, battery. */
@Composable
fun StatusRow(
    online: Boolean,
    micActive: Boolean,
    battery: BatteryInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusChip(
            label = if (online) "ONLINE" else "OFFLINE",
            dot = if (online) OnlineGreen else OfflineRed
        )
        StatusChip(
            label = if (micActive) "MIC ON" else "MIC OFF",
            dot = if (micActive) OnlineGreen else TextMuted
        )
        val batColor = when {
            battery.charging -> Blue
            battery.percent <= 15 -> OfflineRed
            else -> OnlineGreen
        }
        StatusChip(
            label = "${battery.percent}%" + if (battery.charging) " ⚡" else "",
            dot = batColor
        )
    }
}

/** Large circular voice activation button with a pulsing halo while active. */
@Composable
fun MicButton(
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 84.dp
) {
    val transition = rememberInfiniteTransition(label = "mic")
    val halo by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "halo"
    )
    Box(modifier = modifier.size(size * 1.5f), contentAlignment = Alignment.Center) {
        if (active) {
            Box(
                Modifier
                    .size(size)
                    .scale(halo)
                    .clip(CircleShape)
                    .background(Violet.copy(alpha = 0.30f))
            )
        }
        Box(
            Modifier
                .size(size)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(Violet, Blue)))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (active) Icons.Filled.Mic else Icons.Filled.MicOff,
                contentDescription = if (active) "Stop listening" else "Start listening",
                tint = Color.White,
                modifier = Modifier.size(size * 0.42f)
            )
        }
    }
}

/** Screen title block used by every section. */
@Composable
fun ScreenHeader(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 12.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
    }
}

/** A card for a feature that is planned for a later phase. Honest about what is NOT built yet. */
@Composable
fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String,
    phaseLabel: String?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Violet.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = VioletLight)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Text(description, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
            }
            if (phaseLabel != null) {
                Spacer(Modifier.width(8.dp))
                Text(
                    phaseLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = VioletLight
                )
            }
        }
    }
}
