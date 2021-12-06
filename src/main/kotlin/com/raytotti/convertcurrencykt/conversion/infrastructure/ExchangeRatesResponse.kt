package com.raytotti.convertcurrencykt.conversion.infrastructure

import com.raytotti.convertcurrencykt.transaction.domain.Currency
import java.math.BigDecimal

data class ExchangeRatesResponse(
    val rates: Map<Currency, BigDecimal>
)
