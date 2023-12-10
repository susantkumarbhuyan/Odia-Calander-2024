package com.odiacalander.newcalender.compose


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.odiacalander.R
import com.odiacalander.core.util.CalendarManager
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.models.states.CalendarStatus
import com.odiacalander.newcalender.core.CalendarDay
import com.odiacalander.newcalender.core.CalendarMonth
import com.odiacalander.newcalender.core.DayPosition
import com.odiacalander.ui.theme.color1
import com.odiacalander.ui.theme.color7
import com.odiacalander.ui.viewmodels.CalendarViewModel

@Suppress("FunctionName")
internal fun LazyListScope.CalendarMonths(
    monthCount: Int,
    monthData: (offset: Int) -> CalendarMonth,
    contentHeightMode: ContentHeightMode,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)?,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)?
) {
    items(
        count = monthCount,
        key = { offset -> monthData(offset).yearMonth },
    ) { offset ->
        val context = LocalContext.current
        var newMData = monthData(offset)
        Log.d("LANG_ID :", PreferenceUtil.getLanguageNumber().toString())
        val monthId =
            "${PreferenceUtil.getLanguageNumber()}${newMData.yearMonth.monthValue}${newMData.yearMonth.year}".toInt()

        val m1 = CalendarManager.loadMonthDetailsFromDB(context, monthId = monthId)
        newMData = newMData.copy(m1)
        Log.d("MONTHID :", monthId.toString())
        val fillHeight = when (contentHeightMode) {
            ContentHeightMode.Wrap -> false
            ContentHeightMode.Fill -> true
        }
        val hasContainer = monthContainer != null
        monthContainer.or(defaultMonthContainer)(newMData) {
            Column(
                modifier = Modifier
                    .then(if (hasContainer) Modifier.fillMaxWidth() else Modifier.fillParentMaxWidth())
                    .then(
                        if (fillHeight) {
                            if (hasContainer) Modifier.fillMaxHeight() else Modifier.fillParentMaxHeight()
                        } else {
                            Modifier.wrapContentHeight()
                        },
                    )
                    .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                monthHeader?.invoke(this, newMData)
                monthBody.or(defaultMonthBody)(newMData) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(if (fillHeight) Modifier.weight(1f) else Modifier.wrapContentHeight()),
                    ) {
                        val weekDays = if(newMData.weekDays.size ==6) filterLastExtraRowElement(newMData.weekDays) else newMData.weekDays
                        for (week in weekDays) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(if (fillHeight) Modifier.weight(1f) else Modifier.wrapContentHeight()),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                for (day in week) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clipToBounds(),
                                    ) {
                                        dayContent(day)
                                    }
                                }
                            }
                        }
                    }
                }


                Box {
                    Text(
                        text = stringResource(id = R.string.Festivals),
                        fontSize = 20.sp,
                        color = color1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = color7)
                            .padding(top = 5.dp, bottom = 5.dp),
                        textAlign = TextAlign.Center
                    )
                }
                monthFooter?.invoke(this, newMData)
            }
        }
    }
}

@Composable
private fun filterLastExtraRowElement(weekDays: List<List<CalendarDay>>): List<List<CalendarDay>> {
    val map = mutableMapOf<Int, CalendarDay>()
    val newList = mutableListOf<List<CalendarDay>>()
    weekDays[5].forEachIndexed { index, calendarDay ->
        if (calendarDay.position == DayPosition.MonthDate)
            map[index] = calendarDay
    }
    weekDays.forEachIndexed { index, calendarDays ->
        val mutableList = calendarDays.toMutableList()
        if (index == 0 && map.isNotEmpty()) {
            map.forEach { (t, u) ->
                mutableList[t] = u
            }
        }
        if (index != 5) {
            newList.add(index, mutableList.toList())
        }
    }
    return newList
}


private val defaultMonthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit) =
    { _, container -> container() }

private val defaultMonthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit) =
    { _, content -> content() }

private fun <T> T?.or(default: T) = this ?: default
