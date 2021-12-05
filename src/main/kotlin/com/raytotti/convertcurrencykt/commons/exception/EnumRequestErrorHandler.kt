package com.raytotti.convertcurrencykt.commons.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EnumRequestErrorHandler(val messageSource: MessageSource) {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handle(exception: HttpMessageNotReadableException): ResponseEntity<FieldRequestError> {
        val genericMessage = "Unacceptable JSON ${exception.message}"
        var errorResponse = ResponseEntity(FieldRequestError("BAD_REQUEST", genericMessage), HttpStatus.BAD_REQUEST)

        if (exception.cause is InvalidFormatException) {
            val ifx: InvalidFormatException = exception.cause as InvalidFormatException
            if (ifx.targetType != null && ifx.targetType.isEnum) {
                val message = messageSource.getMessage(
                    "Transaction.currency.invalid",
                    listOf(ifx.value, ifx.targetType.enumConstants.joinToString(prefix = "[", postfix = "]")).toTypedArray(),
                    LocaleContextHolder.getLocale()
                )
                val field = ifx.path[ifx.path.size - 1].fieldName
                errorResponse = ResponseEntity(FieldRequestError(field, message), HttpStatus.BAD_REQUEST)
            }
        }

        return errorResponse
    }
}