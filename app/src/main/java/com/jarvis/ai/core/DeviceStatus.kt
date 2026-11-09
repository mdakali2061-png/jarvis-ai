package com.jarvis.ai.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.BatteryManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

data class BatteryInfo(val percent: Int, val charging: Boolean)

/** Reads real internet + battery state. No special permissions beyond ACCESS_NETWORK_STATE. */
object DeviceStatus {

    fun isOnlineNow(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    fun onlineFlow(context: Context): Flow<Boolean> = callbackFlow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(isOnlineNow(context))
            }

            override fun onLost(network: Network) {
                trySend(isOnlineNow(context))
            }

            override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
                trySend(
                    caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                )
            }
        }
        trySend(isOnlineNow(context))
        cm.registerDefaultNetworkCallback(callback)
        awaitClose { cm.unregisterNetworkCallback(callback) }
    }.distinctUntilChanged()

    private fun parse(intent: Intent?): BatteryInfo {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, 100) ?: 100
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val pct = if (level >= 0 && scale > 0) (level * 100) / scale else 0
        val charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL
        return BatteryInfo(pct, charging)
    }

    fun batteryNow(context: Context): BatteryInfo {
        val sticky = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        return parse(sticky)
    }

    fun batteryFlow(context: Context): Flow<BatteryInfo> = callbackFlow {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                trySend(parse(intent))
            }
        }
        val sticky = context.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        trySend(parse(sticky))
        awaitClose { context.unregisterReceiver(receiver) }
    }.distinctUntilChanged()
}
