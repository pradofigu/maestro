package br.com.pradofigu.maestro.input.restapi.category.controller

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.ports.input.CategoryInputPort
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryResponse
import br.com.pradofigu.maestro.input.restapi.order.dto.OrderResponse
import org.springframework.beans.factory.annotation.Autowired
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
@RequestMapping(value = ["/categories"], produces = [APPLICATION_JSON_VALUE])
class CategoryController(@Autowired private val categoryInputPort: CategoryInputPort) {

    @PostMapping
    suspend fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = categoryInputPort.create(Category(name = request.name))
        return ResponseEntity(CategoryResponse.from(category), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<CategoryResponse>> {
        val categories = categoryInputPort.findAll().map { CategoryResponse.from(it) }
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<CategoryResponse> {
        return categoryInputPort.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(CategoryResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: CategoryRequest
    ): ResponseEntity<CategoryResponse> {
        val category = categoryInputPort.update(
            Category(id = UUID.fromString(id), name = request.name)
        )

        return ResponseEntity.ok(CategoryResponse.from(category))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        categoryInputPort.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}