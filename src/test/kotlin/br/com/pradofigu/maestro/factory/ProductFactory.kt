package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.output.persistence.product.repository.ProductRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.random.Random

@Component
class ProductFactory(
    private val categoryFactory: CategoryFactory,
    private val productRepository: ProductRepository
) {

    fun create(customCategory: Category? = null): Product {
        val category = customCategory ?: categoryFactory.create("Category ${Random.nextInt(1, 9999)}")

        return productRepository.save(
            Product(
                name = "Product ${Random.nextInt(1, 9999)}",
                price = BigDecimal(100),
                category = category,
                preparationTime = BigDecimal(10)
            )
        )
    }
}