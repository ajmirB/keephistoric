package com.ajmir.ui.home.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajmir.account.AccountRepository
import com.ajmir.transaction.TransactionRepository
import com.ajmir.ui.home.mapper.HomeMapper
import com.ajmir.ui.home.model.HomeAccountState
import com.ajmir.ui.home.model.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val mapper: HomeMapper
): ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

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

    fun onRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.update { true }
            val currentAccount = getCurrentAccount()
            loadAccounts(selectAccount = currentAccount)
            _isRefreshing.update { false }
        }
    }

    fun onAccountClicked(id: String) {
        _viewState.update { state ->
            when (state) {
                is HomeViewState.Data -> {
                    val accounts = state.accounts
                    val currentAccount = getCurrentAccount(accounts)
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

    // endregion

    @VisibleForTesting
    suspend fun loadAccounts(
        selectAccount: HomeAccountState? = null,
        skipError: Boolean = true
    ) {
        accountRepository.getAccounts()
            .onSuccess { accounts ->
                val accountModel = accounts.map(mapper::mapAccount)
                val data = HomeViewState.Data(
                    accounts = accountModel,
                    transactions = null
                )
                // Display the accounts
                _viewState.emit(data)
                onAccountClicked((selectAccount ?: accountModel.firstOrNull())?.id.orEmpty())
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
                                    mapper.mapTransactions(result.getOrNull()!!)
                                } else if (result.exceptionOrNull() != null){
                                    mapper.mapErrorTransactions(result.exceptionOrNull()!!)
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

    private fun getCurrentAccount(
        accounts: List<HomeAccountState>? = null
    ): HomeAccountState? {
        val loadedAccounts = accounts
            ?: _viewState.value.let {
                when (it) {
                    is HomeViewState.Data -> it.accounts
                    HomeViewState.Error -> emptyList()
                    HomeViewState.Loading -> emptyList()
                }
            }
        return loadedAccounts.firstOrNull { it.isSelected }
    }
}
