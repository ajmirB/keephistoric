package com.ajmir.retrofit

import com.ajmir.data.retrofit.RetrofitProvider
import com.google.gson.Gson
import org.koin.dsl.module

val networkingModule = module {
    factory { Gson() }
    factory { RetrofitProvider.provideOkhttp() }
    factory {
        RetrofitProvider.provideClient(
            baseUrl = BuildConfig.BASE_URL,
            gson = get(),
            okHttpClient = get()
        )
    }
}
