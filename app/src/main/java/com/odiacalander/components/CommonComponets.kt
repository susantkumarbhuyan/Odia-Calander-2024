package com.odiacalander.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.odiacalander.ui.theme.color2
import com.odiacalander.ui.theme.color3
import com.odiacalander.ui.theme.color8


@Composable
fun MinimalDialog(openDialog: Boolean, holidays: List<String>?, onClick: () -> Unit) {
    if (openDialog) {
        Dialog(
            onDismissRequest = onClick,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {

                    repeat(holidays?.size!!) { item ->
                        Text(
                            text = holidays[0],
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                            textAlign = TextAlign.Center,
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun Loading() {
    Box(modifier =Modifier.fillMaxSize() ){
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp).align(Alignment.Center),
        color = color2,
        trackColor = color3,
    )}
}

@Composable
fun PopupWindowDialog(openDialog: Boolean, holidays: List<String>?, onClick: () -> Unit) {

    val popupWidth = 300.dp
    val popupHeight = 100.dp
    if (openDialog) {
        Popup(
            onDismissRequest = onClick,
            alignment = Alignment.Center,
            properties = PopupProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(.7f),
                // .size(popupWidth, popupHeight)
                shape = RoundedCornerShape(7.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color3,
                    contentColor = color8
                ),
            ) {
                repeat(holidays?.size!!) { item ->
                    Text(
                        text = holidays[item],
                        textAlign = TextAlign.Center,
                    )
                }
            }

        }
    }
}

@Composable
fun dm(holidays: List<String>?) {
    val popupWidth = 300.dp
    val popupHeight = 100.dp

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(.7f),
        // .size(popupWidth, popupHeight)
        shape = RoundedCornerShape(7.dp),
        colors = CardDefaults.cardColors(
            containerColor = color3,
            contentColor = color8
        ),
    ) {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally).padding(15.dp)) {
            repeat(holidays?.size!!) { item ->
                Text(
                    text = holidays[item],
                    textAlign = TextAlign.Center,

                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Dummy() {
    dm(
        holidays = listOf("hjhjhjhjhj", "hjhjhjhjhj", "hjhjhjhjhj", "hjhjhjhjhj"),
    )
}

