package com.ajmir.ui.commons.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape

@Composable
fun AppBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Logo()
    }
}

@Composable
fun TopBar(
    title: String,
    onBack: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.background)
            .padding(Dimens.Spacing.small)
    ) {
        Box(Modifier
            .clip(CircleShape)
            .clickable(onClick = onBack)
            .padding(Dimens.Spacing.small)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = Colors.primary
            )
        }
        Text(
            text = title,
            color = Colors.primary,
            fontSize = Dimens.FontSize.h2,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.medium),
    ) {
        AppBar()
        TopBar(
            title =" This is a title",
            onBack = {}
        )
    }
}
