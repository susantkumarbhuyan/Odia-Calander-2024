package com.odiacalander.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.odiacalander.dataclasses.HomeItems
import com.odiacalander.screens.CalanderImgsScreen
import com.odiacalander.screens.CalendarScreen
import com.odiacalander.screens.FestivalsScreen
import com.odiacalander.screens.HolidayScreen
import com.odiacalander.screens.HomeScreen
import com.odiacalander.screens.HoroscopScreen
import com.odiacalander.screens.MuhurthaScreen
import com.odiacalander.ui.common.Route
import com.odiacalander.ui.common.animatedComposable


@Composable
fun HomeEntry(
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()


    val onBackPressed: () -> Unit = {
        with(navController) {
            if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                popBackStack()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        NavHost(
            modifier = Modifier
//                .fillMaxWidth(
//                    when (LocalWindowWidthState.current) {
//                        WindowWidthSizeClass.Compact -> 1f
//                        WindowWidthSizeClass.Expanded -> 0.5f
//                        else -> 0.8f
//                    }
//                )
                .align(Alignment.Center),
            navController = navController,
            startDestination = Route.HOME
        ) {
            animatedComposable(Route.HOME) {
                HomeScreen {
                    navController.navigate(it)
                }
            }
            animatedComposable(route = HomeItems.CalanderImgs.route) {
                CalanderImgsScreen()
            }
            animatedComposable(route = HomeItems.Calander.route) {
                CalendarScreen()
            }
            animatedComposable(route = HomeItems.Horoscop.route) {
                HoroscopScreen()
            }
            composable(route = HomeItems.Festivals.route) {
                FestivalsScreen()
            }
            composable(route = HomeItems.Muhurtha.route) {
                MuhurthaScreen()
            }
            composable(route = HomeItems.GovtHolidays.route) {
                HolidayScreen()
            }


        }
    }
}


