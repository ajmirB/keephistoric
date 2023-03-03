package com.ajmir.ui.home.view

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ajmir.ui.R
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.view.ErrorMessageView
import com.ajmir.ui.commons.view.WarningMessageView
import com.ajmir.ui.home.model.HomeTransactionState
import com.ajmir.ui.home.model.HomeTransactionsState

@Composable
fun HomeTransactionsView(
    transactions: HomeTransactionsState,
    onTransactionClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = Dimens.Spacing.medium),
    ) {
        if (transactions.hasError) {
            ErrorMessageView(
                text = stringResource(id = R.string.home_transaction_error_message),
                modifier = Modifier.padding(horizontal = Dimens.Spacing.medium)
            )
        } else if (transactions.credits.isEmpty() && transactions.debits.isEmpty()) {
            WarningMessageView(
                text = stringResource(id = R.string.home_transaction_no_data_message),
                modifier = Modifier.padding(horizontal = Dimens.Spacing.medium)
            )
        } else {
            // Credit
            transactions.credits.takeIf { it.isNotEmpty() }
                ?.let { credits ->
                    // Sections
                    HomeSection(
                        text = stringResource(id = R.string.home_section_credit),
                        modifier = Modifier.padding(horizontal = Dimens.Spacing.medium)
                    )
                    // Items
                    credits.forEach {
                        HomeTransactionView(
                            transaction = it,
                            onClick = { onTransactionClicked(it.id) }
                        )
                    }
                }
            // Debit
            transactions.debits.takeIf { it.isNotEmpty() }
                ?.let { debits ->
                    // Section
                    HomeSection(
                        text = stringResource(id = R.string.home_section_debit),
                        modifier = Modifier
                            .padding(top = Dimens.Spacing.medium)
                            .padding(horizontal = Dimens.Spacing.medium)
                    )
                    // Items
                    debits.forEach {
                        HomeTransactionView(
                            transaction = it,
                            onClick = { onTransactionClicked(it.id) }
                        )
                    }
                }
        }
    }
}

@Composable
private fun HomeTransactionView(
    transaction: HomeTransactionState,
    onClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .background(Colors.background)
        .padding(
            horizontal = Dimens.Spacing.medium + Dimens.Spacing.high,
            vertical = Dimens.Spacing.medium
        )

    ) {
        Text(
            text = transaction.amount,
            color = Colors.title,
            fontSize = Dimens.FontSize.h2
        )
        Text(
            text = transaction.date,
            color = Colors.text
        )
    }
    Divider(
        color = Colors.backgroundSecondary,
        modifier = Modifier.padding(horizontal = Dimens.Spacing.medium)
    )
}

@Preview
@Composable
private fun TransactionPreview() {
    HomeTransactionView(
        transaction = HomeTransactionState(id = "", "15 EUR", date = "5 avril 2012 - 15:23"),
        onClick = {}
    )
}
