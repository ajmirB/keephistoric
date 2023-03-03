package com.ajmir.transaction.model

import java.util.*

data class TransactionDetail(
    val id: String,
    val reference: String,
    val amount: String,
    val currency: String,
    val date: Date,
    val type: TransactionType,
    val status: TransactionStatus,
    val information: String,
    val address: String? = null
)
