package com.raytotti.convertcurrencykt.user.extension

import com.raytotti.convertcurrencykt.user.application.CreateUserRequest
import com.raytotti.convertcurrencykt.user.application.UserResponse
import com.raytotti.convertcurrencykt.user.domain.User

fun CreateUserRequest.toUserModel(): User {
    return User(cpf = this.cpf, name = this.name)
}

fun User.toUserResponse(): UserResponse {
    return UserResponse(this.id!!, this.cpf, this.name)
}