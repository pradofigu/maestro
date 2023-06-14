package br.com.pradofigu.maestro.output.database.customer.adapter

import br.com.pradofigu.maestro.domain.customer.model.Product
import br.com.pradofigu.maestro.domain.customer.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.output.database.customer.entity.ProductEntity
import br.com.pradofigu.maestro.output.database.customer.repository.ProductRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductDataAccessAdapter(
    private val productRepository: ProductRepository
) : ProductDataAccessPort {
    override suspend fun save(product: Product): Product? {
        return productRepository.save(ProductEntity.fromModel(product)).awaitSingleOrNull()?.toModel()
    }

    override suspend fun findAll(): List<Product> {
        return productRepository.findAll()
            .asFlow()
            .toList()
            .map { it.toModel() }
    }

    override suspend fun findById(id: UUID): Product? {
        return productRepository.findById(id).awaitSingleOrNull()?.toModel()
    }

    override suspend fun deleteById(id: UUID) {
        productRepository.deleteById(id)
    }

    override suspend fun findByCategory(category: String): List<Product> {
        return productRepository.findByCategory(category)
            .asFlow()
            .toList()
            .map { it.toModel() }
    }
}