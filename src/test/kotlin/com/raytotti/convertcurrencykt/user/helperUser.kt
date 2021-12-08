package com.raytotti.convertcurrencykt.user

import com.raytotti.convertcurrencykt.user.application.CreateUserRequest


fun getUrlTemplate(complement: String = "") = "/api/v1/users${complement}"

fun buildUserRequest(
    cpf: String = "430.609.538-05",
    name: String = "Ray Toti Felix de Araujo"
) = CreateUserRequest(cpf, name)