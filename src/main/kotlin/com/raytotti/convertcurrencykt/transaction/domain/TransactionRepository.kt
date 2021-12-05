package com.raytotti.convertcurrencykt.transaction.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<Transaction>
}