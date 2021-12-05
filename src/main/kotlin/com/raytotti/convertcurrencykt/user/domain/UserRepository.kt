package com.raytotti.convertcurrencykt.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByCpf(cpf: String): Boolean
}