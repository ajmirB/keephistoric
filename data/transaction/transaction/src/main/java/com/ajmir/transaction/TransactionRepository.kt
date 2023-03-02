package com.ajmir.transaction

import com.ajmir.transaction.model.Transactions

interface TransactionRepository {
    suspend fun getAll(url: String): Result<Transactions>
}