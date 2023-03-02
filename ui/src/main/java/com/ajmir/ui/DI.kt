package com.ajmir.ui

import com.ajmir.ui.home.mapper.HomeMapper
import com.ajmir.ui.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val uiModule = module {
    singleOf(::HomeViewModel)
    singleOf(::HomeMapper)
}