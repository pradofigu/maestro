package br.com.pradofigu.maestro.resources.categories

import br.com.pradofigu.maestro.domain.categories.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/categories"], produces = [APPLICATION_JSON_VALUE])
class CategoryResource(@Autowired private val service: CategoryService) {

    @PostMapping
    suspend fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = service.create(request.toCreateCategory())
        return ResponseEntity(CategoryResponse.from(category), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): CategoryResponse? {
        val maybeCategory = service.findBy(UUID.fromString(id))
        return maybeCategory?.let { category -> CategoryResponse.from(category) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: CategoryRequest): CategoryResponse {
        val category = service.update(UUID.fromString(id), request.toUpdateCategory())
        return CategoryResponse.from(category)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}