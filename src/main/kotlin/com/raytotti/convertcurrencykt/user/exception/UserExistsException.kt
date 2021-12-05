package com.raytotti.convertcurrencykt.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User.exists")
class UserExistsException : RuntimeException()