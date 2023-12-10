package com.odiacalander.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.odiacalander.core.util.getCurrentMonth
import com.odiacalander.core.util.localeMonths
import com.odiacalander.core.util.localeYears
import com.odiacalander.models.states.FestivalsState
import com.odiacalander.ui.viewmodels.FestivalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalsScreen() {
    val mon = remember {
        mutableIntStateOf(getCurrentMonth())
    }
    val viewModel = hiltViewModel<FestivalsViewModel>()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text("Festivals")
                }
            }
        )
    }
    ) { it ->
        when (val result = viewModel.response.value) {
            FestivalsState.Empty -> CircularProgressIndicator()
            is FestivalsState.Failure -> Text(text = result.msg.toString())
            FestivalsState.Loading -> CircularProgressIndicator()
            is FestivalsState.Success -> {
                Column(modifier = Modifier.padding(it)) {
                    if (result.data.isNotEmpty()) {
                        result.data.forEach { month ->
                            Column {
                                StickyHeader(stringResource(id = localeMonths[month.month]!!))

                                if (month.festivals.isNotEmpty()) {
                                    FestivalList(month.toNewMonth())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StickyHeader(categoryType: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Text(
            text = categoryType,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


