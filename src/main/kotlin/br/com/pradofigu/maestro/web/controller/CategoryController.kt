package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.service.CategoryService
import br.com.pradofigu.maestro.web.dto.CategoryRequest
import br.com.pradofigu.maestro.web.dto.CategoryResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/categories"], produces = [APPLICATION_JSON_VALUE])
class CategoryController(private val categoryService: CategoryService) {

    @PostMapping
    suspend fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = categoryService.create(request.toModel())
        return ResponseEntity(CategoryResponse.from(category), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<CategoryResponse>> {
        val categories = categoryService.findAll().map { CategoryResponse.from(it) }
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: UUID): ResponseEntity<CategoryResponse> {
        return categoryService.findBy(id)?.let {
            ResponseEntity.ok(CategoryResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: UUID,
        @RequestBody request: CategoryRequest
    ): ResponseEntity<CategoryResponse> {
        val category = categoryService.update(request.toModel())
        return ResponseEntity.ok(CategoryResponse.from(category))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: UUID): ResponseEntity<Any> {
        categoryService.delete(id)
        return ResponseEntity.noContent().build()
    }
}