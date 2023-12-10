package com.odiacalander.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.states.CalendarImgState
import com.odiacalander.models.states.HoroscopeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarImgViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {

    val response: MutableState<CalendarImgState> = mutableStateOf(CalendarImgState.Empty)

    init {
        getMonthImgs()
    }

    private fun getMonthImgs() = viewModelScope.launch {
        repository.getMonthImgs().onStart {
            response.value = CalendarImgState.Loading
        }.catch {
            response.value = CalendarImgState.Failure(it)
        }.collect {
            response.value = CalendarImgState.Success(it)
        }
    }

}