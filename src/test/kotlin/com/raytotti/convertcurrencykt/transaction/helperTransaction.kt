package com.raytotti.convertcurrencykt.transaction

import com.raytotti.convertcurrencykt.transaction.application.CreateTransactionRequest
import com.raytotti.convertcurrencykt.transaction.domain.Currency
import java.math.BigDecimal

fun buildTransactionRequest(
    userId: Long = 1L,
    originCurrency: Currency = Currency.BRL,
    originValue: BigDecimal = BigDecimal.TEN,
    destinationCurrency: Currency = Currency.EUR
) = CreateTransactionRequest(
    userId,
    originCurrency,
    originValue,
    destinationCurrency
)

fun getUrlTemplate(complement: String = "") = "/api/v1/transactions${complement}"