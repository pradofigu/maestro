package br.com.pradofigu.maestro.output.persistence.category.adapter

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.ports.output.CategoryDataAccessPort
import br.com.pradofigu.maestro.output.persistence.category.repository.CategoryRepository
import java.util.UUID

class CategoryDataAccessAdapter(
    private val categoryRepository: CategoryRepository
): CategoryDataAccessPort {

    override fun save(category: Category.CreateCategory): Category? {
        return categoryRepository.save(category)
    }

    override fun findBy(id: UUID): Category? {
        return categoryRepository.findBy(id)
    }

    override fun update(id: UUID, category: Category.UpdateCategory): Category? {
        return categoryRepository.update(id, category)
    }

    override fun delete(id: UUID): Boolean {
        return categoryRepository.delete(id)
    }
}