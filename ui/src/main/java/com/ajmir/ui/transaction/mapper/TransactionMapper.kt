package com.ajmir.ui.transaction.mapper

import com.ajmir.transaction.model.TransactionDetail
import com.ajmir.ui.transaction.model.TransactionViewState

class TransactionMapper{

    fun mapToViewState(detail: TransactionDetail): TransactionViewState.Data {
        return TransactionViewState.Data(
            id = detail.id,
            reference = detail.reference,
            amount = "${detail.amount} ${detail.currency}",
            information = detail.information,
            address = detail.address
        )
    }
}
