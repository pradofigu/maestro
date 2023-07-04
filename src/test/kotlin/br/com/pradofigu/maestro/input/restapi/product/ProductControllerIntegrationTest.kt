package br.com.pradofigu.maestro.input.restapi.product

import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/products")
class ProductControllerIntegrationTest(
    private val mvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    private val lanche = CategoryRequest(UUID.fromString("c85b8201-29c4-495a-be86-7dd3a1d16b81"), "Lanche")

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private var productId: String? = null

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun `When create a products should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                ProductRequest(
                    "X-Bacon",
                    BigDecimal("34.90"),
                    lanche,
                    BigDecimal("35")
                )
            )

            val mvcResult = mvc.perform(
                post("/products")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(34.90))
                .andExpect(jsonPath("category").value(lanche.id))
                .andExpect(jsonPath("preparation_time").value(35))
                .andReturn()

            this.productId =
                objectMapper.readValue(response.response.contentAsString, ProductResponse::class.java).id
            assertNotNull(productId, "Created test didn't return the product id")
        }

        @Test
        @Order(2)
        @Throws(java.lang.Exception::class)
        fun `When get a product by id should returns 200`() {
            val mvcResult = mvc.perform(get("/products/$productId")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(productId))
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(34.90))
                .andExpect(jsonPath("category").value(lanche.id))
                .andExpect(jsonPath("preparation_time").value(35))
        }

        @Test
        @Order(3)
        @Throws(java.lang.Exception::class)
        fun `When get a product by category should returns 200`() {
            val mvcResult = mvc.perform(get("/products/category/${lanche.id}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id", hasItem(productId)))
                .andExpect(jsonPath("[*].name", hasItem("X-Bacon")))
                .andExpect(jsonPath("[*].price", hasItem(34.90)))
                .andExpect(jsonPath("[*].category", hasItem(lanche.id)))
                .andExpect(jsonPath("[*].preparation_time", hasItem(35)))
        }

        @Test
        @Order(4)
        @Throws(java.lang.Exception::class)
        fun `When update a product should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                ProductRequest(
                    "X-Bacon",
                    BigDecimal("39.90"),
                    lanche,
                    BigDecimal("35")
                )
            )
            val mvcResult = mvc.perform(
                put("/products/${productId}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(productId))
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(39.90))
                .andExpect(jsonPath("category").value(lanche.id))
                .andExpect(jsonPath("preparation_time").value(35))
                .andReturn()
        }

        @Test
        @Order(5)
        @Throws(java.lang.Exception::class)
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