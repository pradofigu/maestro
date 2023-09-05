package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.usecase.model.*
import br.com.pradofigu.maestro.usecase.persistence.CustomerDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.OrderDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.OrderTrackingDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.ProductDataAccessPort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.UUID

@Service
class OrderService(
    private val orderDataAccessPort: OrderDataAccessPort,
    private val orderTrackingDataAccessPort: OrderTrackingDataAccessPort,
    private val customerDataAccessPort: CustomerDataAccessPort,
    private val productDataAccessPort: ProductDataAccessPort
) {

    @Transactional
    suspend fun create(order: CreateOrder): Order {
        val customer = if (order.customerId != null) {
            customerDataAccessPort.findBy(order.customerId)
        } else null

        val products = order.productsId.map {
            productDataAccessPort.findById(it)?: throw RuntimeException("Product not found for given id $it")
        }

        return orderDataAccessPort.save(Order(customer = customer, products = products)).also {
            orderTrackingDataAccessPort.save(OrderTracking(order = it))
        }
    }

    suspend fun findByNumber(number: Long): Order? = orderDataAccessPort.findByNumber(number)

    @Transactional
    suspend fun processPayment(order: Order): Order = orderDataAccessPort.save(order).also {
        val orderTracking = orderTrackingDataAccessPort.findByOrderId(order.id!!)
            ?: OrderTracking(order = order)

        orderTrackingDataAccessPort.save(orderTracking.copy(status = OrderStatus.RECEIVED, products = order.products))
    }

    suspend fun findTrackingDetails(orderId: UUID): OrderTracking? =
        orderTrackingDataAccessPort.findByOrderId(orderId)

    suspend fun updateOrderTracking(orderId: UUID, orderStatus: OrderStatus): OrderTracking =
        orderTrackingDataAccessPort.updateOrderStatus(orderId, orderStatus)

    suspend fun findAll(): List<OrderTracking> {
        //TODO: order by status > READY | IN_PREPARATION | RECEIVED
        val customOrder = listOf(OrderStatus.READY, OrderStatus.IN_PREPARATION, OrderStatus.RECEIVED)

        return orderTrackingDataAccessPort.findAll().sortedWith(compareBy { customOrder.indexOf(it.status) })
    }
}