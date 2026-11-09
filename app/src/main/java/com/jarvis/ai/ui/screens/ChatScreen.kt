package com.jarvis.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jarvis.ai.MainViewModel
import com.jarvis.ai.core.ChatMessage
import com.jarvis.ai.ui.components.ScreenHeader
import com.jarvis.ai.ui.components.StatusChip
import com.jarvis.ai.ui.theme.Blue
import com.jarvis.ai.ui.theme.OfflineRed
import com.jarvis.ai.ui.theme.OnlineGreen
import com.jarvis.ai.ui.theme.TextMuted
import com.jarvis.ai.ui.theme.TextPrimary
import com.jarvis.ai.ui.theme.Violet
import com.jarvis.ai.ui.theme.VioletLight

@Composable
fun ChatScreen(vm: MainViewModel) {
    val messages by vm.messages.collectAsState()
    val online by vm.online.collectAsState()
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            ScreenHeader("Chat", "Jarvis se baat karo")
            StatusChip(
                label = if (online) "ONLINE" else "OFFLINE",
                dot = if (online) OnlineGreen else OfflineRed
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages, key = { it.id }) { msg -> Bubble(msg) }
        }

        Spacer(Modifier.size(8.dp))
        Row(
            Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Message likho...", color = TextMuted) },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = Violet,
                    unfocusedBorderColor = VioletLight.copy(alpha = 0.3f),
                    cursorColor = VioletLight
                ),
                maxLines = 4
            )
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Violet, Blue)))
                    .clickable {
                        vm.sendMessage(input)
                        input = ""
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color.White)
            }
        }
    }
}

@Composable
private fun Bubble(msg: ChatMessage) {
    val shape = if (msg.fromUser) {
        RoundedCornerShape(18.dp, 18.dp, 4.dp, 18.dp)
    } else {
        RoundedCornerShape(18.dp, 18.dp, 18.dp, 4.dp)
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.fromUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            Modifier
                .widthIn(max = 300.dp)
                .clip(shape)
                .background(
                    if (msg.fromUser) {
                        Brush.linearGradient(listOf(Violet, Blue))
                    } else {
                        Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.10f), Color.White.copy(alpha = 0.05f))
                        )
                    }
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(msg.text, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        }
    }
}
