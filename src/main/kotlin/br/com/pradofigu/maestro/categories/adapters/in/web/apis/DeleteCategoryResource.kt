package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.application.ports.`in`.DeleteCategoryInPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DeleteCategoryResource(@Autowired private val service: DeleteCategoryInPort) {

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.deleteBy(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

}
