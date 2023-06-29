package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.PaymentStatus
import br.com.pradofigu.maestro.domain.products.Product

data class OrderResponse(
    val id: String,
    val number: Long?,
    val customer: Customer?,
    val products: List<Product>?,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) : OrderResponse {
            return OrderResponse(
                    id = order.id.toString(),
                    customer = order.customer,
                    number = order.number,
                    products = order.products,
                    paymentStatus = order.paymentStatus
            )
        }
    }

}
