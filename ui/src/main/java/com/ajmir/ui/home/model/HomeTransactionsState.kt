package com.ajmir.ui.home.model

data class HomeTransactionsState(
    val hasError: Boolean = false,
    val credits: List<HomeTransactionState> = emptyList(),
    val debits: List<HomeTransactionState> = emptyList(),
)

data class HomeTransactionState(
    val id: String,
    val amount: String,
    val date: String
)
