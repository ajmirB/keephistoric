package com.ajmir.transaction_impl

import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.Transaction
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction.model.Transactions
import com.ajmir.transaction_impl.remote.TransactionApi
import com.ajmir.transaction_impl.remote.model.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*

class TransactionsRepositoryImpl(
    private val transactionApi: TransactionApi
): TransactionRepository {

    override suspend fun getAll(url: String): Result<Transactions> {
        return try {
            transactionApi.getTransactions(url)
                .data
                .transactions
                .let { Result.success(mapToEntity(it)) }
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    private fun mapToEntity(response: List<TransactionResponse>): Transactions {
        return Transactions(response.mapNotNull(::mapToEntity))
    }

    private fun mapToEntity(response: TransactionResponse): Transaction? {
        return parseDate(response.dateTime)
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
                    }
                )
            }
    }

    private fun parseDate(date: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
                .parse(date)
        } catch (e: Exception) {
            null
        }
    }
}