package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.input.restapi.order.controller.OrderController
import br.com.pradofigu.maestro.input.restapi.order.dto.OrderRequest
import br.com.pradofigu.maestro.resources.products.ProductRequest
import br.com.pradofigu.maestro.resources.products.ProductResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/orders")
class OrderDataAccessPortResourceIntegrationTest(
    @Autowired val orderController: OrderController,
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {

        private var orderId: String? = null

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun `When create a orders should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                    OrderRequest(
                            "X-Bacon",
                            BigDecimal("34.90"),
                            "Lanche",
                            BigDecimal("35")
                    )
            )

            val mvcResult = mvc.perform(
                    MockMvcRequestBuilders.post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn();

            val response = mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("name").value("X-Bacon"))
                    .andExpect(MockMvcResultMatchers.jsonPath("price").value(34.90))
                    .andExpect(MockMvcResultMatchers.jsonPath("category").value("Lanche"))
                    .andExpect(MockMvcResultMatchers.jsonPath("preparation_time").value(35))
                    .andReturn()

            this.productId =
                    objectMapper.readValue(response.response.contentAsString, ProductResponse::class.java).id
            Assertions.assertNotNull(productId, "Created test didn't return the product id")
        }

        @Test
        @Order(2)
        @Throws(java.lang.Exception::class)
        fun `When get a product by id should returns 200`() {
            val mvcResult = mvc.perform(MockMvcRequestBuilders.get("/products/$productId")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").value(productId))
                    .andExpect(MockMvcResultMatchers.jsonPath("name").value("X-Bacon"))
                    .andExpect(MockMvcResultMatchers.jsonPath("price").value(34.90))
                    .andExpect(MockMvcResultMatchers.jsonPath("category").value("Lanche"))
                    .andExpect(MockMvcResultMatchers.jsonPath("preparation_time").value(35))
        }

        @Test
        @Order(3)
        @Throws(java.lang.Exception::class)
        fun `When get a product by category should returns 200`() {
            val mvcResult = mvc.perform(MockMvcRequestBuilders.get("/products/category/Lanche")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("[*].id", Matchers.hasItem(productId)))
                    .andExpect(MockMvcResultMatchers.jsonPath("[*].name", Matchers.hasItem("X-Bacon")))
                    .andExpect(MockMvcResultMatchers.jsonPath("[*].price", Matchers.hasItem(34.90)))
                    .andExpect(MockMvcResultMatchers.jsonPath("[*].category", Matchers.hasItem("Lanche")))
                    .andExpect(MockMvcResultMatchers.jsonPath("[*].preparation_time", Matchers.hasItem(35)))
        }

        @Test
        @Order(4)
        @Throws(java.lang.Exception::class)
        fun `When update a product should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                    ProductRequest(
                            "X-Bacon",
                            BigDecimal("39.90"),
                            "Lanche",
                            BigDecimal("35")
                    )
            )
            val mvcResult = mvc.perform(
                    MockMvcRequestBuilders.put("/products/${productId}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn();

            mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").value(productId))
                    .andExpect(MockMvcResultMatchers.jsonPath("name").value("X-Bacon"))
                    .andExpect(MockMvcResultMatchers.jsonPath("price").value(39.90))
                    .andExpect(MockMvcResultMatchers.jsonPath("category").value("Lanche"))
                    .andExpect(MockMvcResultMatchers.jsonPath("preparation_time").value(35))
                    .andReturn()
        }

        @Test
        @Order(5)
        @Throws(java.lang.Exception::class)
        fun `When delete a product should returns 204`() {
            val mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/products/${productId}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn();

            mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
        }


    }

}