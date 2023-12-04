package com.odiacalander.ui.component

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomModalNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    content: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {}
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = content
    ) {
        body()
    }
}