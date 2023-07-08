package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.output.persistence.category.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryFactory(private val categoryRepository: CategoryRepository) {

        fun create(name: String) = categoryRepository.save(Category(name = name))
}