package br.com.pradofigu.maestro.resources.categories

import br.com.pradofigu.maestro.domain.categories.Category
import br.com.pradofigu.maestro.domain.categories.Category.UpdateCategory
import java.util.*

interface Categories {

    fun save(category: Category.CreateCategory): Category?

    fun findBy(id: UUID): Category?

    fun update(id: UUID, category: UpdateCategory): Category?

    fun delete(id: UUID): Boolean
}