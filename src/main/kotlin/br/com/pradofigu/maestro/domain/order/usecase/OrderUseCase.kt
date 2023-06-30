package br.com.pradofigu.maestro.domain.order.usecase

import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.order.ports.input.OrderInputPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderUseCase(
    private val orderDataAccessPort: OrderDataAccessPort
): OrderInputPort {

    override fun createOrder(order: Order): Order = orderDataAccessPort.save(order)

    override fun findAll(): List<Order> = orderDataAccessPort.findAll()

    override fun findBy(id: UUID): Order? = orderDataAccessPort.findBy(id)

    override fun findBy(number: Long): Order? = orderDataAccessPort.findBy(number)

    override fun updatePaymentStatus(
        id: UUID,
        paymentStatus: PaymentStatus
    ): Order = orderDataAccessPort.update(id, paymentStatus)

    override fun delete(id: UUID) {
        orderDataAccessPort.delete(id)
    }
}