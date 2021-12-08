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
@Sql(scripts = ["classpath:db/it/base/transaction/FindByUserTransaction.sql"])
class FindByUserTransactionIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun findPageDefault() {
        val rate = BigDecimal.valueOf(0.049119)
        val originValue = BigDecimal.valueOf(40)
        val destinationValue = originValue.multiply(rate)

        mock.perform(get(getUrlTemplate("/users/1")))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(3))
            .andExpect(jsonPath("$.hasNext").value(false))
            .andExpect(jsonPath("$.items[0].id").value("3"))
            .andExpect(jsonPath("$.items[0].userId").value("1"))
            .andExpect(jsonPath("$.items[0].originCurrency").value(Currency.BRL.toString()))
            .andExpect(jsonPath("$.items[0].originValue").value(originValue))
            .andExpect(jsonPath("$.items[0].destinationCurrency").value(Currency.JPY.toString()))
            .andExpect(jsonPath("$.items[0].destinationValue").value(destinationValue.toFloat()))
            .andExpect(jsonPath("$.items[0].conversionRate").value(rate))
            .andExpect(jsonPath("$.items[0].createdAt").exists())
    }

    @Test
    fun findPage() {
        var rate = BigDecimal.valueOf(0.049119)
        var originValue = BigDecimal.valueOf(40)
        var destinationValue = originValue.multiply(rate)

        mock.perform(
            get(getUrlTemplate("/users/1"))
                .param("page", "0")
                .param("size", "2")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(2))
            .andExpect(jsonPath("$.hasNext").value(true))
            .andExpect(jsonPath("$.items[0].id").value("3"))
            .andExpect(jsonPath("$.items[0].userId").value("1"))
            .andExpect(jsonPath("$.items[0].originCurrency").value(Currency.BRL.toString()))
            .andExpect(jsonPath("$.items[0].originValue").value(originValue))
            .andExpect(jsonPath("$.items[0].destinationCurrency").value(Currency.JPY.toString()))
            .andExpect(jsonPath("$.items[0].destinationValue").value(destinationValue.toFloat()))
            .andExpect(jsonPath("$.items[0].conversionRate").value(rate))
            .andExpect(jsonPath("$.items[0].createdAt").exists())

        rate = BigDecimal.valueOf(6.318592)
        originValue = BigDecimal.TEN
        destinationValue = originValue.multiply(rate)

        mock.perform(
            get(getUrlTemplate("/users/1"))
                .param("page", "1")
                .param("size", "2")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(1))
            .andExpect(jsonPath("$.hasNext").value(false))
            .andExpect(jsonPath("$.items[0].id").value("1"))
            .andExpect(jsonPath("$.items[0].userId").value("1"))
            .andExpect(jsonPath("$.items[0].originCurrency").value(Currency.BRL.toString()))
            .andExpect(jsonPath("$.items[0].originValue").value(originValue))
            .andExpect(jsonPath("$.items[0].destinationCurrency").value(Currency.EUR.toString()))
            .andExpect(jsonPath("$.items[0].destinationValue").value(destinationValue.toFloat()))
            .andExpect(jsonPath("$.items[0].conversionRate").value(rate))
            .andExpect(jsonPath("$.items[0].createdAt").exists())

    }

    @Test
    fun findUserNotFound() {
        mock.perform(get(getUrlTemplate("/users/10")))
            .andExpect(status().isNotFound)
    }

}

