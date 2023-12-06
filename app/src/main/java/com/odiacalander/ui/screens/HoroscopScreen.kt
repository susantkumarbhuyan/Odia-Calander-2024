package com.odiacalander.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odiacalander.models.HoroscopeState
import com.odiacalander.ui.theme.color1
import com.odiacalander.ui.viewmodels.HoroscopeViewModel

@Composable
fun HoroscopeScreen(viewModel:HoroscopeViewModel) {
    TestLanguage(viewModel = viewModel)
}


@Composable
fun TestLanguage(viewModel:HoroscopeViewModel) {
    when (val dailyHoroscope = viewModel.horoscopeResponse.value) {
        is HoroscopeState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 7.dp, end = 7.dp)
            ) {
                items(dailyHoroscope.data.size) { item ->
                    Row {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = dailyHoroscope.data[item].rashi,
                            fontSize = 20.sp,
                            color = color1
                        )
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = dailyHoroscope.data[item].details,
                            fontSize = 20.sp,
                            color = color1
                        )
                        if (item != dailyHoroscope.data.size - 1)
                            Divider(
                                thickness = 3.dp, color = Color.Yellow
                            )
                    }
                }
            }
        }

        is HoroscopeState.Failure -> {
            Text(text = "${dailyHoroscope.msg}")
        }

        HoroscopeState.Loading -> {
            CircularProgressIndicator()
        }
        HoroscopeState.Empty -> TODO()
    }
}
