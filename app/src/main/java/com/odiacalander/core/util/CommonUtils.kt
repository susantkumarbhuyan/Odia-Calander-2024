package com.odiacalander.core.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


object CommonUtils {

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
        val currentNetwork = connectivityManager?.activeNetwork
        return currentNetwork != null
    }
}