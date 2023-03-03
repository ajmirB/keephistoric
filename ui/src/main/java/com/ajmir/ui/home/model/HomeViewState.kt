package com.ajmir.ui.home.model

sealed interface HomeViewState {
    object Loading: HomeViewState

    data class Data(
        val accounts: List<HomeAccountState>,
        val transactions: HomeTransactionsState?
    ): HomeViewState

    object Error: HomeViewState
}
