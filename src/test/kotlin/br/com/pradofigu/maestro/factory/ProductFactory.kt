package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.model.Product
import br.com.pradofigu.maestro.persistence.repository.ProductRepository
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
                description = "Lorem Ipsum",
                imageUrl = "https://my-image.com",
                price = BigDecimal(100),
                category = category,
                preparationTime = 10
            ).toEntity()
        ).toModel()
    }

    fun create(products: List<Product>): List<Product> {
        return products.map { product ->
            val category = categoryFactory.create(product.category.name)
            productRepository.save(product.copy(category = category).toEntity()).toModel()
        }
    }
}