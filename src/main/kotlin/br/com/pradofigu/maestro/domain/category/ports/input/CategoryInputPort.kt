package br.com.pradofigu.maestro.domain.category.ports.input

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

interface CategoryInputPort {
    fun create(category: Category): Category

    fun findBy(id: UUID): Category?

    fun update(id: UUID, category: Category): Category

    fun delete(id: UUID)
}