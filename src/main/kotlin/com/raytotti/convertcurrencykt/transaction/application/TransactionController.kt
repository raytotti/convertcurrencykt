package com.raytotti.convertcurrencykt.transaction.application

import com.raytotti.convertcurrencykt.commons.application.ResponseCollection
import com.raytotti.convertcurrencykt.commons.application.toResponseCollection
import com.raytotti.convertcurrencykt.conversion.domain.Conversion
import com.raytotti.convertcurrencykt.conversion.domain.IConversionService
import com.raytotti.convertcurrencykt.transaction.domain.TransactionRepository
import com.raytotti.convertcurrencykt.transaction.exception.TransactionNotFound
import com.raytotti.convertcurrencykt.transaction.extension.toTransactionModel
import com.raytotti.convertcurrencykt.transaction.extension.toTransactionResponse
import com.raytotti.convertcurrencykt.user.domain.UserRepository
import com.raytotti.convertcurrencykt.user.exception.UserNotFound
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import javax.transaction.Transactional

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
    private val repository: TransactionRepository,
    private val userRepository: UserRepository,
    private val conversionService: IConversionService
) {

    @PostMapping
    @Transactional
    fun create(@RequestBody request: CreateTransactionRequest): ResponseEntity<TransactionResponse> {
        if (!userRepository.existsById(request.userId)) {
            throw UserNotFound()
        }

        val conversionRate = conversionService.getRate(Conversion(request.originCurrency, request.destinationCurrency))

        val response = repository.save(request.toTransactionModel(conversionRate)).toTransactionResponse()

        val uri = fromCurrentRequest()
            .path("/")
            .path(response.id.toString())
            .build().toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<TransactionResponse> {
        return ResponseEntity.ok(
            repository.findById(id).orElseThrow { TransactionNotFound() }.toTransactionResponse()
        )
    }

    @GetMapping("/users/{userId}")
    fun findByUserId(
        @PathVariable userId: Long,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["createdAt"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable
    ): ResponseEntity<ResponseCollection<TransactionResponse>> {
        return if (!userRepository.existsById(userId)) {
            throw UserNotFound()
        } else {
            ResponseEntity.ok(
                repository.findByUserId(userId, pageable).map { it.toTransactionResponse() }.toResponseCollection()
            )
        }
    }
}