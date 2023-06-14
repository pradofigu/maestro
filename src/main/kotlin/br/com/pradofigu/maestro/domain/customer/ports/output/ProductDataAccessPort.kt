package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.Product
import java.util.*

interface ProductDataAccessPort {

    suspend fun save(product: Product): Product?

    suspend fun findAll(): List<Product>

    suspend fun findById(id: UUID): Product?

    suspend fun findByCategory(category: String): List<Product>

    suspend fun deleteById(id: UUID)
}