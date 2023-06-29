package br.com.pradofigu.maestro.domain.product.usecase

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.model.Product.*
import br.com.pradofigu.maestro.domain.product.ports.input.ProductInputPort
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class ProductUseCase(@Autowired private val productDataAccessPort: ProductDataAccessPort): ProductInputPort {

    override fun register(product: CreateProduct): Product {
        return productDataAccessPort.save(product) ?:
         throw IllegalArgumentException("Error to save product")
    }

    override fun findBy(id: UUID): Product? {
        return productDataAccessPort.findBy(id)
    }

    override fun findBy(category: String): List<Product> {
        return productDataAccessPort.findBy(category)
    }

    override fun update(id: UUID, product: UpdateProduct): Product {
        return productDataAccessPort.update(id, product) ?:
        throw IllegalArgumentException("Error to update product")
    }

    override fun delete(id: UUID): Boolean {
        return productDataAccessPort.delete(id)
    }

}