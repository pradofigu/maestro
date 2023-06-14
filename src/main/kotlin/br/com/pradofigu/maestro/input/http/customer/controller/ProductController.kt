package br.com.pradofigu.maestro.input.http.customer.controller

import br.com.pradofigu.maestro.domain.customer.ports.input.ProductInputPort
import br.com.pradofigu.maestro.input.http.customer.dto.ProductDTO
import br.com.pradofigu.maestro.input.http.customer.dto.toDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController("/products")
class ProductController(private val productInputPort: ProductInputPort) {
    @GetMapping
    suspend fun findAll(): List<ProductDTO> {
        return productInputPort.findAll().map { it.toDTO() }
    }

    @GetMapping("/{productId}")
    suspend fun findById(@PathVariable productId: UUID): ResponseEntity<ProductDTO> {
        val result = productInputPort.findById(productId)

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/category/{category}")
    suspend fun findByCategory(@PathVariable category: String): List<ProductDTO> {
        return productInputPort.findByCategory(category).map { it.toDTO() }
    }

    @PostMapping
    suspend fun create(@RequestBody product: ProductDTO): ResponseEntity<ProductDTO> {
        val result = productInputPort.create(product.toModel())

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    suspend fun update(@RequestBody product: ProductDTO): ResponseEntity<ProductDTO> {
        val result = productInputPort.update(product.toModel())

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @DeleteMapping("/{productId}")
    suspend fun deleteById(@PathVariable productId: UUID): ResponseEntity<HttpStatus> {
        productInputPort.deleteById(productId)

        return ResponseEntity.ok().build()
    }
}