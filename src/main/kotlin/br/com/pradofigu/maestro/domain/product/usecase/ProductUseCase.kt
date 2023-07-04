package br.com.pradofigu.maestro.domain.product.usecase

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.input.ProductInputPort
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductUseCase(
    private val productDataAccessPort: ProductDataAccessPort
): ProductInputPort {

    override suspend fun register(product: Product): Product = productDataAccessPort.save(product)

    override suspend fun findBy(id: UUID): Product? = productDataAccessPort.findBy(id)

    override suspend fun findByCategory(category: UUID): List<Product> = productDataAccessPort.findByCategory(category)

    override suspend fun update(id: UUID, product: Product): Product {
        return productDataAccessPort.update(id, product)
    }

    override suspend fun delete(id: UUID) {
        productDataAccessPort.delete(id)
    }
}