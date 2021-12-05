package com.raytotti.convertcurrencykt.conversion.domain

import java.math.BigDecimal

interface IConversionService {
    fun getRate(conversion: Conversion): BigDecimal
}