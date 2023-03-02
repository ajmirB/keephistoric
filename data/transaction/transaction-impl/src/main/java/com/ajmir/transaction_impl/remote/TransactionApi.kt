package com.ajmir.transaction_impl.remote

import com.ajmir.data.retrofit.com.ajmir.retrofit.model.ApiResponse
import com.ajmir.transaction_impl.remote.model.TransactionsResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url

interface TransactionApi {
    @GET
    suspend fun getTransactions(@Url url: String): ApiResponse<TransactionsResponse>
}

fun provideTransactionApi(client: Retrofit): TransactionApi = client.create(TransactionApi::class.java)
