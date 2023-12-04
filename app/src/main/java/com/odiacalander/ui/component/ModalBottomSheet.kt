package com.odiacalander.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = SheetState(
        skipPartiallyExpanded = true,
        initialValue = SheetValue.Hidden
    ),
    onDismissRequest: () -> Unit,
    horizontalPadding: PaddingValues = PaddingValues(horizontal = 28.dp),
    content: @Composable ColumnScope.() -> Unit = {},
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Column(modifier = Modifier.padding(paddingValues = horizontalPadding)) {
            content()
            Spacer(modifier = Modifier.height(28.dp))
        }
        NavigationBarSpacer(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        )

    }
}