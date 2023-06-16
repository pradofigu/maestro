package br.com.pradofigu.maestro.input.http.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.model.Order
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class OrderDTO(
        val id: UUID,
        val orderNumber: Long,
        val customer: Customer,
        val products: List<Product>,
        val totalPrice: BigDecimal,
        val statusOrder: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
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
}

fun Order.toDTO() = OrderDTO(
        id = id,
        orderNumber = orderNumber,
        customer = customer,
        products = products,
        totalPrice = totalPrice,
        statusOrder = statusOrder,
        createdAt = createdAt,
        updatedAt = updatedAt
)