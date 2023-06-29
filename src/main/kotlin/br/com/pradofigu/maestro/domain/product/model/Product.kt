package br.com.pradofigu.maestro.domain.products

import java.util.*
import java.math.BigDecimal
data class Product(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal) {

    class CreateProduct(
        val name: String,
        val price: BigDecimal,
        val category: String,
        val preparationTime: BigDecimal
    )

    class UpdateProduct(
        val name: String,
        val price: BigDecimal,
        val category: String,
        val preparationTime: BigDecimal
    )
}
