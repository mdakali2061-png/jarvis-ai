package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jarvis.ai.core.AppLauncher
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.ScreenHeader

@Composable
fun SocialScreen() {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenHeader("Social Media", "Tap karke app kholo. Messaging/automation Phase 5 mein.")
        FeatureCard(
            Icons.Filled.PlayArrow, "YouTube", "Search, play, media controls", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.YOUTUBE) }
        )
        FeatureCard(
            Icons.Filled.Chat, "WhatsApp", "Messages, calls", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.WHATSAPP) }
        )
        FeatureCard(
            Icons.Filled.PhotoCamera, "Instagram", "Open app", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.INSTAGRAM) }
        )
        FeatureCard(
            Icons.Filled.Facebook, "Facebook", "Open app", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.FACEBOOK) }
        )
        FeatureCard(
            Icons.Filled.Send, "Messenger", "Open app", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.MESSENGER) }
        )
        FeatureCard(
            Icons.Filled.Send, "Telegram", "Open app", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.TELEGRAM) }
        )
        FeatureCard(
            Icons.Filled.MusicNote, "TikTok", "Open app", "OPEN",
            onClick = { AppLauncher.open(context, AppLauncher.TIKTOK) }
        )
    }
}
