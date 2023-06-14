package br.com.pradofigu.maestro.domain.customer.ports.input

import br.com.pradofigu.maestro.domain.customer.model.Product
import java.util.*

interface ProductInputPort {

    suspend fun findAll(): List<Product>

    suspend fun findById(id: UUID): Product?

    suspend fun findByCategory(category: String): List<Product>

    suspend fun create(product: Product): Product?

    suspend fun update(product: Product): Product?

    suspend fun deleteById(id: UUID)
}