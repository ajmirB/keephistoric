package com.ajmir.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ajmir.ui.commons.resources.Colors
import com.ajmir.ui.commons.view.LoadingScreen
import com.ajmir.ui.home.model.HomeViewState
import com.ajmir.ui.commons.view.ErrorScreen
import com.ajmir.ui.commons.view.Logo
import com.ajmir.ui.home.view.HomeView
import com.ajmir.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.get
import com.ajmir.ui.R

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = get()
    val viewState by viewModel.viewState.collectAsState()

    Column {
        Logo()
        Box(Modifier.background(Colors.background)) {
            when (viewState) {
                is HomeViewState.Data -> {
                    HomeView(
                        viewState = viewState as HomeViewState.Data,
                        onAccountClicked = viewModel::onAccountClicked,
                        onTransactionClicked = viewModel::onTransactionClicked
                    )
                }
                HomeViewState.Error -> {
                    ErrorScreen(
                        title = stringResource(id = R.string.home_error_no_data_title),
                        message = stringResource(id = R.string.home_error_no_data_message),
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