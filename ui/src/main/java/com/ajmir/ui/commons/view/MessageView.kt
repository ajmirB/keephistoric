package com.ajmir.ui.commons.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape

@Composable
fun WarningMessageView(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Colors.warning,
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.warningBackground, RoundedShape)
            .padding(Dimens.Spacing.small)
    )
}

@Composable
fun ErrorMessageView(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Colors.error,
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.errorBackground, RoundedShape)
            .padding(Dimens.Spacing.small)
    )
}

@Preview
@Composable
fun MessagePreview() {
    Column {
        WarningMessageView(text = "This is a warning message")
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        ErrorMessageView(text = "This is an error message")
    }
}
