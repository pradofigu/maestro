package br.com.pradofigu.maestro.web.dto
import br.com.pradofigu.maestro.domain.product.model.Product
import java.math.BigDecimal

data class ProductResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: CategoryResponse,
    val description: String,
    val imageUrl: String?,
    val preparationTime: BigDecimal
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id!!.toString(),
            name = product.name,
            price = product.price,
            category = product.category.let { CategoryResponse.from(it) },
            description = product.description,
            imageUrl = product.imageUrl,
            preparationTime = product.preparationTime
        )
    }
}
