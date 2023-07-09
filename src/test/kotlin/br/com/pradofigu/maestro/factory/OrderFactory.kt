package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class OrderFactory(
    private val customerFactory: CustomerFactory,
    private val orderRepository: OrderRepository
) {

    fun create(
        customerId: UUID? = null,
        paymentStatus: PaymentStatus = PaymentStatus.PENDING
    ): Order {
        val customer = if (customerId == null) {
            customerFactory.create()
        } else {
            customerFactory.findById(customerId)!!
        }

        return orderRepository.save(
            Order(
                number = Random.nextLong(1, 9999),
                customerId = customer.id,
                paymentStatus = paymentStatus
            )
        )
    }

    fun findById(id: UUID): Order? = orderRepository.findBy(id)
}