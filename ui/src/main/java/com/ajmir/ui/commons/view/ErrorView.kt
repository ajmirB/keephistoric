package com.ajmir.ui.commons.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.R
import com.ajmir.ui.commons.resources.Colors

@Composable
fun ErrorScreen(
    title: String,
    message: String,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Colors.background)
    ) {
        Box(modifier = Modifier
            .align(Center)
            .padding(Dimens.Spacing.medium)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(Dimens.Spacing.medium)
            ) {
                ErrorImage()

                Text(
                    text = title,
                    color = Colors.error,
                    fontSize = Dimens.FontSize.h1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = Dimens.Spacing.small)
                )
                Text(
                    text = message,
                    fontSize = Dimens.FontSize.h3,
                    modifier = Modifier.padding(bottom = Dimens.Spacing.medium)
                )
                Button(onClick = onRetry) {
                    Text(
                        text = stringResource(id = R.string.error_retry),
                        fontSize = Dimens.FontSize.button,
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorImage() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Colors.error.copy(alpha= 0.5f)),
            modifier = Modifier
                .size(150.dp)
                .align(Center)
        )
        Box(
            modifier = Modifier
                .align(Center)
                .size(155.dp)
                .border(5.dp, Colors.error.copy(alpha= 0.3f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Center)
                .size(165.dp)
                .border(5.dp, Colors.error.copy(alpha= 0.2f), CircleShape)
        )
    }
}
