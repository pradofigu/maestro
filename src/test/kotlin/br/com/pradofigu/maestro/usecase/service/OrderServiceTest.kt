package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.factory.OrderFactory
import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking
import br.com.pradofigu.maestro.usecase.persistence.CustomerDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.OrderDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.OrderTrackingDataAccessPort
import br.com.pradofigu.maestro.usecase.persistence.ProductDataAccessPort
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class OrderTrackingServiceTest {

    @Mock private lateinit var orderDataAccessPort: OrderDataAccessPort
    @Mock private lateinit var customerDataAccessPort: CustomerDataAccessPort
    @Mock private lateinit var productDataAccessPort: ProductDataAccessPort
    @Mock private lateinit var orderTrackingDataAccessPort: OrderTrackingDataAccessPort

    @InjectMocks lateinit var orderService: OrderService

    @Test
    fun `findAll should return list ordered by status`() = runBlocking {
        val fakeOrder = OrderFactory.create()

        val unorderedList = listOf(
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.READY),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.READY),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.READY),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.READY),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.RECEIVED),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.RECEIVED),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.RECEIVED),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.IN_PREPARATION),
            OrderTracking(id = UUID.randomUUID(), order = fakeOrder, status = OrderStatus.IN_PREPARATION)
        )

        `when`(orderTrackingDataAccessPort.findAll()).thenReturn(unorderedList)

        val orders = orderService.findAll()

        // Map a fake number for each status
        val statusOrder = mapOf(
            OrderStatus.READY to 1,
            OrderStatus.IN_PREPARATION to 2,
            OrderStatus.RECEIVED to 3
        )

        // Check if the list is ordered by status, using the number map above
        val isOrdered = orders
            .map { it.status }
            .map { statusOrder[it] ?: throw IllegalArgumentException("Unknown status: $it") }
            .zipWithNext()
            .all { (a, b) -> a <= b }

        assertTrue(isOrdered)
        assert(orders.size == 9) // 9 because the other 1 is FINISHED
    }
}