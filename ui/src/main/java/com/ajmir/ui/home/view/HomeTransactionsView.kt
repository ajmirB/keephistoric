package com.ajmir.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.RoundedShape
import com.ajmir.ui.home.model.HomeTransaction
import com.ajmir.ui.home.model.HomeTransactions

@Composable
fun HomeTransactions(
    transactions: HomeTransactions,
    onTransactionClicked: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(all = Dimens.Spacing.medium),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.medium),
    ) {
        transactions.credits.takeIf { it.isNotEmpty() }
            ?.let { credits ->
                item { HomeSection("Credit") }
                items(items = credits) {
                    HomeTransaction(
                        transaction = it,
                        onClick = { onTransactionClicked(it.id) }
                    )
                }
            }

        transactions.debits.takeIf { it.isNotEmpty() }
            ?.let { debits ->
                item { HomeSection("Debit") }
                items(items = debits) {
                    HomeTransaction(
                        transaction = it,
                        onClick = { onTransactionClicked(it.id) }
                    )
                }
            }
    }
}

@Composable
private fun HomeTransaction(
    transaction: HomeTransaction,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()
        .background(Colors.backgroundSecondary, RoundedShape)
        .clickable(onClick = onClick)
        .padding(Dimens.Spacing.medium)
    ) {
        Text(
            text = transaction.amount,
            color = Colors.secondary,
            fontSize = Dimens.FontSize.h2
        )
        Text(
            text = transaction.date,
        )
    }

}