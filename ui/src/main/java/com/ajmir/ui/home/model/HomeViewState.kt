package com.ajmir.ui.home.model

sealed interface HomeViewState {
    object Loading: HomeViewState

    data class Data(
        val accounts: List<HomeAccount>
    ): HomeViewState

    object Error: HomeViewState
}

data class HomeAccount(
    val id: String,
    val name: String
)