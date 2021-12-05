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

        val responses: MutableList<FieldRequestError> = mutableListOf()

        exception.bindingResult.fieldErrors.forEach {
            val message = messageSource.getMessage(it, LocaleContextHolder.getLocale())
            responses.add(FieldRequestError(it.field, message))
        }

        return ResponseEntity(responses, HttpStatus.BAD_REQUEST)
    }
}