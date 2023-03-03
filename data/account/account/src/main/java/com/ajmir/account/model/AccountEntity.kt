package com.ajmir.account.model

data class AccountEntity(
    val id: String,
    val name: String,
    val isEnabled: Boolean,
    val type: AccountType,
    val subtype: AccountSubtype,
    val transactionUrl: String
)
