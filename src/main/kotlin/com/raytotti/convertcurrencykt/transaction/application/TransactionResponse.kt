package com.raytotti.convertcurrencykt.transaction.application

import com.raytotti.convertcurrencykt.transaction.domain.Currency
import java.math.BigDecimal
import java.time.ZonedDateTime

data class TransactionResponse(
    val id: Long,
    val userId: Long,
    val originCurrency: Currency,
    val originValue: BigDecimal,
    val destinationCurrency: Currency,
    val destinationValue: BigDecimal,
    val conversionRate: BigDecimal,
    val createdAt: ZonedDateTime
)
