package br.com.pradofigu.maestro.domain.orders.model

import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.*

data class Order(
    val id: UUID? = null,
    val number: Long? = null,
    val customer: Customer? = null,
    val products: List<Product>? = null,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
)
enum class PaymentStatus { PENDING, PAID, REJECT }
