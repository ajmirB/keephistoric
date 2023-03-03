package com.ajmir.transaction_impl

import com.ajmir.common.manager.DateManager
import com.ajmir.data.retrofit.com.ajmir.retrofit.model.ApiResponse
import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.TransactionStatus
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction_impl.remote.TransactionApi
import com.ajmir.transaction_impl.remote.model.TransactionAmountResponse
import com.ajmir.transaction_impl.remote.model.TransactionResponse
import com.ajmir.transaction_impl.remote.model.TransactionsResponse
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class TransactionsRepositoryImplTest {

    @MockK
    private lateinit var api: TransactionApi

    @MockK
    private lateinit var dateManager: DateManager

    private lateinit var repository: TransactionsRepositoryImpl

    private val transactionResponse = TransactionResponse(
        transactionId = "123",
        reference = "ref123",
        creditDebitIndicator = "credit",
        status = "Canceled",
        dateTime = "any",
        information = "information",
        amount = TransactionAmountResponse("300", "EUR"),
        adress = null
    )

    @BeforeEach
    internal fun setUp() {
        repository = TransactionsRepositoryImpl(api, dateManager)
    }

    @Test
    fun `getAll, with valid account from api, should return a valid entity`() {
        // Given
        val transaction1 = TransactionResponse(
            transactionId = "123",
            reference = "ref123",
            creditDebitIndicator = "credit",
            status = "Canceled",
            dateTime = "any",
            information = "information",
            amount = TransactionAmountResponse("300", "EUR"),
            adress = null
        )
        val transaction2 = TransactionResponse(
            transactionId = "sdfsf",
            reference = "refdsjp",
            creditDebitIndicator = "debit",
            status = "Booked",
            dateTime = "any",
            information = "information",
            amount = TransactionAmountResponse("300", "EUR"),
            adress = null
        )
        val response = TransactionsResponse(listOf(transaction1, transaction2))
        coEvery { api.getTransactions(any()) } returns ApiResponse(response)
        every { dateManager.parse(any()) } returns Date()

        // When
        val result = runBlocking { repository.getAll("") }

        // Then
        result.isSuccess shouldBe true
        result.getOrNull()?.transactions?.size shouldBe response.transactions.size
    }

    @Test
    fun `getAll, with invalid response from api, should not crash`() {
        // Given
        val error = Error("Invalid request")
        coEvery { api.getTransactions(any()) } throws error
        every { dateManager.parse(any()) } returns Date()

        // When
        val result = runBlocking { repository.getAll("") }

        // Then
        result.isFailure shouldBe true
        result.exceptionOrNull() shouldBe error
    }

    // region mapToEntity

    @Test
    fun `mapToEntity for transactions, should not return list with null values`() {
        // Given
        every { dateManager.parse(any()) } returns null
        // When
        val result = repository.mapToEntity(listOf(transactionResponse))
        // Then
        result.transactions.isEmpty() shouldBe true
    }

    @Test
    fun `mapToEntity, verify data integrity`() {
        // Given
        val date = Date()
        every { dateManager.parse(any()) } returns date
        // When
        val result = repository.mapToEntity(transactionResponse)!!
        // Then
        result.id shouldBe transactionResponse.transactionId
        result.date shouldBe date
        result.amount shouldBe transactionResponse.amount.amount
        result.currency shouldBe transactionResponse.amount.currency
        result.status shouldBe TransactionStatus.CANCELED
        result.type shouldBe TransactionType.CREDIT
    }

    @Test
    fun `mapToEntity, with invalid date don't crash`() {
        // Given
        every { dateManager.parse(any()) } returns null
        // When
        val result = repository.mapToEntity(transactionResponse)
        // Then
        result shouldBe null
    }

    @Test
    fun `mapToEntity, verify type integrity`() {
        every { dateManager.parse(any()) } returns Date()
        // Credit
        var transaction = transactionResponse.copy(creditDebitIndicator = "cRedIt")
        var result = repository.mapToEntity(transaction)!!
        result.type shouldBe TransactionType.CREDIT
        // Debit
        transaction = transactionResponse.copy(creditDebitIndicator = "DebIT")
        result = repository.mapToEntity(transaction)!!
        result.type shouldBe TransactionType.DEBIT
        // Unknown
        transaction = transactionResponse.copy(creditDebitIndicator = "dsds")
        result = repository.mapToEntity(transaction)!!
        result.type shouldBe TransactionType.UNKNOWN
    }

    @Test
    fun `mapToEntity, verify status integrity`() {
        every { dateManager.parse(any()) } returns Date()
        // Canceled
        var transaction = transactionResponse.copy(status = "canceLed")
        var result = repository.mapToEntity(transaction)!!
        result.status shouldBe TransactionStatus.CANCELED
        // Booked
        transaction = transactionResponse.copy(status = "booked")
        result = repository.mapToEntity(transaction)!!
        result.status shouldBe TransactionStatus.BOOKED
        // Unknown
        transaction = transactionResponse.copy(status = "dsfdsgds")
        result = repository.mapToEntity(transaction)!!
        result.status shouldBe TransactionStatus.UNKNOWN
    }

    @Test
    fun `mapToDetailEntity, verify data integrity`() {
        // Given
        val date = Date()
        every { dateManager.parse(any()) } returns date
        // When
        val result = repository.mapToDetailEntity(transactionResponse)!!
        // Then
        result.id shouldBe transactionResponse.transactionId
        result.date shouldBe date
        result.amount shouldBe transactionResponse.amount.amount
        result.currency shouldBe transactionResponse.amount.currency
        result.status shouldBe TransactionStatus.CANCELED
        result.type shouldBe TransactionType.CREDIT
        result.information shouldBe transactionResponse.information
        result.address shouldBe transactionResponse.adress
        result.reference shouldBe transactionResponse.reference
    }

    // endregion
}
