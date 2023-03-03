package com.ajmir.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ajmir.ui.R
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.home.model.HomeViewState

@Composable
fun HomeView(
    viewState: HomeViewState.Data,
    onAccountClicked: (String) -> Unit,
    onTransactionClicked: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ) {
        if (viewState.accounts.isNotEmpty()) {
            HomeSection(
                text = stringResource(id = R.string.home_section_account),
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
            viewState.transactions?.let {
                HomeTransactionsView(
                    transactions = it,
                    onTransactionClicked = onTransactionClicked
                )
            }
        }
    }
}

@Composable
fun HomeSection(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text.uppercase(),
    color = Colors.section,
    fontSize = Dimens.FontSize.h4,
    fontWeight = FontWeight.Bold,
    modifier = modifier
)
