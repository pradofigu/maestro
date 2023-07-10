package br.com.pradofigu.maestro.domain.order.usecase

import br.com.pradofigu.maestro.domain.order.model.*
import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.domain.order.ports.input.OrderInputPort
import org.springframework.stereotype.Service

@Service
class OrderUseCase(private val orderDataAccessPort: OrderDataAccessPort): OrderInputPort {

    override suspend fun create(order: CreateOrder): PendingPaymentOrder = orderDataAccessPort.save(order)

    override suspend fun findByNumber(number: Long): Order? = orderDataAccessPort.findByNumber(number)

    override suspend fun process(orderPayment: OrderPayment): Order = orderDataAccessPort.process(orderPayment)

    override suspend fun findTracking(orderId: String): OrderTracking? = orderDataAccessPort.findTracking(orderId)

    override suspend fun findPreparationDetails(orderId: String): OrderTracking =
        orderDataAccessPort.findPreparationDetails(orderId)
}