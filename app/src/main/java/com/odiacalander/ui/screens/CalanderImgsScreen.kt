package com.odiacalander.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.odiacalander.models.Month
import com.odiacalander.core.util.getMonthIndexId
import com.odiacalander.core.util.localeMonths
import com.odiacalander.core.util.localeYears
import com.odiacalander.models.states.CalendarImgState
import com.odiacalander.ui.component.CalendarReset
import com.odiacalander.ui.viewmodels.CalendarImgViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalendarImgsScreen(viewModel: CalendarImgViewModel) {

    val monthList = remember {
        mutableStateOf<List<Month>>(emptyList())
    }


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val initialPage = getMonthIndexId()

    val pagerState = rememberPagerState(
        initialPage = initialPage
    ) {
        monthList.value.size
    }
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
                    pagerState.animateScrollToPage(page = initialPage)
                }
            }
        }
    ) {
        when (val response = viewModel.response.value) {
            is CalendarImgState.Success -> {
                monthList.value = response.data
                CalenderImgComp(
                    modifier = Modifier.padding(it),
                    pagerState = pagerState,
                    months = response.data
                )
            }

            CalendarImgState.Loading -> CircularProgressIndicator()
            is CalendarImgState.Failure -> Text(text = "${response.msg}")

            CalendarImgState.Empty -> TODO()

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalenderImgComp(modifier: Modifier, pagerState: PagerState, months: List<Month>) {
//    val months = monthList()


    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var scale by remember { mutableFloatStateOf(1f) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale *= zoomChange
        // Limit the zoom levels within a certain range (optional).
        scale = scale.coerceIn(1f, 3f)
        // Update the offset to implement panning when zoomed.
        offset = if (scale == 1f) Offset(0f, 0f) else offset + offsetChange
    }
    var zoomReset by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            scale = 1f
            offset = Offset(0f, 0f)
            Log.d("Page change", "Page changed to $page")
        }
    }
    HorizontalPager(state = pagerState, modifier = modifier) { index ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row {
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "")
                }
                Text(
                    text = " ${stringResource(localeMonths[months[index].monthId]!!)} - ${
                        stringResource(
                            id = localeYears[months[index].year]!!
                        )
                    } ",
                    fontSize = 23.sp,
                    modifier = Modifier.padding(6.dp)
                )
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "")
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clipToBounds()
                    .clip(RectangleShape)
                    .transformable(state)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                zoomReset = !zoomReset
                            }
                        )
                    }) {
                AsyncImage(
                    model = months[index].image,
                    contentDescription = "",
                    modifier = Modifier
                        .size(450.dp)
                        .graphicsLayer(
                            scaleX = if (zoomReset) 1f else scale,
                            scaleY = if (zoomReset) 1f else scale,
                            translationX = if (zoomReset) 1f else offset.x,
                            translationY = if (zoomReset) 1f else offset.y

                        )
                )
//                ZoomableImage(
//                    painter = painterResource(id = months[index].image),
//                    isRotation = false,
//                    modifier = Modifier.fillMaxWidth().size(450.dp),
//                    pagerState = pagerState
//                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CalanderScreenPrv() {
    // CalendarImgsScreen(null!!)
}


