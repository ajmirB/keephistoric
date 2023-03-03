package com.ajmir.ui.commons.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.PreviewTheme

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false, onClick = {})
    ) {
        CircularProgressIndicator(
            color = Colors.loading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun LoadingViewPreview() {
    PreviewTheme {
        LoadingScreen()
    }
}
