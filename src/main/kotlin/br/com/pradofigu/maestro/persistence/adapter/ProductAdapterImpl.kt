package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.persistence.repository.ProductRepository
import br.com.pradofigu.maestro.usecase.model.Product
import br.com.pradofigu.maestro.usecase.persistence.ProductDataAccessPort
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductAdapterImpl(
    private val productRepository: ProductRepository
): ProductDataAccessPort {

    override suspend fun save(product: Product): Product = runBlocking {
        productRepository.save(product.toEntity())
    }.toModel()

    override suspend fun findAll(): List<Product> = runBlocking {
        productRepository.findAll()
    }.map { it.toModel() }

    override suspend fun findById(id: UUID): Product? = runBlocking {
        productRepository.findByIdOrNull(id)?.toModel()
    }

    override suspend fun findByCategory(categoryId: UUID): List<Product> = runBlocking {
        productRepository.findByCategoryId(categoryId)
    }.map { it.toModel() }

    @Throws(DatabaseOperationException::class)
    override suspend fun update(id: UUID, product: Product): Product = runBlocking {
        val updatableProduct = productRepository.findByIdOrNull(id)?.copy(
            name = product.name,
            description = product.description,
            price = product.price,
            category = product.category.toEntity(),
            imageUrl = product.imageUrl,
            preparationTime = product.preparationTime
        )?: throw DatabaseOperationException("Error to update product: product not found", product)


        productRepository.save(updatableProduct).toModel()
    }

    override suspend fun delete(id: UUID) = runBlocking { productRepository.deleteById(id) }
}