package com.raytotti.convertcurrencykt.user.application

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateUserRequest (

    @field:NotBlank(message = "{User.cpf.NotBlank}")
    @field:Size(max = 14, min = 14, message = "{User.cpf.Size}")
    @field:CPF(message = "{User.cpf.Pattern}")
    var cpf: String,

    @field:NotBlank(message = "{User.name.NotBlank}")
    @field:Size(min = 3, max = 256, message = "{User.name.Size}")
    var name: String
)