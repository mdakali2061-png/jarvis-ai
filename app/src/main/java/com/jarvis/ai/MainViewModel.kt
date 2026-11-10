package com.jarvis.ai

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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

        tts = TextToSpeech(app.applicationContext) { status ->
            val engine = tts

            if (status != TextToSpeech.SUCCESS || engine == null) {
                addVoiceMessage("Voice engine start nahi hua. Phone ka Text-to-Speech engine check karo.")
            } else {
                val hindiResult = engine.setLanguage(Locale("hi", "IN"))

                val languageResult = if (hindiResult >= TextToSpeech.LANG_AVAILABLE) {
                    hindiResult
                } else {
                    engine.setLanguage(Locale.US)
                }

                if (languageResult < TextToSpeech.LANG_AVAILABLE) {
                    addVoiceMessage("Hindi aur English voice available nahi hai. Phone mein TTS language install karo.")
                } else {
                    ttsReady = true
                    engine.setSpeechRate(1.0f)

                    engine.setOnUtteranceProgressListener(
                        object : UtteranceProgressListener() {
                            override fun onStart(utteranceId: String?) {}

                            override fun onDone(utteranceId: String?) {}

                            override fun onError(utteranceId: String?) {
                                addVoiceMessage("Voice playback error. Phone ka media volume aur TTS engine check karo.")
                            }
                        }
                    )

                    pendingSpeech?.let { message ->
                        pendingSpeech = null
                        speakNow(message)
                    }
                }
            }
        }
    }

    private fun addVoiceMessage(message: String) {
        _messages.update {
            it + ChatMessage(nextId++, message, fromUser = false)
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
        val result = tts?.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "jarvis_${System.currentTimeMillis()}"
        )

        if (result == TextToSpeech.ERROR) {
            addVoiceMessage("JARVIS voice play nahi kar pa rahi. TTS engine check karo.")
        }
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
