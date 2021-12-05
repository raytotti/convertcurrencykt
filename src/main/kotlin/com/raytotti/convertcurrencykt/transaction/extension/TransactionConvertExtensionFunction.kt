package com.raytotti.convertcurrencykt.transaction.extension

import com.raytotti.convertcurrencykt.transaction.application.CreateTransactionRequest
import com.raytotti.convertcurrencykt.transaction.application.TransactionResponse
import com.raytotti.convertcurrencykt.transaction.domain.Transaction
import java.math.BigDecimal
import java.time.ZonedDateTime

fun CreateTransactionRequest.toTransactionModel(rate: BigDecimal): Transaction {
    return Transaction(
        userId = this.userId,
        originCurrency = this.originCurrency,
        originValue = this.originValue,
        destinationCurrency = this.destinationCurrency,
        conversionRate = rate,
        createdAt = ZonedDateTime.now()
    )
}

fun Transaction.toTransactionResponse(): TransactionResponse {
    return TransactionResponse(
        id = this.id!!,
        userId = this.userId,
        originCurrency = this.originCurrency,
        originValue = this.originValue,
        destinationCurrency = this.destinationCurrency,
        destinationValue = this.originValue.multiply(this.conversionRate),
        conversionRate = this.conversionRate,
        createdAt = this.createdAt
    )
}