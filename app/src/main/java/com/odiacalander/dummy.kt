package com.odiacalander
//
//package com.odiacalander.screens
//
//import android.content.res.Configuration.UI_MODE_NIGHT_YES
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material.icons.outlined.AccountCircle
//import androidx.compose.material.icons.outlined.CheckCircle
//import androidx.compose.material.icons.outlined.Settings
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.SheetState
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.drawWithCache
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.graphics.BlendMode
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import com.odiacalander.AMABASYA_ID
//import com.odiacalander.FIRST_AKADASHI_ID
//import com.odiacalander.PURNIMA_ID
//import com.odiacalander.R
//import com.odiacalander.SECOND_AKADASHI_ID
//import com.odiacalander.calander
//import com.odiacalander.dataclasses.Date
//import com.odiacalander.dataclasses.getHomeItems
//import com.odiacalander.getCurrentDate
//import com.odiacalander.getDateData
//import com.odiacalander.getHolidaysList
//import com.odiacalander.ui.theme.color1
//import com.odiacalander.ui.theme.color2
//import com.odiacalander.ui.theme.color3
//import com.odiacalander.ui.theme.color4
//import com.odiacalander.ui.theme.color5
//import com.odiacalander.ui.theme.color6
//import com.odiacalander.ui.theme.color7
//import com.odiacalander.ui.theme.peach1
//import com.odiacalander.weeks
//import kotlinx.coroutines.launch
//import org.json.JSONObject
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun CalanderScreen() {
//    val months = JSONObject(calander).getJSONArray("months")
//    val pagerState = rememberPagerState(initialPage = 0) {
//        months.length()
//    }
//    HorizontalPager(state = pagerState) { index ->
//        CalanderBox(months[index] as JSONObject)
//    }
//}
//
//@ExperimentalMaterial3Api
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun DatesTable() {
//
//    val context = LocalContext.current
//
//    val rows = 6
//    val columns = 7
//    var weekCount = 0
////    FlowRow(
////        modifier = Modifier.padding(4.dp),
////        horizontalArrangement = Arrangement.spacedBy(4.dp),
////        maxItemsInEachRow = rows
////    )
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(rows),
//        modifier = Modifier.padding(start = 7.dp, end = 7.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//    )
//
//
//    {
//        val itemModifier = Modifier
//            .padding(2.dp)
//            .size(50.dp)
//            .clip(RoundedCornerShape(8.dp))
//        // repeat(rows * columns) {
//        items(rows * columns) {
//            if (it % 6 == 0) {
//                Box(
//                    modifier = itemModifier.background(weeks[weekCount].bgColor),
//                    Alignment.Center
//                ) {
//                    Text(
//                        text = weeks[weekCount].name,
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    )
//                }
//                weekCount++
//            } else {
//                val cDate = getDateData(0).find { item -> item.indexNo == it }
//                Box(
//                    modifier = itemModifier
//                        .clickable {
//                            Toast
//                                .makeText(
//                                    context,
//                                    cDate?.currentDateHolidays?.get(0) ?: "",
//                                    Toast.LENGTH_SHORT
//                                )
//                                .show()
//                            Log.d("Cal_Click", cDate?.currentDateHolidays?.get(0) ?: "")
//                        }
//                        .background(
//                            if (cDate?.isGovtHoliday == true || (1..5).contains(it)) color6 else color4
//                        )
//                        .border(
//                            width = if (cDate?.date == getCurrentDate()) 2.dp else 0.dp,
//                            color3,
//                            shape = RoundedCornerShape(8.dp)
//                        ),
//                    Alignment.Center
//                ) {
//                    Column {
//                        Text(
//                            text = cDate?.date ?: "",
//                            color = if (cDate?.date == getCurrentDate()) Color.Magenta else Color.Black,
//                            textAlign = TextAlign.Center,
//                            fontSize = 16.sp,
//                            modifier = Modifier.weight(1f),
//                        )
//                        when (cDate?.dayType) {
//                            AMABASYA_ID -> Icon(
//                                painter = painterResource(id = R.drawable.circle_24px),
//                                tint = color2,
//                                contentDescription = ""
//                            )
//
//                            PURNIMA_ID -> Icon(
//                                painter = painterResource(id = R.drawable.circle_24px_fill),
//                                modifier = Modifier
//                                    .graphicsLayer(alpha = 0.99f)
//                                    .drawWithCache {
//                                        onDrawWithContent {
//                                            drawContent()
//                                            drawRect(peach1, blendMode = BlendMode.SrcAtop)
//                                        }
//                                    },
//                                contentDescription = ""
//                            )
//
//                            FIRST_AKADASHI_ID -> Icon(
//                                painter = painterResource(id = R.drawable.dark_mode_24px),
//                                modifier = Modifier
//                                    .size(20.dp)
//                                    .rotate(180f),
//                                tint = color2,
//                                contentDescription = ""
//                            )
//
//                            SECOND_AKADASHI_ID -> Icon(
//                                painter = painterResource(id = R.drawable.clear_night_24px_fill),
//                                modifier = Modifier
//                                    .size(20.dp)
//                                    .rotate(180f),
//                                tint = color2,
//                                contentDescription = ""
//                            )
//
//                        }
//                    }
//
//                }
//            }
//
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun CalanderBox(currentMonth: JSONObject) {
//    Column(modifier = Modifier.background(color5)) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color5)
//                .padding(top = 20.dp, bottom = 15.dp),
//            Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    text = currentMonth.getString("name"),
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = color2
//                )
//                Spacer(modifier = Modifier.size(10.dp))
//                DatesTable()
//            }
//        }
//
//        Text(
//            text = "Fastivals", fontSize = 20.sp, color = color1, modifier = Modifier
//                .fillMaxWidth()
//                .background(color = color7)
//                .padding(top = 5.dp, bottom = 5.dp), textAlign = TextAlign.Center
//        )
//
//        val listState = rememberLazyListState()
//        LaunchedEffect(key1 = Unit) {
//            listState.animateScrollToItem(index = 49)
//        }
//        Spacer(modifier = Modifier.size(10.dp))
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(start = 7.dp, end = 7.dp),
//            state = listState
//        ) {
//            items(getHolidaysList(currentMonth).size) { item ->
//                Text(
//                    modifier = Modifier.padding(8.dp),
//                    text = getHolidaysList(currentMonth)[item],
//                    fontSize = 20.sp,
//                    color = color1
//                )
//            }
//        }
//    }
//}
//
//
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun DatesTablePreview() {
//    CalanderBox(JSONObject(calander).getJSONArray("months")[0] as JSONObject)
//}
//
//
