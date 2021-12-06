package com.raytotti.convertcurrencykt.commons.exception

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationRequestErrorHandler(val messageSource: MessageSource) {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException): ResponseEntity<List<FieldRequestError>> {
        return ResponseEntity(
            exception.bindingResult.fieldErrors.map {
                val message = messageSource.getMessage(it, LocaleContextHolder.getLocale())
                FieldRequestError(it.field, message)
            },
            HttpStatus.BAD_REQUEST
        )
    }
}