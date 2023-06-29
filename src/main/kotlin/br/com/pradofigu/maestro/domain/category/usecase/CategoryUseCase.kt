package br.com.pradofigu.maestro.domain.category.usecase

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.model.Category.CreateCategory
import br.com.pradofigu.maestro.domain.category.model.Category.UpdateCategory
import br.com.pradofigu.maestro.domain.category.ports.input.CategoryInputPort
import br.com.pradofigu.maestro.domain.category.ports.output.CategoryDataAccessPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.UUID

@Service
class CategoryUseCase(@Autowired private val categoryDataAccessPort: CategoryDataAccessPort): CategoryInputPort {

    override fun create(category: CreateCategory): Category {
        return categoryDataAccessPort.save(category) ?: throw IllegalArgumentException("Error to create category")
    }

    override fun findBy(id: UUID): Category? {
        return categoryDataAccessPort.findBy(id)
    }

    override fun update(id: UUID, category: UpdateCategory): Category {
        return categoryDataAccessPort.update(id, category) ?: throw IllegalArgumentException("Error to update category")
    }

    override fun delete(id: UUID): Boolean {
        return categoryDataAccessPort.delete(id)
    }
}
