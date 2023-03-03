package com.ajmir.transaction_impl

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.ajmir.common.manager.DateManager
import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.*
import com.ajmir.transaction_impl.remote.TransactionApi
import com.ajmir.transaction_impl.remote.model.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*

class TransactionsRepositoryImpl(
    private val transactionApi: TransactionApi,
    private val dateManager: DateManager
): TransactionRepository {

    private val lastLoadedTransactions = mutableMapOf<String, TransactionResponse>()

    override suspend fun getAll(url: String): Result<Transactions> = runCatching {
        transactionApi.getTransactions(url)
            .data
            .transactions
            .also { transactions ->
                lastLoadedTransactions.clear()
                transactions.forEach {
                    lastLoadedTransactions[it.transactionId] = it
                }
            }
            .let { mapToEntity(it) }
    }

    override fun getDetail(id: String): TransactionDetail? {
        Log.e("test", "getDetail: $lastLoadedTransactions", )
        return lastLoadedTransactions[id]
            ?.let { mapToDetailEntity(it)}
    }

    @VisibleForTesting
    fun mapToEntity(response: List<TransactionResponse>): Transactions {
        return Transactions(response.mapNotNull(::mapToEntity))
    }

    @VisibleForTesting
    fun mapToEntity(response: TransactionResponse): Transaction? {
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

    @VisibleForTesting
    fun mapToDetailEntity(response: TransactionResponse): TransactionDetail? {
        return dateManager.parse(response.dateTime)
            ?.let { date ->
                TransactionDetail(
                    id = response.transactionId,
                    reference = response.reference,
                    information = response.information,
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
                    },
                    address = response.adress
                )
            }
    }
}
