package com.ajmir.ui.transaction.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.ViewModel
import com.ajmir.transaction.TransactionRepository
import com.ajmir.ui.transaction.mapper.TransactionMapper
import com.ajmir.ui.transaction.model.TransactionParams
import com.ajmir.ui.transaction.model.TransactionViewState

class TransactionViewModel(
    params: TransactionParams,
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
): ViewModel() {

    val viewState = derivedStateOf {
        transactionRepository.getDetail(params.id)
            ?.let { mapper.mapToViewState(it) }
            ?: TransactionViewState.Error
    }

}
