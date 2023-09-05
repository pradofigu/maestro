package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.usecase.model.Product
import br.com.pradofigu.maestro.usecase.persistence.ProductDataAccessPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductService(
    private val productDataAccessPort: ProductDataAccessPort
) {

    suspend fun register(product: Product): Product = productDataAccessPort.save(product)

    suspend fun findAll(): List<Product> = productDataAccessPort.findAll()

    suspend fun findById(id: UUID): Product? = productDataAccessPort.findById(id)

    suspend fun findByCategory(categoryId: UUID): List<Product> =
        productDataAccessPort.findByCategory(categoryId)

    suspend fun update(id: UUID, product: Product): Product = productDataAccessPort.update(id, product)

    suspend fun delete(id: UUID) {
        productDataAccessPort.delete(id)
    }
}