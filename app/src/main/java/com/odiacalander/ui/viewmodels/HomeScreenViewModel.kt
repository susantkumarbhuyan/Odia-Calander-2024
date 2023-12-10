package com.odiacalander.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odiacalander.core.util.getMonthIndexId
import com.odiacalander.data.CalenderRepository
import com.odiacalander.models.Month
import com.odiacalander.models.states.CalendarImgState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: CalenderRepository) :
    ViewModel() {

    val response: StateFlow<Month>
        get() = repository.monthImg

    init {
        viewModelScope.launch {
            repository.getCurrentMonthImg(getMonthIndexId())
        }

    }


}

