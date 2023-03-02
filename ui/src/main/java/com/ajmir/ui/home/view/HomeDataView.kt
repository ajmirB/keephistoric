package com.ajmir.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.home.model.HomeViewState

@Composable
fun HomeDataView(
    viewState: HomeViewState.Data,
    onAccountClicked: (String) -> Unit,
    onTransactionClicked: (String) -> Unit,
) {
    Column {
        if (viewState.accounts.isNotEmpty()) {
            HomeSection(
                text = "Accounts",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.backgroundSecondary)
                    .padding(
                        start = Dimens.Spacing.medium,
                        top = Dimens.Spacing.small
                    )
            )
            HomeAccountView(
                accounts = viewState.accounts,
                onAccountClicked = onAccountClicked
            )
        }
    }
}

@Composable
fun HomeSection(
    text: String,
    modifier: Modifier,
) = Text(
    text = text,
    fontSize = Dimens.FontSize.h3,
    color = Colors.secondary,
    modifier = modifier
)