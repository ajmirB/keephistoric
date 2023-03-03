package com.ajmir.account

import com.ajmir.account.model.AccountEntity

interface AccountRepository {
    suspend fun getAccounts(): Result<List<AccountEntity>>
}
