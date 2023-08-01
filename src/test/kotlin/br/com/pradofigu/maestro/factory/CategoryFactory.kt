package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.persistence.repository.CategoryRepository
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class CategoryFactory(private val categoryRepository: CategoryRepository) {

        fun create(name: String = "Category ${Random.nextInt(1, 9999)}") =
                categoryRepository.save(Category(name = name))
}