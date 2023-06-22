package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.OrderStatus
import br.com.pradofigu.maestro.domain.orders.PaymentStatus
import br.com.pradofigu.maestro.domain.products.Product
import java.math.BigDecimal
import java.util.*

data class OrderResponse(
        val id: String,
        val number: Long,
        val customer: Customer,
        val products: List<Product>,
        val status: OrderStatus,
        val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) : OrderResponse {
            return OrderResponse(
                    id = order.id.toString(),
                    customer = order.customer,
                    number = order.number,
                    products = order.products,
                    status = order.status,
                    paymentStatus = order.paymentStatus
            )
        }
    }

}
