package com.ajmir.transaction_impl

import com.ajmir.common.manager.DateManager
import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.Transaction
import com.ajmir.transaction.model.TransactionStatus
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction.model.Transactions
import com.ajmir.transaction_impl.remote.TransactionApi
import com.ajmir.transaction_impl.remote.model.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*

class TransactionsRepositoryImpl(
    private val transactionApi: TransactionApi,
    private val dateManager: DateManager
): TransactionRepository {

    override suspend fun getAll(url: String): Result<Transactions> = runCatching {
        transactionApi.getTransactions(url)
            .data
            .transactions
            .let { mapToEntity(it) }
    }


    private fun mapToEntity(response: List<TransactionResponse>): Transactions {
        return Transactions(response.mapNotNull(::mapToEntity))
    }

    private fun mapToEntity(response: TransactionResponse): Transaction? {
        return dateManager.parse(response.dateTime)
            ?.let { date ->
                Transaction(
                    id = response.transactionId,
                    amount = response.amount.amount,
                    currency = response.amount.currency,
                    date = date,
                    type = when (response.creditDebitIndicator.lowercase()) {
                        "credit" -> TransactionType.CREDIT
                        "debit" -> TransactionType.DEBIT
                        else -> TransactionType.UNKNOWN
                    },
                    status = when (response.status.lowercase()) {
                        "booked" -> TransactionStatus.BOOKED
                        "canceled" -> TransactionStatus.CANCELED
                        else -> TransactionStatus.UNKNOWN
                    }
                )
            }
    }
}