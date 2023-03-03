package com.ajmir.ui.home.mapper

import com.ajmir.account.model.AccountEntity
import com.ajmir.common.manager.DateManager
import com.ajmir.transaction.model.Transaction
import com.ajmir.transaction.model.TransactionStatus
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction.model.Transactions
import com.ajmir.ui.home.model.HomeAccountState
import com.ajmir.ui.home.model.HomeTransactionState
import com.ajmir.ui.home.model.HomeTransactionsState

class HomeMapper(
    private val dateManager: DateManager
) {

    fun mapAccount(account: AccountEntity) =
        HomeAccountState(
            id = account.id,
            name = account.name,
            isSelected = false,
            transactionUrl = account.transactionUrl
        )

    fun mapTransactions(transactions: Transactions): HomeTransactionsState {
        val nbToTake = 2
        return HomeTransactionsState(
            credits = transactions.transactions
                .filter { it.type == TransactionType.CREDIT && it.status != TransactionStatus.CANCELED }
                .sortedBy { it.date }
                .take(nbToTake)
                .map(::mapTransaction),
            debits = transactions.transactions
                .filter { it.type == TransactionType.DEBIT && it.status != TransactionStatus.CANCELED }
                .sortedBy { it.date }
                .take(nbToTake)
                .map(::mapTransaction)
        )
    }

    fun mapErrorTransactions(error: Throwable): HomeTransactionsState {
        return HomeTransactionsState(hasError = true)
    }

    fun mapTransaction(transaction: Transaction): HomeTransactionState {
        return HomeTransactionState(
            id = transaction.id,
            amount = "${transaction.amount} ${transaction.currency}",
            date = "${dateManager.formatDate(transaction.date)} - ${dateManager.formatTime(transaction.date)} "
        )
    }
}
