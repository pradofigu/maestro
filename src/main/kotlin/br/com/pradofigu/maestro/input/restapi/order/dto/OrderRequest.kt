package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus

data class OrderRequest(
    val number: Long,
    val customer: Customer,
    val paymentStatus: PaymentStatus
) {
    fun toModel() = Order(
        number = number,
        customer = customer,
        paymentStatus = paymentStatus
    )
}