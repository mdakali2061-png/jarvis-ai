package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.ai.MainViewModel
import com.jarvis.ai.ui.components.AvatarOrb
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.MicButton
import com.jarvis.ai.ui.components.ScreenHeader
import com.jarvis.ai.ui.components.Waveform
import com.jarvis.ai.ui.theme.TextMuted

@Composable
fun VoiceScreen(vm: MainViewModel) {
    val micActive by vm.micActive.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            "Voice Assistant",
            "Speech-to-text aur female voice (Phase 3)",
            Modifier.fillMaxWidth()
        )
        AvatarOrb(size = 180.dp, active = micActive)
        Waveform(active = micActive, modifier = Modifier.padding(horizontal = 24.dp))
        MicButton(active = micActive, onClick = { vm.toggleMic() })
        Text(
            "Abhi sirf animation demo hai. Asli mic aur awaaz Phase 3 mein aayegi.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMuted
        )
        Spacer(Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            FeatureCard(
                Icons.Filled.RecordVoiceOver, "Speech-to-Text",
                "Hindi + English voice recognition", "PHASE 3"
            )
            FeatureCard(
                Icons.Filled.Language, "Female Voice (TTS)",
                "Jarvis ki awaaz, beech mein rok bhi sakte ho", "PHASE 3"
            )
            FeatureCard(
                Icons.Filled.Stop, "Continuous Listening",
                "Sirf tumhare explicit control se on/off", "PHASE 3"
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}
