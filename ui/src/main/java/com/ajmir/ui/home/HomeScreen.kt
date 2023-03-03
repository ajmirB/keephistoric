package com.ajmir.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.ajmir.ui.commons.view.AppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onTransactionClicked: (String) -> Unit
) {
    val viewModel: HomeViewModel = get()
    val viewState by viewModel.viewState.collectAsState()
    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = viewModel::onRefresh
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Colors.background)
        .pullRefresh(pullRefreshState)
    ) {
        Column {
            AppBar()
            when (viewState) {
                is HomeViewState.Data -> {
                    HomeView(
                        viewState = viewState as HomeViewState.Data,
                        onAccountClicked = viewModel::onAccountClicked,
                        onTransactionClicked = onTransactionClicked
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
        if (viewState is HomeViewState.Data) {
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
