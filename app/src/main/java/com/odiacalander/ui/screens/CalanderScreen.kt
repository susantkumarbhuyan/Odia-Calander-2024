package com.odiacalander.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odiacalander.core.constants.AMABASYA_ID
import com.odiacalander.core.constants.FIRST_AKADASHI_ID
import com.odiacalander.core.constants.PURNIMA_ID
import com.odiacalander.R
import com.odiacalander.core.constants.SECOND_AKADASHI_ID
import com.odiacalander.newcalender.compose.CalendarState
import com.odiacalander.newcalender.compose.HorizontalCalendar
import com.odiacalander.newcalender.compose.rememberCalendarState
import com.odiacalander.newcalender.core.CalendarDay
import com.odiacalander.newcalender.core.CalendarMonth
import com.odiacalander.newcalender.core.DayPosition
import com.odiacalander.newcalender.core.daysOfWeek
import com.odiacalander.ui.component.CalendarReset
import com.odiacalander.ui.component.PopupWindowDialog
import com.odiacalander.ui.theme.color1
import com.odiacalander.ui.theme.color2
import com.odiacalander.ui.theme.color3
import com.odiacalander.ui.theme.color4
import com.odiacalander.ui.theme.color6
import com.odiacalander.ui.theme.peach1
import com.odiacalander.core.util.localeMonths
import com.odiacalander.core.util.weeks
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val adjacentMonths: Long = 50
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }
    val context = LocalContext.current
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        BuildTopBar(scrollBehavior) {
            scope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
    },
        floatingActionButton = {
            CalendarReset {
                scope.launch {
                    state.animateScrollToMonth(currentMonth)
                }
            }
        }
    ) {
        LocalCalendar(modifier = Modifier.padding(it), state = state, daysOfWeek = daysOfWeek)
    }

}


@Composable
private fun LocalCalendar(modifier: Modifier, state: CalendarState, daysOfWeek: List<DayOfWeek>) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        HorizontalCalendar(
            modifier = Modifier.testTag("Calendar"),
            state = state,
            dayContent = { day ->
                Day(day)
            },
            monthHeader = {
                MonthName(it)
                MonthHeader(daysOfWeek = daysOfWeek)
            },
            monthFooter = {
                MonthFooter(it)
            }
        )

    }
}

@Composable
fun MonthFooter(calendarMonth: CalendarMonth) {
    val festivals = calendarMonth.newMonth?.festivals?.values?.flatten()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 7.dp, end = 7.dp)
    ) {
        if (festivals != null) {
            items(festivals.size) { item ->
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = festivals[item],
                    fontSize = 20.sp,
                    color = color1
                )
            }
        }
    }
}

@Composable
private fun MonthName(calendarMonth: CalendarMonth) {
    Text(
        text = "${stringResource(localeMonths[calendarMonth.yearMonth.month.value]!!)} ${calendarMonth.yearMonth.year}",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = color2
    )
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (dayOfWeek in daysOfWeek) {
            val itemModifier = Modifier
                .padding(2.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
            Box(
                modifier = itemModifier.background(weeks[dayOfWeek.value - 1].bgColor),
                Alignment.Center
            ) {
                Text(
                    text = stringResource(weeks[dayOfWeek.value - 1].name),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun Day(day: CalendarDay) {
    val itemModifier = Modifier
        .padding(2.dp)
        .size(50.dp)
        .border(
            width = if (day.dayData?.isCurrentData == true) 4.dp else 0.dp,
            color = color3,
            shape = RoundedCornerShape(8.dp)
        )
        .clip(RoundedCornerShape(8.dp))
    val openDialog = remember { mutableStateOf(false) }
    if (day.position == DayPosition.MonthDate) {
        Box(
            modifier = itemModifier
                .aspectRatio(1f) // This is important for square-sizing!
                .testTag("MonthDay")
                .background(
                    if (day.dayData?.isGovtHoliday == true)
                        color6
                    else
                        color4
                )
                .clickable(
                    enabled = true,
                    role = Role.Button,
                    onClick = {
                        openDialog.value = true
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column {

                PopupWindowDialog(
                    openDialog = openDialog.value,
                    holidays = day.dayData?.festivals
                ) { openDialog.value = false }
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (day.dayData?.isSunday == true) Color.Red else Color.Black,
                    fontSize = 14.sp,
                )
                when (day.dayData?.lunarDayType) {
                    AMABASYA_ID -> Icon(
                        painter = painterResource(id = R.drawable.circle_24px_fill),
                        tint = color1,
                        contentDescription = ""
                    )

                    PURNIMA_ID -> Icon(
                        painter = painterResource(id = R.drawable.circle_24px_fill),
                        modifier = Modifier
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(peach1, blendMode = BlendMode.SrcAtop)
                                }
                            },
                        contentDescription = ""
                    )

                    FIRST_AKADASHI_ID -> Icon(
                        painter = painterResource(id = R.drawable.dark_mode_24px),
                        modifier = Modifier
                            .size(20.dp),
                        tint = color2,
                        contentDescription = ""
                    )

                    SECOND_AKADASHI_ID -> Icon(
                        painter = painterResource(id = R.drawable.clear_night_24px_fill),
                        modifier = Modifier
                            .size(20.dp),
                        tint = color2,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Example1Preview() {
   // LocalCalendar(Modifier.padding(1.dp))
}

