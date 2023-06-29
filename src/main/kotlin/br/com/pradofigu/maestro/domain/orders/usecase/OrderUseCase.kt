package br.com.pradofigu.maestro.domain.orders.usecase

import br.com.pradofigu.maestro.domain.orders.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.domain.orders.model.Order
import br.com.pradofigu.maestro.domain.orders.model.PaymentStatus
import br.com.pradofigu.maestro.domain.orders.ports.input.OrderInputPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderUseCase(private val orderDataAccessPort: OrderDataAccessPort): OrderInputPort {

    override fun createOrder(order: Order): Order = orderDataAccessPort.save(order)

    override fun findAll(): List<Order> = orderDataAccessPort.findAll()

    override fun findBy(id: UUID): Order = orderDataAccessPort.findBy(id)

    override fun updatePaymentStatus(id: UUID, paymentStatus: PaymentStatus) : Order = orderDataAccessPort.update(id, paymentStatus)

    override fun delete(id: UUID): Boolean = orderDataAccessPort.delete(id)
}