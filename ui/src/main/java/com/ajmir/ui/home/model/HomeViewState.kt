package com.ajmir.ui.home.model

sealed interface HomeViewState {
    object Loading: HomeViewState

    data class Data(
        val accounts: List<HomeAccount>,
        val transactions: HomeTransactions?
    ): HomeViewState

    object Error: HomeViewState
}