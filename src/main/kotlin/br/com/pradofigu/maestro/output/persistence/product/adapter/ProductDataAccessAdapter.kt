package br.com.pradofigu.maestro.output.persistence.product.adapter

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.output.persistence.product.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductDataAccessAdapter(
    private val productRepository: ProductRepository
): ProductDataAccessPort {
    override suspend fun findBy(id: UUID): Product? = productRepository.findBy(id)

    override suspend fun findByCategory(categoryId: UUID): List<Product> {
        return productRepository.findByCategory(categoryId)
    }

    override suspend fun save(product: Product): Product = productRepository.save(product)

    override suspend fun update(id: UUID, product: Product): Product {
        return productRepository.update(id, product)
            ?: throw DatabaseOperationException("Error to update product", product)
    }

    override suspend fun delete(id: UUID) {
        productRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete product with ID $id")
        }
    }
}