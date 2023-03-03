package com.ajmir.ui.transaction.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ajmir.ui.R
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.resources.PreviewTheme
import com.ajmir.ui.transaction.model.TransactionViewState

@Composable
fun TransactionView(
    viewState: TransactionViewState.Data
) {
    LazyColumn {
        item {
            TransactionFieldView(
                stringResource(id = R.string.transaction_reference_label),
                viewState.reference
            )
        }
        item {
            TransactionFieldView(
                stringResource(id = R.string.transaction_amount_label),
                viewState.amount
            )
        }
        item {
            TransactionFieldView(
                stringResource(id = R.string.transaction_information_label),
                viewState.information
            )
        }
        viewState.address?.let {
            item {
                TransactionFieldView(
                    stringResource(id = R.string.transaction_address_label),
                    it
                )
            }
        }
    }
}

@Composable
private fun TransactionFieldView(
    label: String,
    value: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Colors.background)
        .padding(
            horizontal = Dimens.Spacing.medium + Dimens.Spacing.high,
            vertical = Dimens.Spacing.medium
        )

    ) {
        Text(
            text = label,
            color = Colors.section,
            fontSize = Dimens.FontSize.h3,
            modifier = Modifier.padding(bottom = Dimens.Spacing.verySmall)
        )
        Text(
            text = value,
            color = Colors.textDark,
            fontSize = Dimens.FontSize.h3
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
    PreviewTheme {
        TransactionFieldView(
            label = "Information",
            value = "Cashback from Patrick"
        )
    }
}
