package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.orders.Order.UpdatePaymentStatus
import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import br.com.pradofigu.maestro.domain.orders.PaymentStatus
import br.com.pradofigu.maestro.domain.products.Product
import java.math.BigDecimal
import java.util.*

data class OrderRequest(
        val id: UUID,
        val customerId: UUID,
        val number: Long,
        val customer: Customer,
        val products: List<Product>,
        val paymentStatus: PaymentStatus
) {
    fun toCreateOrder() : CreateOrder {
        return CreateOrder(
                customerId = this.customerId,
                products = this.products,
                paymentStatus = this.paymentStatus
        )
    }
    fun toUpdatePaymentStatus() : UpdatePaymentStatus {
        return UpdatePaymentStatus(
                paymentStatus = this.paymentStatus
        )
    }

}