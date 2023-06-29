package br.com.pradofigu.maestro.input.restapi.category.controller

import br.com.pradofigu.maestro.domain.category.ports.input.CategoryInputPort
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/categories"], produces = [APPLICATION_JSON_VALUE])
class CategoryController(@Autowired private val categoryInputPort: CategoryInputPort) {

    @PostMapping
    suspend fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = categoryInputPort.create(request.toCreateCategory())
        return ResponseEntity(CategoryResponse.from(category), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): CategoryResponse? {
        val maybeCategory = categoryInputPort.findBy(UUID.fromString(id))
        return maybeCategory?.let { category -> CategoryResponse.from(category) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: CategoryRequest): CategoryResponse {
        val category = categoryInputPort.update(UUID.fromString(id), request.toUpdateCategory())
        return CategoryResponse.from(category)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        categoryInputPort.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}