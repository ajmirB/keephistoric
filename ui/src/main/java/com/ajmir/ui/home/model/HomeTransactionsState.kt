package com.ajmir.ui.home.model

data class HomeTransactionsState(
    val credits: List<HomeTransactionState>,
    val debits: List<HomeTransactionState>,
)

data class HomeTransactionState(
    val id: String,
    val amount: String,
    val date: String
)