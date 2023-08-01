package br.com.pradofigu.maestro.web.order

import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.factory.CustomerFactory
import br.com.pradofigu.maestro.factory.OrderFactory
import br.com.pradofigu.maestro.factory.ProductFactory
import br.com.pradofigu.maestro.web.dto.CreateOrderRequest
import br.com.pradofigu.maestro.web.dto.PayOrderRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/orders")
class OrderDataAccessPortResourceIntegrationTest {

    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper
    @Autowired private lateinit var orderFactory: OrderFactory
    @Autowired private lateinit var customerFactory: CustomerFactory
    @Autowired private lateinit var productFactory: ProductFactory

    @Nested
    @Transactional
    inner class HappyPathIntegrationTest {

        @Test
        @Rollback
        fun `When create an order should returns 201`() {
            val customer = customerFactory.create()
            val products = listOf(productFactory.create(), productFactory.create())

            val request = CreateOrderRequest(
                customerId = customer.id,
                productsId = products.map { it.id!! }
            )

            val body: String = objectMapper.writeValueAsString(request)

            val mvcResult = mvc.perform(
                post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("number").isNotEmpty())
                .andReturn()
        }

        @Test
        @Rollback
        fun `When order is paid should returns 202`() {
            val defaultPendingOrder = orderFactory.create()

            val request = PayOrderRequest(
                number = defaultPendingOrder.number,
                status = PaymentStatus.PAID,
            )

            val body: String = objectMapper.writeValueAsString(request)

            val mvcResult = mvc.perform(
                put("/orders/${defaultPendingOrder.id}/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(defaultPendingOrder.id.toString()))
                .andExpect(jsonPath("number").value(defaultPendingOrder.number))
                .andExpect(jsonPath("paymentStatus").value("PAID"))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When get an order by number should returns 200`() {
            val order = orderFactory.create()

            val mvcResult = mvc.perform(
                get("/orders/number/${order.number}")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(order.id.toString()))
                .andExpect(jsonPath("number").value(order.number))
                .andExpect(jsonPath("customerId").isNotEmpty)
                .andExpect(jsonPath("paymentStatus").value("PENDING"))
        }
    }
}