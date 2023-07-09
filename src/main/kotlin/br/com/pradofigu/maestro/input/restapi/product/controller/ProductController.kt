package br.com.pradofigu.maestro.input.restapi.product.controller

import br.com.pradofigu.maestro.domain.product.ports.input.ProductInputPort
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/products"], produces = [APPLICATION_JSON_VALUE])
class ProductController(private val productInputPort: ProductInputPort) {

    @PostMapping
    suspend fun register(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val product = productInputPort.register(request.toModel())
        return ResponseEntity(ProductResponse.from(product), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<Any> {
        return productInputPort.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(ProductResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> {
        val product = productInputPort.update(UUID.fromString(id), request.toModel())
        return ResponseEntity.ok(ProductResponse.from(product))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        productInputPort.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/category/{categoryId}")
    suspend fun findByCategory(@PathVariable categoryId: UUID): ResponseEntity<List<ProductResponse>> {
        val products = productInputPort.findByCategory(categoryId).map {
            ProductResponse.from(it)
        }

        return ResponseEntity.ok(products)
    }
}