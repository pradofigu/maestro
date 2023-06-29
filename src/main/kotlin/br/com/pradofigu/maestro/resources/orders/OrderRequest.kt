package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.PaymentStatus
import br.com.pradofigu.maestro.domain.products.Product

data class OrderRequest(
    val number: Long,
    val customer: Customer,
    val products: List<Product>,
    val paymentStatus: PaymentStatus
) {
    fun toCreateOrder() : Order {
        return Order(
                customer = this.customer,
                products = this.products,
                paymentStatus = this.paymentStatus
        )
    }
    fun toUpdatePaymentStatus() : Order {
        return Order(
                paymentStatus = this.paymentStatus
        )
    }

}