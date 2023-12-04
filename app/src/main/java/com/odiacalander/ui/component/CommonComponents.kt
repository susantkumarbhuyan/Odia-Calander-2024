package com.odiacalander.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.odiacalander.MainActivity
import com.odiacalander.R
import com.odiacalander.util.LANGUAGE
import com.odiacalander.util.PreferenceUtil
import com.odiacalander.util.PreferenceUtil.getLanguageConfiguration
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
    suspend fun setLanguage(selectedLanguage: Int) {
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
                        LocaleListCompat.getAdjustedDefault()[0]?.toLanguageTag().toString(),
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