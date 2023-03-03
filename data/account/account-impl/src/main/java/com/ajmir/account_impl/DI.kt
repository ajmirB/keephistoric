package com.ajmir.account_impl

import com.ajmir.account.AccountRepository
import com.ajmir.account_impl.remote.provideAccountApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val accountModule = module {
    factory { provideAccountApi(get()) }
    singleOf(::AccountRepositoryImpl) {
        bind<AccountRepository>()
    }
}
