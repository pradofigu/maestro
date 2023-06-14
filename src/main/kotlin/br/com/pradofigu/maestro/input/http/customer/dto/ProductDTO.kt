package br.com.pradofigu.maestro.input.http.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.Product
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class ProductDTO(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun toModel() = Product(
        id = id,
        name = name,
        price = price,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Product.toDTO() = ProductDTO(
    id = id,
    name = name,
    price = price,
    category = category,
    createdAt = createdAt,
    updatedAt = updatedAt
)
