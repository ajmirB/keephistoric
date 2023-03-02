package com.ajmir.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajmir.account.AccountRepository
import com.ajmir.account.model.AccountEntity
import com.ajmir.ui.home.model.HomeAccount
import com.ajmir.ui.home.model.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val accountRepository: AccountRepository,
): ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState = _viewState.asStateFlow()

    // region Lifecycle

    fun onViewVisible() {
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

    }

    fun onTransactionClicked(id: String) {

    }

    // endregion

    private suspend fun loadAccounts(
        skipError: Boolean = true
    ) {
        accountRepository.getAccounts()
            .onSuccess { accounts ->
                val resultAccounts = accounts.map(::mapToModel)
                val data = HomeViewState.Data(
                    accounts = resultAccounts
                )
                _viewState.emit(data)
            }
            .onFailure {
                if (!skipError) {
                    _viewState.emit(HomeViewState.Error)
                }
            }
    }

    private fun mapToModel(account: AccountEntity) =
        HomeAccount(
            id = account.id,
            name = account.name
        )
}