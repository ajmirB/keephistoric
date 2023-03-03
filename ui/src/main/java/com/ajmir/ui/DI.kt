package com.ajmir.ui

import com.ajmir.ui.home.mapper.HomeMapper
import com.ajmir.ui.home.viewmodel.HomeViewModel
import com.ajmir.ui.transaction.mapper.TransactionMapper
import com.ajmir.ui.transaction.viewmodel.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    singleOf(::HomeViewModel)
    singleOf(::HomeMapper)

    viewModelOf(::TransactionViewModel)
    singleOf(::TransactionMapper)
}
