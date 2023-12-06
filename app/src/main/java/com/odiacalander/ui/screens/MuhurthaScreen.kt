package com.odiacalander.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.odiacalander.core.util.AdsManager


@Composable
fun MuhurthaScreen() {
    var adStatus by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Column {
        Text(text = "MuhurthaScreen")
        Button(onClick = {
            if (adStatus) {
                AdsManager.showInterstitialAd()
                adStatus = false
            } else {
                AdsManager.loadInterstitialAd(context) {
                    adStatus = it
                }
            }
        }) {
            if (adStatus)
                Text(text = "Show Ads")
            else
                Text(text = "load Ads")
        }
    }


}

