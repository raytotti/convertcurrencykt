package com.raytotti.convertcurrencykt.transaction

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import javax.transaction.Transactional

@Transactional
@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
@Sql(scripts = ["classpath:db/it/base/transaction/CreateTransaction.sql"])
class CreateTransactionIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun create() {
        val createTransactionRequest = buildTransactionRequest()

        mock.perform(
            post(getUrlTemplate()).contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(createTransactionRequest))
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists(LOCATION))
            .andExpect(redirectedUrlPattern("**//**${getUrlTemplate()}/*"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.userId").value(createTransactionRequest.userId.toString()))
            .andExpect(jsonPath("$.originCurrency").value(createTransactionRequest.originCurrency.toString()))
            .andExpect(jsonPath("$.originValue").value(createTransactionRequest.originValue))
            .andExpect(jsonPath("$.destinationCurrency").value(createTransactionRequest.destinationCurrency.toString()))
            .andExpect(jsonPath("$.destinationValue").exists())
            .andExpect(jsonPath("$.conversionRate").exists())
            .andExpect(jsonPath("$.createdAt").exists())
    }

    @Test
    fun createNotFoundUser() {
        val createTransactionRequest = buildTransactionRequest(userId = 10L)

        mock.perform(
            post(getUrlTemplate()).contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(createTransactionRequest))
        )
            .andExpect(status().isNotFound)
    }

}

