package com.raytotti.convertcurrencykt.transaction

import com.raytotti.convertcurrencykt.transaction.domain.Currency
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import javax.transaction.Transactional

@Transactional
@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
@Sql(scripts = ["classpath:db/it/base/transaction/FindByIdTransaction.sql"])
class FindByIdTransactionIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun find() {
        val id = 1

        val rate = BigDecimal.valueOf(6.318592)
        val originValue = BigDecimal.TEN
        val destinationValue = originValue.multiply(rate)

        mock.perform(get(getUrlTemplate("/${id}")))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.userId").value("1"))
            .andExpect(jsonPath("$.originCurrency").value(Currency.BRL.toString()))
            .andExpect(jsonPath("$.originValue").value(originValue))
            .andExpect(jsonPath("$.destinationCurrency").value(Currency.EUR.toString()))
            .andExpect(jsonPath("$.destinationValue").value(destinationValue.toFloat()))
            .andExpect(jsonPath("$.conversionRate").value(rate))
            .andExpect(jsonPath("$.createdAt").exists())
    }

    @Test
    fun findIdNotFound() {
        mock.perform(get(getUrlTemplate("/10")))
            .andExpect(status().isNotFound)
    }

}

