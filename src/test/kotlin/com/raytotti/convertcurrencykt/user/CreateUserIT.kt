package com.raytotti.convertcurrencykt.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.raytotti.convertcurrencykt.user.application.CreateUserRequest
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
@Sql(scripts = ["classpath:db/it/base/user/CreateUser.sql"])
class CreateUserIT {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun create() {
        val urlTemplate = "/api/v1/users"
        val cpf = "430.609.538-05"
        val name = "Ray Toti Felix de Araujo"
        val userRequest = CreateUserRequest(cpf, name)

        mock.perform(post(urlTemplate).contentType(APPLICATION_JSON).content(mapper.writeValueAsBytes(userRequest)))
            .andExpect(status().isCreated)
            .andExpect(header().exists(LOCATION))
            .andExpect(redirectedUrlPattern("**//**${urlTemplate}/*"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.cpf").value(cpf))
    }

    @Test
    fun createWithCpfExists() {
        val urlTemplate = "/api/v1/users"
        val cpf = "911.135.730-40"
        val name = "Ray Toti Felix de Araujo"
        val userRequest = CreateUserRequest(cpf, name)

        mock.perform(post(urlTemplate).contentType(APPLICATION_JSON).content(mapper.writeValueAsBytes(userRequest)))
            .andExpect(status().isConflict)
    }
}