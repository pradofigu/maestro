package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.service.ProductService
import br.com.pradofigu.maestro.web.dto.ProductRequest
import br.com.pradofigu.maestro.web.dto.ProductResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/products"], produces = [APPLICATION_JSON_VALUE])
class ProductController(private val productService: ProductService) {

    @PostMapping
    suspend fun register(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val product = productService.register(request.toModel())
        return ResponseEntity(ProductResponse.from(product), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<ProductResponse>> {
        val product = productService.findAll().map { ProductResponse.from(it) }
        return ResponseEntity.ok(product)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<Any> {
        return productService.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(ProductResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> {
        val product = productService.update(UUID.fromString(id), request.toModel())
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        productService.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/category/{categoryId}")
    suspend fun findByCategory(@PathVariable categoryId: UUID): ResponseEntity<List<ProductResponse>> {
        val products = productService.findByCategory(categoryId).map {
            ProductResponse.from(it)
        }

        return ResponseEntity.ok(products)
    }
}