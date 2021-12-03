package com.raytotti.convertcurrencykt.user.application

import com.raytotti.convertcurrencykt.user.domain.UserRepository
import com.raytotti.convertcurrencykt.user.extension.toUserModel
import com.raytotti.convertcurrencykt.user.extension.toUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.transaction.Transactional

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    val repository: UserRepository
) {

    @PostMapping
    @Transactional
    fun create (@RequestBody request: CreateUserRequest) : ResponseEntity<UserResponse> {
        if(repository.existsByCpf(request.cpf)){
            throw Exception()
        }

        val newUser = repository.save(request.toUserModel())

        val response = newUser.toUserResponse();

        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/")
            .path(response.id.toString())
            .build().toUri()

        return ResponseEntity.created(uri).body(response)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : ResponseEntity<UserResponse> {
        val user = repository.findById(id)

        val response = user.orElseThrow().toUserResponse()

        return ResponseEntity.ok(response)
    }
}