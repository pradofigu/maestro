package br.com.pradofigu.maestro.input.restapi.order

import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest
import br.com.pradofigu.maestro.input.restapi.order.dto.OrderRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/orders")
class OrderDataAccessPortResourceIntegrationTest {

    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private var productId = ""

        @Test
        @Order(1)
        fun `When create a orders should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                OrderRequest(
                    number = 1010L,
                    customer = CustomerRequest(
                        name = "John Dow",
                        cpf = "123.123.123-12",
                        email = "john@doe.co",
                        phone = "+5511999998888",
                        birthDate = LocalDate.now()
                    ),
                    paymentStatus = PaymentStatus.PENDING
                )
            )

            val mvcResult = mvc.perform(
                post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(34.90))
                .andExpect(jsonPath("category").value("Lanche"))
                .andExpect(jsonPath("preparation_time").value(35))
                .andReturn()

            productId = objectMapper.readValue(
                response.response.toString(),
                ProductResponse::class.java
            ).id

            assertNotNull(productId, "Created test didn't return the product id")
        }

        @Test
        @Order(2)
        fun `When get a product by id should returns 200`() {
            val mvcResult = mvc.perform(
                get("/products/$productId")
                .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(productId))
                    .andExpect(jsonPath("name").value("X-Bacon"))
                    .andExpect(jsonPath("price").value(34.90))
                    .andExpect(jsonPath("category").value("Lanche"))
                    .andExpect(jsonPath("preparation_time").value(35))
        }

        @Test
        @Order(3)
        fun `When get a product by category should returns 200`() {
            val mvcResult = mvc.perform(
                get("/products/category/Lanche")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(request().asyncStarted())
                    .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("[*].id", Matchers.hasItem(productId)))
                    .andExpect(jsonPath("[*].name", Matchers.hasItem("X-Bacon")))
                    .andExpect(jsonPath("[*].price", Matchers.hasItem(34.90)))
                    .andExpect(jsonPath("[*].category", Matchers.hasItem("Lanche")))
                    .andExpect(jsonPath("[*].preparation_time", Matchers.hasItem(35)))
        }

        @Test
        @Order(4)
        fun `When update a product should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                ProductRequest(
                    name = "X-Bacon",
                    price = BigDecimal("39.90"),
                    category = CategoryRequest(name = "Lanche"),
                    preparationTime = BigInteger("35")
                )
            )

            val mvcResult = mvc.perform(
                put("/products/${productId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(productId))
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(39.90))
                .andExpect(jsonPath("category").value("Lanche"))
                .andExpect(jsonPath("preparation_time").value(35))
                .andReturn()
        }

        @Test
        @Order(5)
        fun `When delete a product should returns 204`() {
            val mvcResult = mvc.perform(delete("/products/${productId}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}