package br.com.pradofigu.maestro.domain.product.ports.output

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.model.Product.*
import java.util.*

interface ProductDataAccessPort {

    fun save(product: CreateProduct): Product?

    fun findBy(id: UUID): Product?

    fun update(id: UUID, product: UpdateProduct): Product?

    fun delete(id: UUID): Boolean
    fun findBy(category: String): List<Product>

}
