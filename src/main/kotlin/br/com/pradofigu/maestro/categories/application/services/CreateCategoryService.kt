package br.com.pradofigu.maestro.categories.application.services

import br.com.pradofigu.maestro.categories.application.ports.`in`.CreateCategoryInPort
import br.com.pradofigu.maestro.categories.application.ports.out.CreateCategoryOutPort
import br.com.pradofigu.maestro.categories.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateCategoryService(@Autowired private val repository: CreateCategoryOutPort) : CreateCategoryInPort {

    override fun create(category: Category.CreateCategory): Category {
        return repository.save(category) ?: throw IllegalArgumentException("Error to create category")
    }
}
