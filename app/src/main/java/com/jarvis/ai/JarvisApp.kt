package com.jarvis.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.screens.ChatScreen
import com.jarvis.ai.ui.screens.CodingScreen
import com.jarvis.ai.ui.screens.HomeScreen
import com.jarvis.ai.ui.screens.MemoryScreen
import com.jarvis.ai.ui.screens.PhoneControlScreen
import com.jarvis.ai.ui.screens.SettingsScreen
import com.jarvis.ai.ui.screens.SocialScreen
import com.jarvis.ai.ui.screens.VoiceScreen
import com.jarvis.ai.ui.theme.Bg
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary
import com.jarvis.ai.ui.theme.Violet
import com.jarvis.ai.ui.theme.VioletLight

enum class Section(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Filled.Home),
    CHAT("Chat", Icons.Filled.Chat),
    VOICE("Voice", Icons.Filled.Mic),
    PHONE("Phone", Icons.Filled.PhoneAndroid),
    SOCIAL("Social", Icons.Filled.Favorite),
    CODING("Coding", Icons.Filled.Code),
    MEMORY("Memory", Icons.Filled.Memory),
    SETTINGS("Settings", Icons.Filled.Settings)
}

@Composable
fun JarvisApp(vm: MainViewModel) {
    var current by rememberSaveable { mutableStateOf(Section.HOME) }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1A0F3A), Bg, Color(0xFF080512))
                )
            )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            Box(Modifier.weight(1f).fillMaxWidth()) {
                when (current) {
                    Section.HOME -> HomeScreen(vm, onOpenChat = { current = Section.CHAT })
                    Section.CHAT -> ChatScreen(vm)
                    Section.VOICE -> VoiceScreen(vm)
                    Section.PHONE -> PhoneControlScreen()
                    Section.SOCIAL -> SocialScreen()
                    Section.CODING -> CodingScreen()
                    Section.MEMORY -> MemoryScreen()
                    Section.SETTINGS -> SettingsScreen(vm)
                }
            }
            BottomTabs(current = current, onSelect = { current = it })
        }
    }
}

/** Scrollable bottom bar so all 8 sections fit on any phone width. */
@Composable
private fun BottomTabs(current: Section, onSelect: (Section) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.05f))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Section.entries.forEach { section ->
            val selected = section == current
            Column(
                Modifier
                    .width(68.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (selected) Violet.copy(alpha = 0.25f) else Color.Transparent)
                    .clickable { onSelect(section) }
                    .padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    section.icon,
                    contentDescription = section.label,
                    tint = if (selected) VioletLight else TextMuted
                )
                Text(
                    section.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected) TextPrimary else TextMuted
                )
            }
        }
    }
}
