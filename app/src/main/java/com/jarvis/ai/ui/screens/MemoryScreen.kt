package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.GlassCard
import com.jarvis.ai.ui.components.ScreenHeader
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary

@Composable
fun MemoryScreen() {
    // Phase 1: switch is visual only. Persistence (Room + DataStore) arrives in Phase 8.
    var memoryEnabled by remember { mutableStateOf(true) }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenHeader("Memory", "Tum decide karo Jarvis kya yaad rakhe (Phase 8)")
        GlassCard(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Memory enabled", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    Text(
                        "Off karne par kuch bhi save nahi hoga",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
                Spacer(Modifier.width(8.dp))
                Switch(checked = memoryEnabled, onCheckedChange = { memoryEnabled = it })
            }
        }
        FeatureCard(
            Icons.Filled.Favorite, "Preferences",
            "Naam, pasand-napasand, baat karne ka style", "PHASE 8"
        )
        FeatureCard(
            Icons.Filled.Alarm, "Reminders & Notes",
            "Offline bhi kaam karenge", "PHASE 8"
        )
        FeatureCard(
            Icons.Filled.Contacts, "Frequent Contacts",
            "Sirf tumhari ijaazat se", "PHASE 8"
        )
        FeatureCard(
            Icons.Filled.Edit, "View / Edit / Delete",
            "Poori memory par tumhara control", "PHASE 8"
        )
    }
}
