package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.application.ports.`in`.CreateCategoryInPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CreateCategoryResource(@Autowired private val service: CreateCategoryInPort) {

    @PostMapping
    suspend fun create(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val category = service.create(request.toCreateCategory())
        return ResponseEntity(CategoryResponse.from(category), HttpStatus.CREATED)
    }

}
