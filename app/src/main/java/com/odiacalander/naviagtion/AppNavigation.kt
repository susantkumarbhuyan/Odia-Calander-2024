package com.odiacalander.naviagtion

import androidx.compose.runtime.Composable
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

@Composable
fun AppNavigation(onClickTheme : () -> Unit) {
    val navController = rememberNavController()
    val destinations = "AppDestinations"
    NavHost(
        navController = navController,
        startDestination = "homeScreen"
    ) {
        composable(route = "homeScreen") {
            HomeScreen {
                navController.navigate(it)
            }
        }
        composable(route = HomeItems.CalanderImgs.route) {
            CalanderImgsScreen()
        }
        composable(route = HomeItems.Calander.route) {
            CalendarScreen()
        }
        composable(route = HomeItems.Horoscop.route) {
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