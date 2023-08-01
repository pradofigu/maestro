package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.Category
import java.util.UUID

interface CategoryDataAccessPort {

    suspend fun findAll(): List<Category>

    suspend fun findBy(id: UUID): Category?

    suspend fun save(category: Category): Category

    suspend fun update(category: Category): Category

    suspend fun delete(id: UUID)
}