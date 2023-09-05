package br.com.pradofigu.maestro.web.controller.order

import br.com.pradofigu.maestro.usecase.model.CreateOrder
import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import br.com.pradofigu.maestro.usecase.service.OrderService
import br.com.pradofigu.maestro.web.controller.OrderController
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@SpringBootTest(classes = [OrderController::class])
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `When creating an order, it should return a 201 status`() {
        val createOrderRequest = CreateOrder(
                customerId = UUID.randomUUID(),
                productsId = listOf(UUID.randomUUID(), UUID.randomUUID())
        )

        val createOrderResponseJson = mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderRequest.toJson())
        )
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString

        val orderId = jacksonObjectMapper().readTree(createOrderResponseJson).get("id").textValue()

        val createdOrder = runBlocking { orderService.findByNumber(orderId.toLong()) }

        assertNotNull(createdOrder)
        assertEquals(createOrderRequest.customerId, createdOrder?.customer?.id)
        assertEquals(PaymentStatus.PENDING, createdOrder?.paymentStatus)
    }

    @Test
    fun `When processing a payment, it should return a 200 status`() {
        val createOrderRequest = CreateOrder(
                customerId = UUID.randomUUID(),
                productsId = listOf(UUID.randomUUID(), UUID.randomUUID())
        )

        val createOrderResponseJson = mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderRequest.toJson())
        )
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString

        val orderId = jacksonObjectMapper().readTree(createOrderResponseJson).get("id").textValue()

        val orderPaymentRequest = Order(
                id = UUID.randomUUID(),
                number = orderId.toLong(),
                paymentStatus = PaymentStatus.PAID
        )

        mockMvc.perform(
                put("/orders/$orderId/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderPaymentRequest.toJson())
        )
                .andExpect(status().isOk)

        val paidOrder = runBlocking { orderService.findByNumber(orderId.toLong()) }

        assertNotNull(paidOrder)
        assertEquals(orderPaymentRequest.paymentStatus, paidOrder?.paymentStatus)
    }

    @Test
    fun `When finding an order by number, it should return a 200 status`() {
        val createOrderRequest = CreateOrder(
                customerId = UUID.randomUUID(),
                productsId = listOf(UUID.randomUUID(), UUID.randomUUID())
        )

        val createOrderResponseJson = mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderRequest.toJson())
        )
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString

        val orderId = jacksonObjectMapper().readTree(createOrderResponseJson).get("id").textValue()

        mockMvc.perform(
                get("/orders/number/{number}", orderId)
        )
                .andExpect(status().isOk)

        runBlocking {
            val foundOrder = orderService.findByNumber(orderId.toLong())
            assertNotNull(foundOrder)
        }
    }

    private fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}
