package com.odiacalander.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.odiacalander.dataclasses.Horoscope
import com.odiacalander.util.WebScarpingManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HoroscopeViewModel: ViewModel() {

    private val _apiResponse = MutableStateFlow<List<Horoscope>>( emptyList())
    val apiResponse: StateFlow<List<Horoscope>> get() = _apiResponse
    fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = WebScarpingManager.getDailyHoroscopeData()
            Log.d("HOROSCOP_DATAA", Gson().toJson( response) )
            _apiResponse.emit(response)
        }
    }
}