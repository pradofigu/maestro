package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import br.com.pradofigu.maestro.domain.orders.Order.UpdateStatus
import java.math.BigDecimal
import java.util.*

data class OrderRequest(
        val customerId: UUID,
        val orderNumber: Long,
        val customer: Customer,
        val products: List<Product>,
        val totalPrice: BigDecimal,
        val statusOrder: String,
) {
    fun toCreateOrder() : CreateOrder {
        return CreateOrder(
                customerId = this.customerId,
                products = this.products,
                totalPrice = this.totalPrice,
        )
    }

    fun toUpdateStatus() : UpdateStatus {
        return UpdateStatus(
                orderNumber = this.orderNumber,
                statusOrder = this.statusOrder,
        )
    }

}