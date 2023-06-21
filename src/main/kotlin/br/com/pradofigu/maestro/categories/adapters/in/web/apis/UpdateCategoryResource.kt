package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.application.ports.`in`.UpdateCategoryInPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UpdateCategoryResource(@Autowired private val service: UpdateCategoryInPort) {

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: CategoryRequest): CategoryResponse {
        val category = service.update(UUID.fromString(id), request.toUpdateCategory())
        return CategoryResponse.from(category)
    }

}
