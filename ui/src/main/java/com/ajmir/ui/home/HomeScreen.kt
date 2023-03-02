package com.ajmir.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.resources.Dimens
import com.ajmir.ui.commons.view.LoadingScreen
import com.ajmir.ui.home.model.HomeViewState
import com.ajmir.ui.commons.view.ErrorScreen
import com.ajmir.ui.commons.view.Logo
import com.ajmir.ui.home.view.HomeDataView
import com.ajmir.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.get

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = get()
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onViewVisible()
    }

    Column {
        Logo()
        Box(Modifier.background(Colors.background)) {
            when (viewState) {
                is HomeViewState.Data -> {
                    HomeDataView(
                        viewState = viewState as HomeViewState.Data,
                        onAccountClicked = viewModel::onAccountClicked,
                        onTransactionClicked = viewModel::onTransactionClicked
                    )
                }
                HomeViewState.Error -> {
                    ErrorScreen(
                        title = "Oops!",
                        message = "Something went wrong, we couldn't retrieve your data",
                        onRetry = viewModel::onRetryLoadAccount
                    )
                }
                HomeViewState.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }

}