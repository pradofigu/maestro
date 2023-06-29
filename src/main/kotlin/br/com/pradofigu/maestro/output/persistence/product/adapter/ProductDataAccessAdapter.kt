package br.com.pradofigu.maestro.output.persistence.product.adapter

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.output.persistence.product.repository.ProductRepository
import java.util.UUID

class ProductDataAccessAdapter(
    private val productRepository: ProductRepository
): ProductDataAccessPort {
    override fun save(product: Product.CreateProduct): Product? {
        return productRepository.save(product)
    }

    override fun findBy(id: UUID): Product? {
        return productRepository.findBy(id)
    }

    override fun findBy(category: String): List<Product> {
        return productRepository.findBy(category)
    }

    override fun update(id: UUID, product: Product.UpdateProduct): Product? {
        return productRepository.update(id, product)
    }

    override fun delete(id: UUID): Boolean {
        return productRepository.delete(id)
    }
}