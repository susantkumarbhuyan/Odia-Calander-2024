package com.odiacalander.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.odiacalander.core.util.WebScarpingManager
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.Horoscope
import com.odiacalander.models.HoroscopeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoroscopeViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {

    private val _apiResponse = MutableStateFlow<List<Horoscope>>(emptyList())
    val apiResponse: StateFlow<List<Horoscope>> get() = _apiResponse

    val horoscopeResponse: MutableState<HoroscopeState> = mutableStateOf(HoroscopeState.Empty)

    init {
        getDailyHoroscope()
    }

    fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = WebScarpingManager.getDailyHoroscopeData()
            Log.d("HOROSCOP_DATAA", Gson().toJson(response))
            _apiResponse.emit(response)
        }
    }


    private fun getDailyHoroscope() = viewModelScope.launch {
        repository.getHoroscope().onStart {
            horoscopeResponse.value = HoroscopeState.Loading
        }.catch {
            horoscopeResponse.value = HoroscopeState.Failure(it)
        }.collect {
            horoscopeResponse.value = HoroscopeState.Success(it)
        }
    }
}