package br.com.pradofigu.maestro.domain.product.ports.input

import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.UUID

interface ProductInputPort {

    suspend fun register(product: Product): Product

    suspend fun findBy(id: UUID): Product?

    suspend fun findByCategory(categoryId: UUID): List<Product>

    suspend fun update(id: UUID, product: Product): Product

    suspend fun delete(id: UUID)
}