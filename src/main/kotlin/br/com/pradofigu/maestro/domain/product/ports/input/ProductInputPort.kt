package br.com.pradofigu.maestro.domain.product.ports.input

import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.UUID

interface ProductInputPort {

    fun register(product: Product.CreateProduct): Product

    fun findBy(id: UUID): Product?

    fun findBy(category: String): List<Product>

    fun update(id: UUID, product: Product.UpdateProduct): Product

    fun delete(id: UUID): Boolean
}