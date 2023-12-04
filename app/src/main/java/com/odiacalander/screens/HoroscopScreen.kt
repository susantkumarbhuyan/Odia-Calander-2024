package com.odiacalander.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.odiacalander.ui.theme.color1
import com.odiacalander.viewmodels.HoroscopeViewModel

@Composable
fun HoroscopeScreen() {
    TestLanguage()
}


@Composable
fun TestLanguage() {
    val viewModel: HoroscopeViewModel = viewModel()
    val apiResponse = viewModel.apiResponse.collectAsState()

    viewModel.fetchDataFromApi()
    if ((apiResponse.value.size) > 0) {
        val dailyHoroscope = apiResponse.value
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 7.dp, end = 7.dp)
        ) {
            items(dailyHoroscope.size) { item ->
                Row {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = dailyHoroscope[item].rashi,
                        fontSize = 20.sp,
                        color = color1
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = dailyHoroscope[item].details,
                        fontSize = 20.sp,
                        color = color1
                    )
                    if (item != dailyHoroscope.size - 1)
                        Divider(
                            thickness = 3.dp, color = Color.Yellow
                        )
                }

            }
        }
    } else {
        // Loading()
    }
}
