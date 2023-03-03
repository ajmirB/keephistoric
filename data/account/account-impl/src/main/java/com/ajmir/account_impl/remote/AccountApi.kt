package com.ajmir.account_impl.remote

import com.ajmir.account_impl.remote.model.AccountsResponse
import com.ajmir.data.retrofit.com.ajmir.retrofit.model.ApiResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountApi {
    @GET("{id}")
    suspend fun getAccounts(
        @Path("id") id: String
    ) : ApiResponse<AccountsResponse>
}

fun provideAccountApi(client: Retrofit): AccountApi = client.create(AccountApi::class.java)
