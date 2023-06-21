package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.orders.Order
import java.math.BigDecimal
import java.util.*

data class OrderResponse(
        val id: String,
        val orderNumber: Long,
        val customer: Customer,
        val products: List<Product>,
        val totalPrice: BigDecimal,
        val statusOrder: String,
) {
    companion object {
        fun from(order: Order) : OrderResponse {
            return OrderResponse(
                    id = order.id.toString(),
                    customer = order.customer,
                    orderNumber = order.orderNumber,
                    products = order.products,
                    totalPrice = order.totalPrice,
                    statusOrder = order.statusOrder
            )
        }
    }

}
