package com.ajmir.ui.commons.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ajmir.ui.R
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape

@Composable
fun Logo() {
    Text(
        text = stringResource(id = R.string.app_name),
        color = Colors.primary,
        fontSize = Dimens.FontSize.h1,
        fontWeight = FontWeight.Black,
        modifier = Modifier
            .padding(Dimens.Spacing.medium)
            .background(Colors.background, RoundedShape)
    )
}
