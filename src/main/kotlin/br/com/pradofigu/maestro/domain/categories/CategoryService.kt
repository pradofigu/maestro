package br.com.pradofigu.maestro.domain.categories

import br.com.pradofigu.maestro.domain.categories.Category.CreateCategory
import br.com.pradofigu.maestro.domain.categories.Category.UpdateCategory
import br.com.pradofigu.maestro.resources.categories.Categories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class CategoryService(@Autowired private val categories: Categories) {

    fun create(category: CreateCategory): Category {
        return categories.save(category) ?: throw IllegalArgumentException("Error to create category")
    }

    fun findBy(id: UUID): Category? {
        return categories.findBy(id)
    }

    fun update(id: UUID, category: UpdateCategory): Category {
        return categories.update(id, category) ?: throw IllegalArgumentException("Error to update category")
    }

    fun delete(id: UUID): Boolean {
        return categories.delete(id)
    }

}
