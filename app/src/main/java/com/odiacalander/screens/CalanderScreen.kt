package com.odiacalander.screens

import android.text.format.DateUtils.isToday
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.odiacalander.AMABASYA_ID
import com.odiacalander.util.DataManager
import com.odiacalander.FIRST_AKADASHI_ID
import com.odiacalander.PURNIMA_ID
import com.odiacalander.R
import com.odiacalander.SECOND_AKADASHI_ID
import com.odiacalander.components.Loading
import com.odiacalander.components.PopupWindowDialog
import com.odiacalander.dataclasses.Date
import com.odiacalander.dataclasses.MonthEntity
import com.odiacalander.getCurrentDate
import com.odiacalander.getCurrentMonth
import com.odiacalander.getCurrentYear
import com.odiacalander.ui.theme.color1
import com.odiacalander.ui.theme.color2
import com.odiacalander.ui.theme.color3
import com.odiacalander.ui.theme.color4
import com.odiacalander.ui.theme.color5
import com.odiacalander.ui.theme.color6
import com.odiacalander.ui.theme.color7
import com.odiacalander.ui.theme.peach1
import com.odiacalander.weeks


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen() {
    val context = LocalContext.current

    val pagerState =
        rememberPagerState(initialPage = DataManager.getMonthIndex(getCurrentMonth())) {
            DataManager.getMontCalendar().size
        }
    HorizontalPager(state = pagerState) { index ->
        Log.d("INDEX ---  ", index.toString())
        DataManager.loadAssetsFromJsonFile(context, index)
        if (DataManager.isDataLoaded) {
            CalendarBox(DataManager.data, index)
        } else {
            Loading()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarBox(currentMonth: MonthEntity, index: Int) {
    Log.d("MONTH ---  ", currentMonth.name)
    Column(modifier = Modifier.background(color5)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color5)
                .padding(top = 20.dp, bottom = 15.dp),
            Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentMonth.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = color2
                )
                Spacer(modifier = Modifier.size(10.dp))
                DatesTable(currentMonth.dates, index)
            }
        }

        Text(
            text = "Festivals", fontSize = 20.sp, color = color1, modifier = Modifier
                .fillMaxWidth()
                .background(color = color7)
                .padding(top = 5.dp, bottom = 5.dp), textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.size(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 7.dp, end = 7.dp)
        ) {
            items(currentMonth.holidays.size) { item ->
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = currentMonth.holidays[item],
                    fontSize = 20.sp,
                    color = color1
                )
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun DatesTable(dates: List<Date>, index: Int) {

    val rows = 6
    val columns = 7
    var weekCount = 0
    var clickedIndex by remember { mutableIntStateOf(0) }
    val openDialog = remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(rows),
        modifier = Modifier.padding(start = 7.dp, end = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    )
    {
        val itemModifier = Modifier
            .padding(2.dp)
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp))
        items(rows * columns) { index ->
            if (index % 6 == 0) {
                Box(
                    modifier = itemModifier.background(weeks[weekCount].bgColor),
                    Alignment.Center
                ) {
                    Text(
                        text = stringResource(weeks[weekCount].name),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                weekCount++
            } else {
                val cDate = dates.find { item -> item.indexNo == index }
                Box(
                    modifier = itemModifier
                        .clickable {
                            openDialog.value = !cDate?.currentDateHolidays.isNullOrEmpty()
                            clickedIndex = index
                            Log.d("Cal_Click", cDate?.currentDateHolidays.toString())
                        }
                        .background(
                            if (cDate?.isGovtHoliday == true || (1..5).contains(index)) color6 else color4
                        )
                        .border(
                            width = if (getCurrentDate() == cDate?.date) 2.dp else 0.dp,
                            color3,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    Alignment.Center
                ) {

                    if (clickedIndex == index) {
                        PopupWindowDialog(
                            openDialog.value,
                            cDate?.currentDateHolidays
                        ) { openDialog.value = false }
                    }

                    Column {
                        Text(
                            text = cDate?.date ?: "",
                            color = if (cDate?.date == getCurrentDate()) Color.Magenta else Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f),
                        )
                        when (cDate?.dayType) {
                            AMABASYA_ID -> Icon(
                                painter = painterResource(id = R.drawable.circle_24px),
                                tint = color2,
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
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DatesTablePreview() {
    CalendarScreen()
}


