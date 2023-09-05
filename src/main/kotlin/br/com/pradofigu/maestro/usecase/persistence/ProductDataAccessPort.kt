package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.Product
import java.util.UUID

interface ProductDataAccessPort {

    suspend fun findAll(): List<Product>

    suspend fun findById(id: UUID): Product?

    suspend fun findByCategory(categoryId: UUID): List<Product>

    suspend fun update(id: UUID, product: Product): Product

    suspend fun save(product: Product): Product

    suspend fun delete(id: UUID)
}
