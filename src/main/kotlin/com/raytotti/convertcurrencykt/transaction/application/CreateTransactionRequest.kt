package com.raytotti.convertcurrencykt.transaction.application

import com.raytotti.convertcurrencykt.transaction.domain.Currency
import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class CreateTransactionRequest(
    @field:NotNull(message = "{Transaction.userId.NotNull}")
    val userId: Long,

    @field:NotNull(message = "{Transaction.originCurrency.NotNull}")
    val originCurrency: Currency,

    @field:NotNull(message = "{Transaction.originValue.NotNull}")
    val originValue: BigDecimal,

    @field:NotNull(message = "{Transaction.destinationCurrency.NotNull}")
    val destinationCurrency: Currency
)
