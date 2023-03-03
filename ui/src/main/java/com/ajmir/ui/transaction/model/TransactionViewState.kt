package com.ajmir.ui.transaction.model


sealed interface TransactionViewState {

    object Error: TransactionViewState

    data class Data(
        val id: String,
        val reference: String,
        val amount: String,
        val information: String,
        val address: String? = null
    ): TransactionViewState
}
