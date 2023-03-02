package com.ajmir.ui.transaction

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.ajmir.ui.R
import com.ajmir.ui.commons.view.ErrorMessageView
import com.ajmir.ui.commons.view.TopBar
import com.ajmir.ui.transaction.model.TransactionParams
import com.ajmir.ui.transaction.model.TransactionViewState
import com.ajmir.ui.transaction.view.TransactionView
import com.ajmir.ui.transaction.viewmodel.TransactionViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TransactionDetailScreen(
    id: String,
    onClose: () -> Unit
) {
    val viewModel = getViewModel<TransactionViewModel>(
        parameters = { parametersOf(TransactionParams(id = id)) }
    )
    val viewState by viewModel.viewState
    Log.e("test", "TransactionDetailScreen: $viewState", )
    Column {
        TopBar(
            title = stringResource(id = R.string.transaction_title),
            onBack = onClose
        )
        when(viewState) {
            is TransactionViewState.Data ->
                TransactionView(viewState = viewState as TransactionViewState.Data)
            TransactionViewState.Error ->
                ErrorMessageView(text = "Sorry, we couldn't display your transaction detail.")
        }
    }
}
