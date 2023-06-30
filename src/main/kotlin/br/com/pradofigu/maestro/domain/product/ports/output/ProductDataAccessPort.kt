package br.com.pradofigu.maestro.domain.product.ports.output

import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.UUID

interface ProductDataAccessPort {

    suspend fun findBy(id: UUID): Product?

    suspend fun findBy(category: String): List<Product>

    suspend fun update(id: UUID, product: Product): Product

    suspend fun save(product: Product): Product

    suspend fun delete(id: UUID)
}
