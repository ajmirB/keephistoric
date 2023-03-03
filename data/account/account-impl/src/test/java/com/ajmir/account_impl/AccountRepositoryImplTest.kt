package com.ajmir.account_impl

import com.ajmir.account.model.AccountSubtype
import com.ajmir.account.model.AccountType
import com.ajmir.account_impl.remote.AccountApi
import com.ajmir.account_impl.remote.model.AccountResponse
import com.ajmir.account_impl.remote.model.AccountsResponse
import com.ajmir.data.retrofit.com.ajmir.retrofit.model.ApiResponse
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class AccountRepositoryImplTest {

    @MockK
    private lateinit var api: AccountApi

    private lateinit var repository: AccountRepositoryImpl

    val accountResponse = AccountResponse(
        id = "123",
        nickname = "John Snow",
        type = "Personal",
        subType = "CurrentAccount",
        status = "Enabled",
        currency = "EUR",
        transactionUrl = "sdfdspofjsdp"
    )

    @BeforeEach
    internal fun setUp() {
        repository = AccountRepositoryImpl(api)
    }

    @Test
    fun `getAccount, with valid account from api, should return a valid entity`() {
        // Given
        val accountResponse = AccountResponse(
            id = "123",
            nickname = "John Snow",
            type = "Saving",
            subType = "CurrentAccount",
            status = "Enabled",
            currency = "EUR",
            transactionUrl = "sdfdspofjsdp"
        )
        val accountResponse2 = AccountResponse(
            id = "U94",
            nickname = "Han Solo",
            type = "Personal",
            subType = "SavingAccount",
            status = "Disabled",
            currency = "DOLLARS",
            transactionUrl = "sdfdsiofnsdo"
        )
        val accountsResponse = AccountsResponse(accounts = listOf(accountResponse, accountResponse2))
        coEvery { api.getAccounts(any()) } returns ApiResponse(accountsResponse)

        // When
        val result = runBlocking { repository.getAccounts() }

        // Then
        result.isSuccess shouldBe true
        result.getOrNull()?.size shouldBe accountsResponse.accounts.size
    }

    @Test
    fun `getAccount, with invalid response from api, should not crash`() {
        // Given
        val error = Error("invalid request")
        coEvery { api.getAccounts(any()) } throws error

        // When
        val result = runBlocking { repository.getAccounts() }

        // Then
        result.isFailure shouldBe true
        result.exceptionOrNull() shouldBe error

    }

    @Test
    fun `mapToEntity, verify data integrity`() {
        // Given
        val accountResponse = AccountResponse(
            id = "123",
            nickname = "John Snow",
            type = "Personal",
            subType = "CurrentAccount",
            status = "Enabled",
            currency = "EUR",
            transactionUrl = "sdfdspofjsdp"
        )

        // When
        val result = repository.mapToEntity(accountResponse)

        // Then
        result.id shouldBe accountResponse.id
        result.name shouldBe accountResponse.nickname
        result.type shouldBe AccountType.PERSONAL
        result.subtype shouldBe AccountSubtype.CURRENT
        result.transactionUrl shouldBe accountResponse.transactionUrl
        result.isEnabled shouldBe true
    }

    @Test
    fun `mapToEntity, verify type integrity`() {
        // Personal
        var accountResponse = accountResponse.copy(type = "personal")
        var result = repository.mapToEntity(accountResponse)
        result.type shouldBe AccountType.PERSONAL
        // Unknown
        accountResponse = accountResponse.copy(type = "dskdshkfs")
        result = repository.mapToEntity(accountResponse)
        result.type shouldBe AccountType.UNKNOWN
    }

    @Test
    fun `mapToEntity, verify subtype integrity`() {
        // Current
        var accountResponse = accountResponse.copy(subType = "CurrEntacCount")
        var result = repository.mapToEntity(accountResponse)
        result.subtype shouldBe AccountSubtype.CURRENT
        // Saving
        accountResponse = accountResponse.copy(subType = "sAvingaccOunt")
        result = repository.mapToEntity(accountResponse)
        result.subtype shouldBe AccountSubtype.SAVING
        // Unknown
        accountResponse = accountResponse.copy(subType = "dskdshkfs")
        result = repository.mapToEntity(accountResponse)
        result.subtype shouldBe AccountSubtype.UNKNOWN
    }

    @Test
    fun `mapToEntity, verify isEnabled integrity`() {
        // is Enabled
        var accountResponse = accountResponse.copy(status = "eNabled")
        var result = repository.mapToEntity(accountResponse)
        result.isEnabled shouldBe true
        // Unknown should return false
        accountResponse = accountResponse.copy(status = "disabled")
        result = repository.mapToEntity(accountResponse)
        result.isEnabled shouldBe false
        // Empty should return false
        accountResponse = accountResponse.copy(status = "")
        result = repository.mapToEntity(accountResponse)
        result.isEnabled shouldBe false
    }
}
