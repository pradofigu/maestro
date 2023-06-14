package br.com.pradofigu.maestro.output.database.customer.entity

import br.com.pradofigu.maestro.domain.customer.model.Product
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    val name: String,

    val price: BigDecimal,

    val category: String,

    @CreatedDate
    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toModel() = Product(
        id = id,
        name = name,
        price = price,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromModel(product: Product) = ProductEntity(
            id = product.id,
            name = product.name,
            price = product.price,
            category = product.category,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
    }
}