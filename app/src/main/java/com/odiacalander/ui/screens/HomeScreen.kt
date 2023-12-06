package com.odiacalander.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.odiacalander.R
import com.odiacalander.models.Month
import com.odiacalander.models.getHomeItems
import com.odiacalander.ui.common.LocalDarkTheme
import com.odiacalander.ui.common.PreferenceSwitch
import com.odiacalander.ui.component.CustomDropDownMenu
import com.odiacalander.core.util.DarkThemePreference.Companion.OFF
import com.odiacalander.core.util.DarkThemePreference.Companion.ON
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.getCurrentDate
import com.odiacalander.core.util.getCurrentYear
import com.odiacalander.core.util.getMonthIndexId
import com.odiacalander.core.util.localeMonths
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(monthList: List<Month>, onClick: (route: String) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            var isSelected by remember {
                mutableStateOf(false)
            }
            val isDarkTheme = LocalDarkTheme.current.isDarkTheme()
            ModalDrawerSheet {
                PreferenceSwitch(title = stringResource(id = R.string.dark_theme),
                    icon = if (isDarkTheme) Icons.Outlined.ArrowBack else Icons.Outlined.ArrowForward,
                    isChecked = isDarkTheme,
                    description = LocalDarkTheme.current.getDarkThemeDesc(),
                    onChecked = { PreferenceUtil.modifyDarkThemePreference(if (isDarkTheme) OFF else ON) },
                    onClick = { })
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = isSelected,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.Black,
                        selectedTextColor = Color.Yellow
                    ),
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Setting"
                        )
                    },
                    onClick = { isSelected = true }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item1") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                BuildTopBar(scrollBehavior) {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                if (monthList.isNotEmpty()) {
                    Card(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "${getCurrentDate()} - ${stringResource(localeMonths[monthList[getMonthIndexId()].monthId]!!)} - ${getCurrentYear()}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3)

                    ) {
                        items(getHomeItems().size) { item ->

                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                ),
                                modifier = Modifier
                                    .clickable { onClick(getHomeItems()[item].route) }
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    AsyncImage(
                                        model = if (item == 0) monthList[item].image else getHomeItems()[item].image,
                                        modifier = Modifier
                                            .size(70.dp)
                                            .padding(start = 6.dp, top = 6.dp, end = 6.dp),
                                        contentDescription = ""
                                    )

                                    Text(
                                        text = stringResource(id = if (item == 0) localeMonths[monthList[getMonthIndexId()].monthId]!! else getHomeItems()[item].title),
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(
                                            start = 6.dp,
                                            bottom = 6.dp,
                                            end = 6.dp
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildTopBar(scrollBehavior: TopAppBarScrollBehavior, onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.Yellow,
        ),
        title = {
            Text(
                "Odia Calender",
            )
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            CustomDropDownMenu()
        },
        scrollBehavior = scrollBehavior,
    )
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPrv() {
    // HomeScreen(months = emptyList<Month>()) {}
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun demo() {

}