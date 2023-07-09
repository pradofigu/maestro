package br.com.pradofigu.maestro.domain.category.ports.input

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

interface CategoryInputPort {
    suspend fun create(category: Category): Category

    suspend fun findBy(id: UUID): Category?

    suspend fun update(category: Category): Category

    suspend fun delete(id: UUID)
}