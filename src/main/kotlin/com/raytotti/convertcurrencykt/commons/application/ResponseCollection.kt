package com.raytotti.convertcurrencykt.commons.application

import org.springframework.data.domain.Page

class ResponseCollection<T> (
    val hasNext: Boolean,
    val items: Collection<T>
)

fun <T> Page<T>.toResponseCollection() : ResponseCollection<T>{
    return ResponseCollection(!this.isLast, this.content)
}