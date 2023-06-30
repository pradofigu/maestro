package br.com.pradofigu.maestro.output.persistence.category.adapter

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.ports.output.CategoryDataAccessPort
import br.com.pradofigu.maestro.output.persistence.category.repository.CategoryRepository
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import java.util.UUID

class CategoryDataAccessAdapter(
    private val categoryRepository: CategoryRepository
): CategoryDataAccessPort {

    override fun findBy(id: UUID): Category? = categoryRepository.findBy(id)

    override fun save(category: Category): Category = categoryRepository.save(category)
        ?: throw DatabaseOperationException("Error to create category", category)

    override fun update(id: UUID, category: Category): Category {
        return categoryRepository.update(id, category)
            ?: throw DatabaseOperationException(
                "Error to update category",
                mapOf("id" to id, "category" to category)
            )
    }

    override fun delete(id: UUID) {
        categoryRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete category with ID $id")
        }
    }
}