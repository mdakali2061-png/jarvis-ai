package com.jarvis.ai.core

import android.content.Context
import android.content.Intent
import android.widget.Toast

/** Opens an installed app by package name. Handles "not installed" gracefully. */
object AppLauncher {

    data class AppInfo(val label: String, val packageName: String)

    val YOUTUBE = AppInfo("YouTube", "com.google.android.youtube")
    val WHATSAPP = AppInfo("WhatsApp", "com.whatsapp")
    val INSTAGRAM = AppInfo("Instagram", "com.instagram.android")
    val FACEBOOK = AppInfo("Facebook", "com.facebook.katana")
    val MESSENGER = AppInfo("Messenger", "com.facebook.orca")
    val TELEGRAM = AppInfo("Telegram", "org.telegram.messenger")
    val TIKTOK = AppInfo("TikTok", "com.zhiliaoapp.musically")

    fun open(context: Context, app: AppInfo): Boolean {
        val intent: Intent? = context.packageManager.getLaunchIntentForPackage(app.packageName)
        return if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } else {
            Toast.makeText(context, "${app.label} installed nahi hai", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
