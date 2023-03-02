package com.ajmir.transaction

import com.ajmir.transaction.model.Transactions

interface TransactionRepository {
    suspend fun getSummary(url: String): Result<Transactions>
}