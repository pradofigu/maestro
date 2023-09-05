package br.com.pradofigu.maestro.usecase.model

import br.com.pradofigu.maestro.persistence.entity.ProductEntity
import java.math.BigDecimal
import java.util.*

data class Product(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val price: BigDecimal,
    val category: Category,
    val preparationTime: Int
) {

    fun toEntity(): ProductEntity = ProductEntity(
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        price = this.price,
        category = this.category.toEntity(),
        preparationTime = this.preparationTime
    ).apply { this.id = this@Product.id }
}

//data class ProductPreparation(
//    val id: UUID? = UUID.randomUUID(),
//    val preparationTime: Int
//)
