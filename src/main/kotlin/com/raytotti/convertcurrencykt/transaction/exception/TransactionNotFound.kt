package com.raytotti.convertcurrencykt.transaction.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transaction.notFound")
class TransactionNotFound : RuntimeException()