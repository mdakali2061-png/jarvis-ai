package com.jarvis.ai

import android.app.Application
import android.speech.tts.TextToSpeech
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
import java.util.Locale

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

    private val _micActive = MutableStateFlow(false)
    val micActive: StateFlow<Boolean> = _micActive.asStateFlow()

    private var tts: TextToSpeech? = null
    private var ttsReady = false
    private var pendingSpeech: String? = null

    init {
        pendingSpeech = _messages.value.firstOrNull()?.text

        tts = TextToSpeech(app) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("hi", "IN"))
                    ?: TextToSpeech.LANG_NOT_SUPPORTED

                if (result < TextToSpeech.LANG_AVAILABLE) {
                    tts?.setLanguage(Locale.US)
                }

                ttsReady = true

                pendingSpeech?.let { message ->
                    pendingSpeech = null
                    speakNow(message)
                }
            }
        }
    }

    fun toggleMic() {
        _micActive.update { !it }
    }

    fun sendMessage(text: String) {
        val clean = text.trim()
        if (clean.isEmpty()) return

        _messages.update {
            it + ChatMessage(nextId++, clean, fromUser = true)
        }

        val reply = if (online.value) {
            "Gemini abhi connect nahi hai (Phase 2 mein hoga). Tab tak UI ka maza lo! ✨"
        } else {
            "Offline mode: Gemini ke bina main sirf basic local commands samajh paungi (Phase 8)."
        }

        _messages.update {
            it + ChatMessage(nextId++, reply, fromUser = false)
        }

        speakText(reply)
    }

    private fun speakText(message: String) {
        if (!ttsReady) {
            pendingSpeech = message
            return
        }

        speakNow(message)
    }

    private fun speakNow(message: String) {
        tts?.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "jarvis_${System.currentTimeMillis()}"
        )
    }

    fun clearChat() {
        _messages.value = emptyList()
    }

    override fun onCleared() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        super.onCleared()
    }
}
