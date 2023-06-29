package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.orders.model.Order
import br.com.pradofigu.maestro.domain.orders.model.PaymentStatus
import br.com.pradofigu.maestro.domain.product.model.Product

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