package com.ajmir.common

import com.ajmir.common.manager.DateManager
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val commonModule = module {
    factoryOf(::DateManager)
}
