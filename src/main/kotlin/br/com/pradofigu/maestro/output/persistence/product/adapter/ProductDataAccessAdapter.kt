package br.com.pradofigu.maestro.output.persistence.product.adapter

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.output.persistence.product.repository.ProductRepository
import java.util.UUID

class ProductDataAccessAdapter(
    private val productRepository: ProductRepository
): ProductDataAccessPort {
    override fun findBy(id: UUID): Product? = productRepository.findBy(id)

    override fun findBy(category: String): List<Product> {
        return productRepository.findBy(category)
    }

    override fun save(product: Product): Product = productRepository.save(product)
        ?: throw DatabaseOperationException("Error to save product", product)

    override fun update(id: UUID, product: Product): Product {
        return productRepository.update(id, product)
            ?: throw DatabaseOperationException("Error to update product", product)
    }

    override fun delete(id: UUID) {
        productRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete product with ID $id")
        }
    }
}