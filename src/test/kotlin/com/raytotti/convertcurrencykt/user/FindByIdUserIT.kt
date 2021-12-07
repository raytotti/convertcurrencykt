package com.raytotti.convertcurrencykt.user

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
import javax.transaction.Transactional


@Transactional
@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
class FindByIdUserIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    @Sql(scripts = ["classpath:db/it/base/user/FindByIdUser.sql"])
    fun find() {
        val userId = "1"
        val cpf = "430.609.538-05"
        val name = "Ray"

        mock.perform(get("/api/v1/users/${userId}"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.cpf").value(cpf))
    }
}