package br.com.pradofigu.maestro.input.restapi.product

import br.com.pradofigu.maestro.factory.CategoryFactory
import br.com.pradofigu.maestro.factory.ProductFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/products")
class ProductControllerIntegrationTest {

    @Autowired private lateinit var productFactory: ProductFactory
    @Autowired private lateinit var categoryFactory: CategoryFactory
    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Transactional
    inner class HappyPathIntegrationTest {

        @Test
        @Rollback
        fun `When create a products should returns 201`() {
            val product = productFactory.create()
            val body: String = objectMapper.writeValueAsString(product)

            val mvcResult = mvc.perform(
                post("/products")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(product.name))
                .andExpect(jsonPath("price").value(100.0))
                .andExpect(jsonPath("category.name").value(product.category.name))
                .andExpect(jsonPath("preparationTime").value(10.0))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When get a product by id should returns 200`() {
            val product = productFactory.create()

            val mvcResult = mvc.perform(get("/products/${product.id}")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(product.id.toString()))
                .andExpect(jsonPath("name").value(product.name))
                .andExpect(jsonPath("price").value(110.0))
                .andExpect(jsonPath("category.name").value(product.category.name))
                .andExpect(jsonPath("preparationTime").value(10.0))
        }

        @Test
        @Rollback
        fun `When get a product by category should returns 200`() {
            val category = categoryFactory.create()

            val product1 = productFactory.create(category)
            val product2 = productFactory.create(category)
            productFactory.create()

            val mvcResult = mvc.perform(get("/products/category/${category.id}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[0].id").value(product1.id))
                .andExpect(jsonPath("$[0].name").value(product1.name))
                .andExpect(jsonPath("$[0].category.name").value(category.name))
                .andExpect(jsonPath("$[1].id").value(product2.id))
                .andExpect(jsonPath("$[1].name").value(product2.name))
                .andExpect(jsonPath("$[1].category.name").value(category.name))
        }

        @Test
        @Rollback
        fun `When update a product should returns 200`() {
            val product = productFactory.create()
            val body: String = objectMapper.writeValueAsString(product.copy(name = "Updated product name"))

            val mvcResult = mvc.perform(
                put("/products/${product.id}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Updated product name"))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When delete a product should returns 204`() {
            val product = productFactory.create()

            val mvcResult = mvc.perform(delete("/products/${product.id}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}