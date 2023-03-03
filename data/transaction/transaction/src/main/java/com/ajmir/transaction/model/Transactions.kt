package com.ajmir.transaction.model

import java.util.*

data class Transactions(
    val transactions: List<Transaction>
)

data class Transaction(
    val id: String,
    val amount: String,
    val currency: String,
    val date: Date,
    val type: TransactionType,
    val status: TransactionStatus
)
