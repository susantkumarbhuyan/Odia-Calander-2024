package com.odiacalander.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.os.LocaleListCompat
import com.odiacalander.MainActivity
import com.odiacalander.R
import com.odiacalander.core.util.CommonUtils
import com.odiacalander.ui.theme.color2
import com.odiacalander.ui.theme.color3
import com.odiacalander.ui.theme.color8
import com.odiacalander.core.util.LANGUAGE
import com.odiacalander.core.util.PreferenceUtil
import com.odiacalander.core.util.PreferenceUtil.getLanguageConfiguration
import kotlinx.coroutines.launch


@Composable
fun NavigationBarSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier.height(
            with(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()) {
                if (this.value > 30f) this else 0f.dp
            }
        )
    )
}

@Composable
fun CustomDropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var language by remember { mutableIntStateOf(PreferenceUtil.getLanguageNumber()) }
    val scope = rememberCoroutineScope()
     fun setLanguage(selectedLanguage: Int) {
        language = selectedLanguage
        PreferenceUtil.encodeInt(LANGUAGE, language)
        MainActivity.setLanguage(getLanguageConfiguration())
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Image(
                painter = painterResource(id = R.drawable.google_translate_icon_1),
                contentDescription = "Localized description"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Odia") },
                onClick = {
                    scope.launch {
                        setLanguage(1)
                        Toast.makeText(context, "Odia", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    scope.launch {
                        setLanguage(2)
                        Toast.makeText(context, "English", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            DropdownMenuItem(
                text = { Text("Hindi") },
                onClick = {
                    scope.launch {
                        setLanguage(3)
                        Toast.makeText(context, "Hindi", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            DropdownMenuItem(
                text = { Text("CheckAppLang") },
                onClick = {

                    Toast.makeText(
                        context,
                        if(CommonUtils.isInternetConnected(context)) "Online" else "Offline",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            DropdownMenuItem(
                text = { Text("CheckStoredLang") },
                onClick = {
                    Toast.makeText(context, getLanguageConfiguration(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


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
        )
    }
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


@Composable
fun CalendarReset(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Refresh, "Floating action button.")
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Dummy() {
    dm(
        holidays = listOf("hjhjhjhjhj", "hjhjhjhjhj", "hjhjhjhjhj", "hjhjhjhjhj"),
    )
}
