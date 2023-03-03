package com.ajmir.transaction_impl

import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction_impl.remote.provideTransactionApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val transactionModule = module {
    factory { provideTransactionApi(get()) }
    singleOf(::TransactionsRepositoryImpl) {
        bind<TransactionRepository>()
    }
}
