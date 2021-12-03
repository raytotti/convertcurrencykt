package com.raytotti.convertcurrencykt.user.domain

import javax.persistence.*

@Entity(name = "user")
@Table(name = "user", uniqueConstraints = [UniqueConstraint(name = "user_uk", columnNames = ["cpf"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    var cpf: String,

    @Column
    var name: String
)