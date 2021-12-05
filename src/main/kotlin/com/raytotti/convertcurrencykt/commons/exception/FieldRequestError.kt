package com.raytotti.convertcurrencykt.commons.exception

data class FieldRequestError(
    val field: String,
    val error : String
)