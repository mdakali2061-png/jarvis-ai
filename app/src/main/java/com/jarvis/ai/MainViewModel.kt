package com.jarvis.ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jarvis.ai.core.BatteryInfo
import com.jarvis.ai.core.ChatMessage
import com.jarvis.ai.core.DeviceStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val online: StateFlow<Boolean> = DeviceStatus.onlineFlow(app)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DeviceStatus.isOnlineNow(app))

    val battery: StateFlow<BatteryInfo> = DeviceStatus.batteryFlow(app)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DeviceStatus.batteryNow(app))

    private var nextId = 2L

    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = 1,
                text = "Hello jaan! Main Jarvis hoon, tumhari AI companion. Kaise ho? 💜",
                fromUser = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    /** Phase 1 only: the mic button animates the UI. Real speech recognition arrives in Phase 3. */
    private val _micActive = MutableStateFlow(false)
    val micActive: StateFlow<Boolean> = _micActive.asStateFlow()

    fun toggleMic() {
        _micActive.update { !it }
    }

    fun sendMessage(text: String) {
        val clean = text.trim()
        if (clean.isEmpty()) return
        _messages.update { it + ChatMessage(nextId++, clean, fromUser = true) }
        // Phase 1 stand-in reply. Phase 2 replaces this with the real Gemini service.
        val reply = if (online.value) {
            "Gemini abhi connect nahi hai (Phase 2 mein hoga). Tab tak UI ka maza lo! ✨"
        } else {
            "Offline mode: Gemini ke bina main sirf basic local commands samajh paungi (Phase 8)."
        }
        _messages.update { it + ChatMessage(nextId++, reply, fromUser = false) }
    }

    fun clearChat() {
        _messages.value = emptyList()
    }
}
