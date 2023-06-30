package br.com.pradofigu.maestro.domain.product.ports.output

import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.UUID

interface ProductDataAccessPort {

    fun findBy(id: UUID): Product?

    fun findBy(category: String): List<Product>

    fun update(id: UUID, product: Product): Product

    fun save(product: Product): Product

    fun delete(id: UUID)
}
