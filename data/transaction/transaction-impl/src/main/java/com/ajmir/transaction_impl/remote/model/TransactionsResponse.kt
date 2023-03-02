package com.ajmir.transaction_impl.remote.model

import com.google.gson.annotations.SerializedName

data class TransactionsResponse(
    @SerializedName("Transaction") val transactions: List<TransactionResponse>
)

data class TransactionResponse(
    @SerializedName("TransactionId") val transactionId: String,
    @SerializedName("TransactionReference") val reference: String,
    @SerializedName("CreditDebitIndicator") val creditDebitIndicator: String,
    @SerializedName("Status") val status: String,
    @SerializedName("ValueDateTime") val dateTime: String,
    @SerializedName("TransactionInformation") val information: String,
    @SerializedName("Amount") val amount: TransactionAmountResponse,
    @SerializedName("Address") val adress: String?,
)

data class TransactionAmountResponse(
    @SerializedName("Amount") val amount: String,
    @SerializedName("Currency") val currency: String
)
