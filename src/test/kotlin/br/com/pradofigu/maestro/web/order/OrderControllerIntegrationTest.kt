package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.model.CreateOrder
import br.com.pradofigu.maestro.usecase.model.OrderPayment
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import br.com.pradofigu.maestro.usecase.service.OrderService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
    }

    @Test
    suspend fun `When creating an order, it should return a 201 status`() {
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

        val createdOrder = orderService.findByNumber(orderId.toLong())
        assertNotNull(createdOrder)
        assertEquals(createOrderRequest.customerId, createdOrder?.customerId)
        assertEquals(PaymentStatus.PENDING, createdOrder?.paymentStatus)
    }

    @Test
    suspend fun `When processing a payment, it should return a 200 status`() {
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

        val orderPaymentRequest = OrderPayment(
                id = UUID.randomUUID(),
                number = orderId.toLong(),
                status = PaymentStatus.PAID
        )

        mockMvc.perform(
                put("/orders/$orderId/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderPaymentRequest.toJson())
        )
                .andExpect(status().isOk)

        val paidOrder = orderService.findByNumber(orderId.toLong())
        assertNotNull(paidOrder)
        assertEquals(orderPaymentRequest.status, paidOrder?.paymentStatus)
    }

    @Test
    suspend fun `When finding an order by number, it should return a 200 status`() {
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

        val foundOrder = orderService.findByNumber(orderId.toLong())
        assertNotNull(foundOrder)
    }

    private fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}
