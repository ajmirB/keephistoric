package com.ajmir.account_impl

import com.ajmir.account.AccountRepository
import com.ajmir.account.model.AccountEntity
import com.ajmir.account.model.AccountSubtype
import com.ajmir.account.model.AccountType
import com.ajmir.account_impl.remote.AccountApi
import com.ajmir.account_impl.remote.model.AccountResponse

class AccountRepositoryImpl(
    private val accountApi: AccountApi
) : AccountRepository {

    override suspend fun getAccounts(): Result<List<AccountEntity>> = runCatching {
        accountApi.getAccounts("ea42529b-1a24-4f3e-9ba4-8e6665666d6b")
            .data
            .accounts
            .map(::mapToEntity)
    }

    fun mapToEntity(account: AccountResponse) =
        AccountEntity(
            id = account.id,
            name = account.nickname,
            isEnabled = account.status.equals("enabled", true),
            type = when (account.type.lowercase()) {
                "personal" -> AccountType.PERSONAL
                else  ->AccountType.UNKNOWN
            },
            subtype = when (account.subType.lowercase()) {
                "currentaccount" -> AccountSubtype.CURRENT
                "savingaccount" -> AccountSubtype.SAVING
                else -> AccountSubtype.UNKNOWN
            },
            transactionUrl = account.transactionUrl
        )
}
