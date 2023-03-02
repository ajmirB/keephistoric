package com.ajmir.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajmir.account.AccountRepository
import com.ajmir.account.model.AccountEntity
import com.ajmir.common.manager.DateManager
import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.Transaction
import com.ajmir.transaction.model.TransactionStatus
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction.model.Transactions
import com.ajmir.ui.home.model.HomeAccountState
import com.ajmir.ui.home.model.HomeTransactionState
import com.ajmir.ui.home.model.HomeTransactionsState
import com.ajmir.ui.home.model.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val dateManager: DateManager
): ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    // region Lifecycle

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(HomeViewState.Loading)
            loadAccounts(skipError = false)
        }
    }

    // endregion

    // region User Interaction

    fun onRetryLoadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(HomeViewState.Loading)
            loadAccounts(skipError = false)
        }
    }

    fun onAccountClicked(id: String) {
        _viewState.update { state ->
            when (state) {
                is HomeViewState.Data -> {
                    val accounts = state.accounts
                    val currentAccount = accounts.firstOrNull { it.isSelected }
                    if (currentAccount?.id != id) {
                        // Get transactions
                        accounts.firstOrNull { it.id == id }
                            ?.also { selectedAccount -> loadTransactions(selectedAccount) }
                        // Update selected account
                        state.copy(accounts = accounts.map { it.copy(isSelected = it.id == id) },)
                    } else {
                        // The current account selected is the same as the asked one
                        state
                    }
                }
                else -> {
                    state
                }
            }
        }
    }

    fun onTransactionClicked(id: String) {

    }

    // endregion

    private suspend fun loadAccounts(
        skipError: Boolean = true
    ) {
        accountRepository.getAccounts()
            .onSuccess { accounts ->
                val accountModel = accounts.map(::mapAccount)
                val data = HomeViewState.Data(
                    accounts = accountModel,
                    transactions = null
                )
                // Load the transactions for the first account
                accountModel.firstOrNull()?.also { loadTransactions(it) }
                // Display the accounts
                _viewState.emit(data)
            }
            .onFailure {
                if (!skipError) {
                    _viewState.emit(HomeViewState.Error)
                }
            }
    }

    private fun loadTransactions(account: HomeAccountState) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { state ->
                when (state) {
                    is HomeViewState.Data -> {
                        val transactions = transactionRepository
                            .getAll(account.transactionUrl)
                            .let { result ->
                                if (result.getOrNull() != null) {
                                    mapTransactions(result.getOrNull()!!)
                                } else if (result.exceptionOrNull() != null){
                                    mapErrorTransactions(result.exceptionOrNull()!!)
                                } else {
                                    null
                                }
                            }
                        // Update the transactions for the selected account
                        state.copy(transactions = transactions)
                    }
                    else -> {
                        state
                    }
                }
            }
        }
    }

    // region Mapper

    private fun mapAccount(account: AccountEntity) =
        HomeAccountState(
            id = account.id,
            name = account.name,
            isSelected = false,
            transactionUrl = account.transactionUrl
        )

    private fun mapTransactions(transactions: Transactions): HomeTransactionsState {
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

    private fun mapErrorTransactions(error: Throwable): HomeTransactionsState {
        return HomeTransactionsState(hasError = true)
    }

    private fun mapTransaction(transaction: Transaction): HomeTransactionState {
        return HomeTransactionState(
            id = transaction.id,
            amount = "${transaction.amount} ${transaction.currency}",
            date = "${dateManager.formatDate(transaction.date)} - ${dateManager.formatTime(transaction.date)} "
        )
    }

    // endregion
}