package com.class100.tvsdk.exts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings

fun Context.gotoWifiSetting() {
    val wifiIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
    if (packageManager.resolveActivity(wifiIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
        startActivity(wifiIntent)
        return
    }

    val netIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
    if (packageManager.resolveActivity(netIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
        startActivity(netIntent)
        return
    }
}

inline fun <reified T : Activity> Activity.start() {
    startActivity(Intent(this, T::class.java))
}