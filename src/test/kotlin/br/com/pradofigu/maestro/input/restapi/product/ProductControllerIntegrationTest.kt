package br.com.pradofigu.maestro.input.restapi.product

import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductRequest
import br.com.pradofigu.maestro.input.restapi.product.dto.ProductResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
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
class ProductControllerIntegrationTest {

    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private var productId: String = UUID.randomUUID().toString()
        private val sandwichCategory = CategoryRequest(
            id = UUID.fromString("c85b8201-29c4-495a-be86-7dd3a1d16b81"),
            name = "Lanche"
        )

        @Test
        @Order(1)
        fun `When create a products should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                ProductRequest(
                    name = "X-Bacon",
                    price = BigDecimal("34.90"),
                    category = sandwichCategory,
                    preparationTime = BigDecimal("35")
                )
            )

            val mvcResult = mvc.perform(
                post("/products")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(34.90))
                .andExpect(jsonPath("category").value(sandwichCategory.id))
                .andExpect(jsonPath("preparation_time").value(35))
                .andReturn()

            this.productId = objectMapper.readValue(
                response.response.contentAsString,
                ProductResponse::class.java
            ).id

            assertNotNull(productId, "Created test didn't return the product id")
        }

        @Test
        @Order(2)
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
                .andExpect(jsonPath("category").value(sandwichCategory.id))
                .andExpect(jsonPath("preparation_time").value(35))
        }

        @Test
        @Order(3)
        fun `When get a product by category should returns 200`() {
            val mvcResult = mvc.perform(get("/products/category/${sandwichCategory.id}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id", hasItem(productId)))
                .andExpect(jsonPath("[*].name", hasItem("X-Bacon")))
                .andExpect(jsonPath("[*].price", hasItem(34.90)))
                .andExpect(jsonPath("[*].category", hasItem(sandwichCategory.id)))
                .andExpect(jsonPath("[*].preparation_time", hasItem(35)))
        }

        @Test
        @Order(4)
        fun `When update a product should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                ProductRequest(
                    name = "X-Bacon",
                    price = BigDecimal("39.90"),
                    category = sandwichCategory,
                    preparationTime = BigDecimal("35")
                )
            )

            val mvcResult = mvc.perform(
                put("/products/${productId}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(productId))
                .andExpect(jsonPath("name").value("X-Bacon"))
                .andExpect(jsonPath("price").value(39.90))
                .andExpect(jsonPath("category").value(sandwichCategory.id))
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
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}