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
@Sql(scripts = ["classpath:db/it/base/user/FindAllUser.sql"])
class FindAllUserIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun findPageDefault() {
        mock.perform(get(getUrlTemplate()))
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(3))
            .andExpect(jsonPath("$.hasNext").value(false))
            .andExpect(jsonPath("$.items[0].id").value(2))
            .andExpect(jsonPath("$.items[0].cpf").value("911.135.730-40"))
            .andExpect(jsonPath("$.items[0].name").value("Maria"))
    }

    @Test
    fun findPage() {
        mock.perform(
            get(getUrlTemplate())
                .param("page", "0")
                .param("size", "2")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(2))
            .andExpect(jsonPath("$.hasNext").value(true))
            .andExpect(jsonPath("$.items[0].id").value(2))
            .andExpect(jsonPath("$.items[0].cpf").value("911.135.730-40"))
            .andExpect(jsonPath("$.items[0].name").value("Maria"))

        mock.perform(
            get(getUrlTemplate())
                .param("page", "1")
                .param("size", "2")
        )
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.items.length()").value(1))
            .andExpect(jsonPath("$.hasNext").value(false))
            .andExpect(jsonPath("$.items[0].id").value(1))
            .andExpect(jsonPath("$.items[0].cpf").value("430.609.538-05"))
            .andExpect(jsonPath("$.items[0].name").value("Ray"))
    }
}