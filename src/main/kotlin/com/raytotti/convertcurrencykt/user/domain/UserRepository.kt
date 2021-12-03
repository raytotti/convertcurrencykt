package com.raytotti.convertcurrencykt.user.domain

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun existsByCpf(cpf: String): Boolean
}