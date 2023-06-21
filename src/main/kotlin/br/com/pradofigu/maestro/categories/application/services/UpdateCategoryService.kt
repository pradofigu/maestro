package br.com.pradofigu.maestro.categories.application.services

import br.com.pradofigu.maestro.categories.application.ports.`in`.UpdateCategoryInPort
import br.com.pradofigu.maestro.categories.application.ports.out.UpdateCategoryOutPort
import br.com.pradofigu.maestro.categories.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateCategoryService(@Autowired private val repository: UpdateCategoryOutPort) : UpdateCategoryInPort {

    override fun update(id: UUID, category: Category.UpdateCategory): Category {
        return repository.update(id, category) ?: throw IllegalArgumentException("Error to update category")
    }

}
