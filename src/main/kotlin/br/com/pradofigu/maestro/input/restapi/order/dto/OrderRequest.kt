package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest

data class OrderRequest(
    val number: Long,
    val customer: CustomerRequest, // Check the possibility of a null customer (e.g. when the customer is not registered yet)
    val paymentStatus: PaymentStatus
) {
    fun toModel() = Order(
        number = number,
        customer = customer.toModel(),
        paymentStatus = paymentStatus
    )
}