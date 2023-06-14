package br.com.pradofigu.maestro.domain.customer.usecase

import br.com.pradofigu.maestro.domain.customer.model.Product
import br.com.pradofigu.maestro.domain.customer.ports.input.ProductInputPort
import br.com.pradofigu.maestro.domain.customer.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductUseCase(private val productDataAccessPort: ProductDataAccessPort) : ProductInputPort {

    override suspend fun findAll() = productDataAccessPort.findAll()

    override suspend fun findById(id: UUID) = productDataAccessPort.findById(id)
        ?: throw ResourceNotFoundException("Product not found")

    override suspend fun findByCategory(category: String) = productDataAccessPort.findByCategory(category)

    override suspend fun create(product: Product) = productDataAccessPort.save(product)
        ?: throw IllegalStateException("Unable to save product")

    override suspend fun update(product: Product) = productDataAccessPort.save(product)
        ?: throw IllegalStateException("Unable to update product")

    override suspend fun deleteById(id: UUID) = productDataAccessPort.deleteById(id)

}