package br.com.pradofigu.maestro.input.restapi.order

import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.factory.CustomerFactory
import br.com.pradofigu.maestro.factory.OrderFactory
import br.com.pradofigu.maestro.factory.ProductFactory
import br.com.pradofigu.maestro.input.restapi.order.dto.CreateOrderRequest
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNull
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
    @Autowired private lateinit var orderRepository: OrderRepository
    @Autowired private lateinit var customerFactory: CustomerFactory
    @Autowired private lateinit var productFactory: ProductFactory

    @Nested
    @Transactional
    inner class HappyPathIntegrationTest {

//        @Test
//        @Rollback
//        fun `When create an order should returns 201`() {
//
//            val customer = customerFactory.create()
//            val products = listOf<Product>(productFactory.create(), productFactory.create())
//
//            val order = orderFactory.create()
////            CreateOrderRequest(customerId = customer.id, productsId = products.map { product -> product.id })
//
//            val body: String = objectMapper.writeValueAsString(orderFactory.create())
//
//            val mvcResult = mvc.perform(
//                post("/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body)
//            )
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").isNotEmpty())
//                .andExpect(jsonPath("number").value(order.number))
//                .andExpect(jsonPath("customerId").value(order.customerId.toString()))
//                .andExpect(jsonPath("paymentStatus").value("PENDING"))
//                .andReturn()
//        }
//
//        @Test
//        @Rollback
//        fun `When get a list of order should returns 200`() {
//            val pendingOrder = orderFactory.create()
//            val paidOrder = orderFactory.create(paymentStatus = PaymentStatus.PAID)
//            val rejectedOrder = orderFactory.create(paymentStatus = PaymentStatus.REJECT)
//
//            val mvcResult = mvc.perform(
//                get("/orders")
//                    .contentType(MediaType.APPLICATION_JSON)
//            )
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize<Any>(3)))
//                .andExpect(jsonPath("$[0].id").value(pendingOrder.id))
//                .andExpect(jsonPath("$[0].paymentStatus").value("PENDING"))
//                .andExpect(jsonPath("$[1].id").value(paidOrder.id))
//                .andExpect(jsonPath("$[1].paymentStatus").value("PAID"))
//                .andExpect(jsonPath("$[2].id").value(rejectedOrder.id))
//                .andExpect(jsonPath("$[2].paymentStatus").value("REJECT"))
//        }
//
//        @Test
//        @Rollback
//        fun `When get an order by id should returns 200`() {
//            val order = orderFactory.create()
//
//            val mvcResult = mvc.perform(
//                get("/orders/${order.id}")
//                .contentType(MediaType.APPLICATION_JSON)
//            )
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("id").value(order.id))
//                    .andExpect(jsonPath("number").value(order.number))
//                    .andExpect(jsonPath("customerId").value(order.customerId.toString()))
//                    .andExpect(jsonPath("paymentStatus").value("PENDING"))
//        }
//
//        @Test
//        @Rollback
//        fun `When get an order by number should returns 200`() {
//            val order = orderFactory.create()
//
//            val mvcResult = mvc.perform(
//                get("/orders/number/${order.number}")
//                    .contentType(MediaType.APPLICATION_JSON)
//            )
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(order.id))
//                .andExpect(jsonPath("number").value(order.number))
//                .andExpect(jsonPath("customerId").value(order.customerId.toString()))
//                .andExpect(jsonPath("paymentStatus").value("PENDING"))
//        }
//
//        @Test
//        @Rollback
//        fun `When update an order should returns 200`() {
//            val defaultPendingOrder = orderFactory.create()
//            val body: String = objectMapper.writeValueAsString(
//                defaultPendingOrder.copy(paymentStatus = PaymentStatus.PAID)
//            )
//
//            val mvcResult = mvc.perform(
//                put("/order/${defaultPendingOrder.id}")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body)
//            )
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(defaultPendingOrder.id))
//                .andExpect(jsonPath("paymentStatus").value("PAID"))
//                .andReturn()
//        }
//
//        @Test
//        @Rollback
//        fun `When delete an order should returns 204`() {
//            val order = orderFactory.create()
//            val mvcResult = mvc.perform(delete("/order/${order.id}"))
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn()
//
//            mvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isNoContent())
//
//            assertNull(orderFactory.findById(order.id!!))
//        }
    }
}