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
@Sql(scripts = ["classpath:db/it/base/user/FindByIdUser.sql"])
class FindByIdUserIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun find() {
        mock.perform(get(getUrlTemplate("/1")))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("Ray"))
            .andExpect(jsonPath("$.cpf").value("430.609.538-05"))
    }


    @Test
    fun findNotFound() {
        mock.perform(get(getUrlTemplate("/10")))
            .andExpect(status().isNotFound)
    }
}