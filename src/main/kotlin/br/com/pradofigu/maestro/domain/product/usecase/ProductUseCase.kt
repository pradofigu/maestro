package br.com.pradofigu.maestro.domain.products

import br.com.pradofigu.maestro.domain.products.Product.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class ProductService(@Autowired private val products: Products) {

    fun register(product: CreateProduct): Product {
        return products.save(product) ?:
         throw IllegalArgumentException("Error to save product")
    }

    fun findBy(id: UUID): Product? {
        return products.findBy(id)
    }

    fun findBy(category: String): List<Product> {
        return products.findBy(category)
    }

    fun update(id: UUID, product: UpdateProduct): Product {
        return products.update(id, product) ?:
        throw IllegalArgumentException("Error to update product")
    }

    fun delete(id: UUID): Boolean {
        return products.delete(id)
    }

}