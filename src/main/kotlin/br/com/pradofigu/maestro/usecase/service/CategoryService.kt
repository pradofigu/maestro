package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.persistence.CategoryDataAccessPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(
    private val categoryDataAccessPort: CategoryDataAccessPort
) {

    suspend fun create(category: Category): Category {
        return categoryDataAccessPort.save(category)
    }

    suspend fun findAll(): List<Category> = categoryDataAccessPort.findAll()

    suspend fun findBy(id: UUID): Category? {
        return categoryDataAccessPort.findBy(id)
    }

    suspend fun update(category: Category): Category {
        return categoryDataAccessPort.update(category)
    }

    suspend fun delete(id: UUID) {
        categoryDataAccessPort.delete(id)
    }
}
