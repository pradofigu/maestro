package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.model.Product
import java.math.BigDecimal
import java.util.UUID

object ProductFactory {

    fun create(customCategory: Category? = null, batchSize: Int = 1): List<Product> = List(batchSize) {
        Product(
            id = UUID.randomUUID(),
            name = "Product $batchSize",
            description = "Lorem Ipsum",
            imageUrl = "https://my-image.com",
            price = BigDecimal(100),
            category = customCategory ?: CategoryFactory.create("Category $batchSize"),
            preparationTime = 10
        )
    }
}