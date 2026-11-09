package com.jarvis.ai.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarvis.ai.ui.components.FeatureCard
import com.jarvis.ai.ui.components.ScreenHeader

@Composable
fun PhoneControlScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenHeader("Phone Control", "Jarvis tumhare phone ko control karegi, tumhari permission se")
        FeatureCard(
            Icons.Filled.TouchApp, "Screen Automation",
            "Tap, swipe, back, home, recents (AccessibilityService)", "PHASE 4"
        )
        FeatureCard(
            Icons.Filled.Apps, "Apps",
            "Installed apps search aur voice se launch", "PHASE 4"
        )
        FeatureCard(
            Icons.Filled.Call, "Calls",
            "Contact search, call (confirmation ke baad)", "PHASE 5"
        )
        FeatureCard(
            Icons.Filled.Sms, "SMS",
            "Message prepare aur send (confirmation ke baad)", "PHASE 5"
        )
        FeatureCard(
            Icons.Filled.PhotoCamera, "Camera",
            "Front/back camera, photo capture", "PHASE 6"
        )
        FeatureCard(
            Icons.Filled.PhotoLibrary, "Gallery",
            "Photos select, preview, share", "PHASE 6"
        )
        FeatureCard(
            Icons.Filled.Folder, "File Manager",
            "Search, PDFs, folders (Storage Access Framework)", "PHASE 6"
        )
    }
}
