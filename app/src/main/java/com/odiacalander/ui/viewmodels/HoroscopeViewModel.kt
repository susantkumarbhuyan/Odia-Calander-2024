package com.odiacalander.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.states.HoroscopeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoroscopeViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {

    val horoscopeResponse: MutableState<HoroscopeState> = mutableStateOf(HoroscopeState.Empty)

    init {
        getDailyHoroscope()
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