package com.jarvis.ai.core

data class ChatMessage(
    val id: Long,
    val text: String,
    val fromUser: Boolean
)
