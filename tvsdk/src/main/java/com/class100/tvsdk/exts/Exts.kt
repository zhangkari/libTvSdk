package com.class100.tvsdk.exts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

fun Context.isWifiConnected(): Boolean {
    val connManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networks = connManager.allNetworks
    if (networks == null || networks.isEmpty()) {
        return false;
    }

    for (n in networks) {
        if (connManager.getNetworkCapabilities(n)!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            && connManager.getNetworkInfo(n)!!.isConnected
        ) {
            return true
        }
    }
    return false
}


inline fun <reified T : Activity> Activity.start() {
    startActivity(Intent(this, T::class.java))
}