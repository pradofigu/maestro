package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.persistence.CategoryDataAccessPort
import br.com.pradofigu.maestro.persistence.repository.CategoryRepository
import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CategoryAdapterImpl(
    private val categoryRepository: CategoryRepository
): CategoryDataAccessPort {

    override suspend fun findAll(): List<Category> = categoryRepository.findAll()

    override suspend fun findBy(id: UUID): Category? = categoryRepository.findBy(id)

    override suspend fun save(category: Category): Category = categoryRepository.save(category)

    override suspend fun update(category: Category): Category {
        return categoryRepository.update(category)
            ?: throw DatabaseOperationException(
                "Error to update category", category
            )
    }

    override suspend fun delete(id: UUID) {
        categoryRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete category with ID $id")
        }
    }
}