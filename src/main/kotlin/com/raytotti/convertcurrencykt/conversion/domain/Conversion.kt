package com.raytotti.convertcurrencykt.conversion.domain

import com.raytotti.convertcurrencykt.transaction.domain.Currency

data class Conversion(
    val to: Currency,
    val from: Currency
)