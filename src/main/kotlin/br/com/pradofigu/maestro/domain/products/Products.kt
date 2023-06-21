package br.com.pradofigu.maestro.domain.products

import br.com.pradofigu.maestro.domain.products.Product.*
import java.util.*

interface Products {

    fun save(product: CreateProduct): Product?

    fun findBy(id: UUID): Product?

    fun update(id: UUID, product: UpdateProduct): Product?

    fun delete(id: UUID): Boolean
    fun findBy(category: String): List<Product>

}
