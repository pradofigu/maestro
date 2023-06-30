package br.com.pradofigu.maestro.domain.category.usecase

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.ports.input.CategoryInputPort
import br.com.pradofigu.maestro.domain.category.ports.output.CategoryDataAccessPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.UUID

@Service
class CategoryUseCase(
    private val categoryDataAccessPort: CategoryDataAccessPort
): CategoryInputPort {

    override suspend fun create(category: Category): Category {
        return categoryDataAccessPort.save(category) ?: throw IllegalArgumentException("Error to create category")
    }

    override suspend fun findBy(id: UUID): Category? {
        return categoryDataAccessPort.findBy(id)
    }

    override suspend fun update(id: UUID, category: Category): Category {
        return categoryDataAccessPort.update(id, category) ?: throw IllegalArgumentException("Error to update category")
    }

    override suspend fun delete(id: UUID) {
        categoryDataAccessPort.delete(id)
    }
}
