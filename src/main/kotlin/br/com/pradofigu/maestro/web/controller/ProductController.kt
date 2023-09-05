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

    @GetMapping("/{productId}")
    suspend fun findById(@PathVariable productId: UUID): ResponseEntity<Any> {
        return productService.findById(productId)?.let {
            ResponseEntity.ok(ProductResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{productId}")
    suspend fun update(
        @PathVariable productId: UUID,
        @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> {
        val product = productService.update(productId, request.toModel())
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @DeleteMapping("/{productId}")
    suspend fun delete(@PathVariable productId: UUID): ResponseEntity<Any> {
        productService.delete(productId)
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