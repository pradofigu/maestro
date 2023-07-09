package br.com.pradofigu.maestro.domain.product.usecase

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.input.ProductInputPort
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductUseCase(
    private val productDataAccessPort: ProductDataAccessPort
): ProductInputPort {

    override suspend fun register(product: Product): Product = productDataAccessPort.save(product)

    override suspend fun findAll(): List<Product> = productDataAccessPort.findAll()

    override suspend fun findBy(id: UUID): Product? = productDataAccessPort.findBy(id)

    override suspend fun findByCategory(categoryId: UUID): List<Product> =
        productDataAccessPort.findByCategory(categoryId)

    override suspend fun update(id: UUID, product: Product): Product = productDataAccessPort.update(id, product)

    override suspend fun delete(id: UUID) {
        productDataAccessPort.delete(id)
    }
}