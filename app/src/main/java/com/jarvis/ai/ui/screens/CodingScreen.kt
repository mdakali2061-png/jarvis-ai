package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.ScreenHeader
import com.jarvis.ai.ui.theme.TextPrimary
import com.jarvis.ai.ui.theme.VioletLight

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CodingScreen() {
    val languages = listOf("C", "C++", "Java", "Python", "HTML", "CSS", "JavaScript", "Kotlin", "SQL")
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenHeader("Coding Assistant", "Code likhna, samjhana, debug karna (Phase 7)")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            languages.forEach { lang ->
                AssistChip(
                    onClick = {},
                    label = { Text(lang, color = TextPrimary) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.White.copy(alpha = 0.07f)
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = VioletLight.copy(alpha = 0.4f)
                    )
                )
            }
        }
        FeatureCard(
            Icons.Filled.Code, "Generate & Explain",
            "Complete code, explanations, project structures", "PHASE 7"
        )
        FeatureCard(
            Icons.Filled.BugReport, "Debug",
            "Error paste karo, fix aur improvements pao", "PHASE 7"
        )
        FeatureCard(
            Icons.Filled.Terminal, "Termux Commands",
            "Commands generate honge, bina tumhari confirmation ke run nahi honge", "PHASE 7"
        )
    }
}
