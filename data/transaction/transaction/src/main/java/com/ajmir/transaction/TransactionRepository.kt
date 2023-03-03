package com.ajmir.transaction

import com.ajmir.transaction.model.TransactionDetail
import com.ajmir.transaction.model.Transactions

interface TransactionRepository {
    suspend fun getAll(url: String): Result<Transactions>
    fun getDetail(id: String): TransactionDetail?
}
