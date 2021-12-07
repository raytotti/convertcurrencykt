package com.raytotti.convertcurrencykt.user.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator

class CreateUserRequestTest {

    private val cpfDefault = "430.609.538-05"
    private val nameDefault = "Ray Toti Felix de Araujo"

    private lateinit var validator: Validator

    @BeforeEach
    fun beforeEach() {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun build() {
        val createUserRequest = CreateUserRequest(cpf = cpfDefault, name = nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(cpfDefault, createUserRequest.cpf)
        assertEquals(nameDefault, createUserRequest.name)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun nameEmpty() {
        val createUserRequest = CreateUserRequest(cpfDefault, "")
        val violations = validator.validate(createUserRequest)

        assertEquals(cpfDefault, createUserRequest.cpf)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun nameMin() {
        val createUserRequest = CreateUserRequest(cpfDefault, "ab")
        val violations = validator.validate(createUserRequest)

        assertEquals(cpfDefault, createUserRequest.cpf)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun nameMax() {
        val createUserRequest = CreateUserRequest(cpfDefault, "A".repeat(257))
        val violations = validator.validate(createUserRequest)

        assertEquals(cpfDefault, createUserRequest.cpf)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun cpfEmpty() {
        val createUserRequest = CreateUserRequest("", nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(nameDefault, createUserRequest.name)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun cpfSizeMin() {
        val createUserRequest = CreateUserRequest("1".repeat(13), nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(nameDefault, createUserRequest.name)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun cpfSizeMax() {
        val createUserRequest = CreateUserRequest("1".repeat(15), nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(nameDefault, createUserRequest.name)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun cpfFormat() {
        val createUserRequest = CreateUserRequest("1".repeat(14), nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(nameDefault, createUserRequest.name)
        assertFalse(violations.isEmpty())
    }

    @Test
    fun cpfInvalid() {
        val createUserRequest = CreateUserRequest("123.456.789-00", nameDefault)
        val violations = validator.validate(createUserRequest)

        assertEquals(nameDefault, createUserRequest.name)
        assertFalse(violations.isEmpty())
    }

}