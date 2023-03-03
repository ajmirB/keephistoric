package com.ajmir.account_impl.remote.model

import com.google.gson.annotations.SerializedName

data class AccountsResponse(
    @SerializedName("Account") val accounts: List<AccountResponse>
)

data class AccountResponse(
    @SerializedName("Nickname") val nickname: String,
    @SerializedName("AccountId") val id: String,
    @SerializedName("AccountType") val type: String,
    @SerializedName("AccountSubType") val subType: String,
    @SerializedName("Status") val status: String,
    @SerializedName("Currency") val currency: String,
    @SerializedName("transactionsUrl") val transactionUrl: String
)
