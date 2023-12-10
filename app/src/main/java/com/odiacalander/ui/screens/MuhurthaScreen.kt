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
fun MuhurthaScreen(adStatus :(Boolean) -> Unit) {

    val context = LocalContext.current
    Column {
        Text(text = "MuhurthaScreen")
        Button(onClick = {
            adStatus.invoke(true)
        }) {
                Text(text = "Show Ads")
        }
    }


}

