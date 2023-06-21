package br.com.pradofigu.maestro.resources.products

import br.com.pradofigu.maestro.domain.products.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/products"], produces = [APPLICATION_JSON_VALUE])
class ProductResource(@Autowired private val service: ProductService) {

    @PostMapping
    suspend fun register(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val product = service.register(request.toCreateProduct())
        return ResponseEntity(ProductResponse.from(product), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ProductResponse? {
        val maybeProduct = service.findBy(UUID.fromString(id))
        return maybeProduct?.let { product -> ProductResponse.from(product) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: ProductRequest): ProductResponse {
        val product = service.update(UUID.fromString(id), request.toUpdateProduct())
        return ProductResponse.from(product)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/category/{category}")
    suspend fun findByCategory(@PathVariable category: String): List<ProductResponse> {
        val products = service.findBy(category)
        return products.map { product -> ProductResponse.from(product) }
    }
}