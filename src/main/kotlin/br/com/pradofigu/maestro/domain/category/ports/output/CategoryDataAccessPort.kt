package br.com.pradofigu.maestro.domain.category.ports.output

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

interface CategoryDataAccessPort {

    suspend fun findBy(id: UUID): Category?

    suspend fun save(category: Category): Category

    suspend fun update(category: Category): Category

    suspend fun delete(id: UUID)
}