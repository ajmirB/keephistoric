package com.ajmir.ui.home.model

data class HomeTransactions(
    val credits: List<HomeTransaction>,
    val debits: List<HomeTransaction>,
)

data class HomeTransaction(
    val id: String,
    val amount: String,
    val date: String
)