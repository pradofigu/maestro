package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.application.ports.`in`.FindCategoryInPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class FindCategoryResource(@Autowired private val service: FindCategoryInPort) {

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): CategoryResponse? {
        val maybeCategory = service.findBy(UUID.fromString(id))
        return maybeCategory?.let { category -> CategoryResponse.from(category) }
    }

}
