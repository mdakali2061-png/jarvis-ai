package com.jarvis.ai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Telegram
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jarvis.ai.MainViewModel
import com.jarvis.ai.core.AppLauncher
import com.jarvis.ai.ui.components.AvatarOrb
import com.jarvis.ai.ui.components.GlassCard
import com.jarvis.ai.ui.components.MicButton
import com.jarvis.ai.ui.components.StatusRow
import com.jarvis.ai.ui.components.Waveform
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary
import com.jarvis.ai.ui.theme.Violet
import com.jarvis.ai.ui.theme.VioletLight

private data class Shortcut(val app: AppLauncher.AppInfo, val icon: ImageVector)

@Composable
fun HomeScreen(vm: MainViewModel, onOpenChat: () -> Unit) {
    val online by vm.online.collectAsState()
    val battery by vm.battery.collectAsState()
    val micActive by vm.micActive.collectAsState()
    val messages by vm.messages.collectAsState()
    val context = LocalContext.current

    val shortcuts = listOf(
        Shortcut(AppLauncher.YOUTUBE, Icons.Filled.PlayArrow),
        Shortcut(AppLauncher.WHATSAPP, Icons.Filled.Chat),
        Shortcut(AppLauncher.INSTAGRAM, Icons.Filled.PhotoCamera),
        Shortcut(AppLauncher.FACEBOOK, Icons.Filled.Facebook),
        Shortcut(AppLauncher.TELEGRAM, Icons.Filled.Telegram),
        Shortcut(AppLauncher.MESSENGER, Icons.Filled.Send)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatusRow(online = online, micActive = micActive, battery = battery)

        Spacer(Modifier.height(12.dp))
        Text(
            "JARVIS",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary
        )
        Text(
            "Tumhari personal AI companion",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted
        )

        Spacer(Modifier.height(8.dp))
        AvatarOrb(active = micActive)

        Waveform(active = micActive, modifier = Modifier.padding(horizontal = 24.dp))

        MicButton(active = micActive, onClick = { vm.toggleMic() })
        Text(
            if (micActive) "Sun rahi hoon... (demo animation)" else "Mic dabao aur baat karo",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted
        )

        Spacer(Modifier.height(12.dp))

        // Last message preview -> opens Chat
        val last = messages.lastOrNull()
        if (last != null) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenChat)
            ) {
                Column {
                    Text(
                        if (last.fromUser) "You" else "Jarvis",
                        style = MaterialTheme.typography.labelSmall,
                        color = VioletLight
                    )
                    Text(
                        last.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        maxLines = 3
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            "Quick Apps",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        shortcuts.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { s ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                            .clickable { AppLauncher.open(context, s.app) }
                            .padding(8.dp)
                    ) {
                        Box(
                            Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Violet.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(s.icon, contentDescription = s.app.label, tint = VioletLight)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            s.app.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}
