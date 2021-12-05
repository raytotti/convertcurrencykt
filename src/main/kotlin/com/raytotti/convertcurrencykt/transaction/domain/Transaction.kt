package com.raytotti.convertcurrencykt.transaction.domain

import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.*

@Entity(name = "transaction")
data class Transaction (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: Long,

    @Enumerated(EnumType.STRING)
    var originCurrency: Currency,

    var originValue: BigDecimal,

    @Enumerated(EnumType.STRING)
    var destinationCurrency: Currency,

    var conversionRate: BigDecimal,

    var createdAt: ZonedDateTime

)