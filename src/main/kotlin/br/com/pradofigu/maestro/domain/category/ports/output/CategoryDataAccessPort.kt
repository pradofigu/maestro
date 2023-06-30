package br.com.pradofigu.maestro.domain.category.ports.output

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

interface CategoryDataAccessPort {

    fun findBy(id: UUID): Category?

    fun save(category: Category): Category

    fun update(id: UUID, category: Category): Category

    fun delete(id: UUID)
}