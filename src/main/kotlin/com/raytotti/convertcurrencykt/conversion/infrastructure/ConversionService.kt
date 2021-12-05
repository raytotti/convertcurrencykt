package com.raytotti.convertcurrencykt.conversion.infrastructure

import com.raytotti.convertcurrencykt.conversion.domain.Conversion
import com.raytotti.convertcurrencykt.conversion.domain.IConversionService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ConversionService : IConversionService {
    override fun getRate(conversion: Conversion): BigDecimal {
        return BigDecimal.ONE
    }
}