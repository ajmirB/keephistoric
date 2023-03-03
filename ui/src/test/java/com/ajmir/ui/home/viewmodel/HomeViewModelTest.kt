package com.ajmir.ui.home.viewmodel

import com.ajmir.account.AccountRepository
import com.ajmir.account.model.AccountEntity
import com.ajmir.account.model.AccountSubtype
import com.ajmir.account.model.AccountType
import com.ajmir.common.manager.DateManager
import com.ajmir.transaction.TransactionRepository
import com.ajmir.transaction.model.Transaction
import com.ajmir.transaction.model.TransactionStatus
import com.ajmir.transaction.model.TransactionType
import com.ajmir.transaction.model.Transactions
import com.ajmir.ui.home.mapper.HomeMapper
import com.ajmir.ui.home.model.HomeViewState
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class HomeViewModelTest {

    @MockK
    lateinit var accountRepository: AccountRepository

    @MockK
    lateinit var transactionRepository: TransactionRepository

    private lateinit var mapper: HomeMapper

    private lateinit var viewModel: HomeViewModel

    val accounts = listOf(
        AccountEntity("a", "", true, AccountType.PERSONAL, AccountSubtype.CURRENT, ""),
        AccountEntity("b", "", true, AccountType.PERSONAL, AccountSubtype.SAVING, ""),
        AccountEntity("c", "", true, AccountType.PERSONAL, AccountSubtype.SAVING, "")
    )
    val transactions = Transactions(
        listOf(
            Transaction("1", "", "", Date(), TransactionType.CREDIT, TransactionStatus.BOOKED),
            Transaction("2", "", "", Date(), TransactionType.CREDIT, TransactionStatus.CANCELED),
            Transaction("3", "", "", Date(), TransactionType.DEBIT, TransactionStatus.BOOKED),
        )
    )

    @BeforeEach
    internal fun setUp() {
        coEvery { accountRepository.getAccounts() } returns Result.success(accounts)
        coEvery { transactionRepository.getAll(any()) } returns Result.success(transactions)
        mapper = HomeMapper(DateManager())
        viewModel = HomeViewModel(
            accountRepository = accountRepository,
            transactionRepository = transactionRepository,
            mapper = mapper
        )
    }

    @Test
    fun `At beginning, the default view state is loading`() {
        viewModel.viewState.value shouldBe HomeViewState.Loading
    }

    @Test
    fun `loadAccounts returns an error not skippable, the viewState is Error`() {
        coEvery { accountRepository.getAccounts() } returns Result.failure(Error())
        runBlocking { viewModel.loadAccounts(null, false) }
        viewModel.viewState.value shouldBe HomeViewState.Error
    }

    @Test
    fun `loadAccounts with a current valid state, and with skip error, should stay at the current state`() {
        // Load data to get a viewState filled with data
        runBlocking { viewModel.loadAccounts(null, false) }
        (viewModel.viewState.value is HomeViewState.Data) shouldBe true

        // This load data will failed but without interfering with the viewstate
        coEvery { accountRepository.getAccounts() } returns Result.failure(Error())
        runBlocking { viewModel.loadAccounts(null, true) }
        (viewModel.viewState.value is HomeViewState.Data) shouldBe true

        // And this one will failed and the viewstate will return an error state
        coEvery { accountRepository.getAccounts() } returns Result.failure(Error())
        runBlocking { viewModel.loadAccounts(null, false) }
        viewModel.viewState.value shouldBe HomeViewState.Error
    }

    @Test
    fun `onRefresh failed should not interfered with the viewstate`() {
        // Load data to get a viewState filled with data
        runBlocking { viewModel.loadAccounts(null, false) }
        (viewModel.viewState.value is HomeViewState.Data) shouldBe true

        // This load data will failed but without interfering with the viewstate
        coEvery { accountRepository.getAccounts() } returns Result.failure(Error())
        runBlocking { viewModel.onRefresh() }
        (viewModel.viewState.value is HomeViewState.Data) shouldBe true
    }
}
