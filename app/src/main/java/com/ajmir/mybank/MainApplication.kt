package com.ajmir.mybank

import android.app.Application
import com.ajmir.account_impl.accountModule
import com.ajmir.common.commonModule
import com.ajmir.retrofit.networkingModule
import com.ajmir.transaction_impl.transactionModule
import com.ajmir.ui.uiModule
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(
                uiModule,
                commonModule,
                networkingModule,
                accountModule,
                transactionModule,
            )
        }
    }
}
