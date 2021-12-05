package com.raytotti.convertcurrencykt.commons.application

import org.springframework.data.domain.Page

class ResponseCollection<T> (
    val hasNext: Boolean,
    val items: Collection<T>
    ){
    constructor(page: Page<T>) : this(!page.isLast, page.content)
}