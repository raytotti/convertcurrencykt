package com.raytotti.convertcurrencykt.user.application

import com.raytotti.convertcurrencykt.commons.application.ResponseCollection
import com.raytotti.convertcurrencykt.user.domain.UserRepository
import com.raytotti.convertcurrencykt.user.exception.UserExistsException
import com.raytotti.convertcurrencykt.user.exception.UserNotFound
import com.raytotti.convertcurrencykt.user.extension.toUserModel
import com.raytotti.convertcurrencykt.user.extension.toUserResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val repository: UserRepository
) {

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<UserResponse> {
        if (repository.existsByCpf(request.cpf))
            throw UserExistsException()

        val response = repository.save(request.toUserModel()).toUserResponse();

        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/")
            .path(response.id.toString())
            .build().toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val response = repository.findById(id).orElseThrow { UserNotFound() }.toUserResponse()
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun findAll(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["name"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): ResponseEntity<ResponseCollection<UserResponse>> {
        val users: Page<UserResponse> = repository.findAll(pageable).map { it.toUserResponse() }
        return ResponseEntity.ok(ResponseCollection(users))
    }
}