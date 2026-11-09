package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.ai.MainViewModel
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.GlassCard
import com.jarvis.ai.ui.components.ScreenHeader
import com.jarvis.ai.ui.components.StatusChip
import com.jarvis.ai.ui.theme.OfflineRed
import com.jarvis.ai.ui.theme.OnlineGreen
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary

@Composable
fun SettingsScreen(vm: MainViewModel) {
    val online by vm.online.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenHeader("Settings", "API key, model, permissions")
        GlassCard(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Connection", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    Text(
                        if (online) "Internet hai. Gemini Phase 2 mein connect hoga."
                        else "Internet nahi hai. Sirf offline features chalenge.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
                StatusChip(
                    label = if (online) "ONLINE" else "OFFLINE",
                    dot = if (online) OnlineGreen else OfflineRed
                )
            }
        }
        FeatureCard(
            Icons.Filled.Key, "Gemini API Key",
            "Secure storage, show/hide, save, test connection", "PHASE 2"
        )
        FeatureCard(
            Icons.Filled.Tune, "Model & Temperature",
            "Gemini model chuno, response style adjust karo", "PHASE 2"
        )
        FeatureCard(
            Icons.Filled.Security, "Permission Manager",
            "Har permission ki wajah aur on/off", "PHASE 9"
        )
        FeatureCard(
            Icons.Filled.DeleteSweep, "Clear Chat History",
            "Abhi chat clear karne ke liye tap karo", null,
            onClick = { vm.clearChat() }
        )
    }
}
