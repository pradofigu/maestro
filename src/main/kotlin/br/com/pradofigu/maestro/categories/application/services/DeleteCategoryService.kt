package br.com.pradofigu.maestro.categories.application.services

import br.com.pradofigu.maestro.categories.application.ports.`in`.DeleteCategoryInPort
import br.com.pradofigu.maestro.categories.application.ports.out.DeleteCategoryOutPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteCategoryService(@Autowired private val repository: DeleteCategoryOutPort) : DeleteCategoryInPort {

    override fun deleteBy(id: UUID): Boolean {
        return repository.deleteBy(id)
    }

}
