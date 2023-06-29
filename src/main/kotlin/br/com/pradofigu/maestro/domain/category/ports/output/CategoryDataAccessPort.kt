package br.com.pradofigu.maestro.domain.category.ports.output

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.category.model.Category.UpdateCategory
import java.util.*

interface CategoryDataAccessPort {

    fun save(category: Category.CreateCategory): Category?

    fun findBy(id: UUID): Category?

    fun update(id: UUID, category: UpdateCategory): Category?

    fun delete(id: UUID): Boolean
}