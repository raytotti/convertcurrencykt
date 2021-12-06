package com.raytotti.convertcurrencykt.conversion.infrastructure

import com.raytotti.convertcurrencykt.conversion.domain.Conversion
import com.raytotti.convertcurrencykt.conversion.domain.IConversionService
import com.raytotti.convertcurrencykt.conversion.exception.TaxRateNotFound
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ConversionService(
    @Value("\${conversion.access-key}")
    private var ACCESS_KEY: String,

    @Value("\${conversion.url}")
    private val URL: String,

    @Value("\${conversion.uri}")
    private val URI: String
) : IConversionService {
    override fun getRate(conversion: Conversion): BigDecimal {

        val rates = WebClient
            .create(URL)
            .get()
            .uri(URI, ACCESS_KEY, conversion.to, conversion.from)
            .retrieve()
            .bodyToMono<ExchangeRatesResponse>()
            .blockOptional()
            .orElseThrow { throw TaxRateNotFound() }

        val taxRateTo = rates.rates.get(key = conversion.to) ?: throw TaxRateNotFound()
        val taxRateFrom = rates.rates.get(key = conversion.from) ?: throw TaxRateNotFound()

        return taxRateFrom.divide(taxRateTo, 6, RoundingMode.HALF_EVEN) ?: throw TaxRateNotFound()
    }
}