package br.com.pradofigu.maestro.output.database.customer.entity

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.model.Order
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    val orderNumber: Long,

    @OneToOne
    val customer: Customer,

    @OneToMany(mappedBy = "order")
    val products: List<Products>,

    val totalPrice: BigDecimal,

    val statusOrder: String,

    @CreatedDate
    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime = LocalDateTime.now()
){
    fun toModel() = Order(
        id = id,
        orderNumber = orderNumber,
        customer = customer,
        products = products,
        totalPrice = totalPrice,
        statusOrder = statusOrder,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    companion object {
        fun fromModel(order: Order) = OrderEntity(
            id = order.id,
            orderNumber = order.orderNumber,
            customer = order.customer,
            products = order.products,
            totalPrice = order.totalPrice,
            statusOrder = order.statusOrder,
            createdAt = order.createdAt,
            updatedAt = order.updatedAt

        )
    }
}