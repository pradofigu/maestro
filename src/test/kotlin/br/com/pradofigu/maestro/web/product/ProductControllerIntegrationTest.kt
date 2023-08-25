package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.service.ProductService
import br.com.pradofigu.maestro.web.dto.CategoryRequest
import br.com.pradofigu.maestro.web.dto.ProductRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productService: ProductService

    @Test
    suspend fun `When registering a product, it should return a 201 status`() {
        val productRequest = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val createProductResponseJson = mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapper().writeValueAsString(productRequest))
        )
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString

        val productId = UUID.fromString(
                jacksonObjectMapper().readTree(createProductResponseJson).get("id").textValue()
        )

        val createdProduct = productService.findBy(productId)
        assertNotNull(createdProduct)
        assertEquals(productRequest.name, createdProduct?.name)
        assertEquals(productRequest.price, createdProduct?.price)
        assertEquals(productRequest.category.name, createdProduct?.category?.name)
        assertEquals(productRequest.description, createdProduct?.description)
        assertEquals(productRequest.imageUrl, createdProduct?.imageUrl)
        assertEquals(productRequest.preparationTime, createdProduct?.preparationTime)
    }

    @Test
    suspend fun `When finding all products, it should return a list with length 2`() {
        val product1 = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val product2 = ProductRequest(
                name = "Batata Frita",
                price = BigDecimal("5.99"),
                category = CategoryRequest(name = "Acompanhamento"),
                description = "Batatas fritas crocantes",
                imageUrl = "http://example.com/batatafrita.jpg",
                preparationTime = BigDecimal("15")
        )

        productService.register(product1.toModel())
        productService.register(product2.toModel())

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    suspend fun `When finding a product by ID, it should return a 200 status`() {
        val productRequest = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val createdProduct = productService.register(productRequest.toModel())

        mockMvc.perform(get("/products/${createdProduct.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(createdProduct.id.toString()))
                .andExpect(jsonPath("$.name").value(productRequest.name))
    }

    @Test
    suspend fun `When updating a product, it should return a 200 status`() {
        val productRequest = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val createdProduct = productService.register(productRequest.toModel())

        val updatedProductRequest = ProductRequest(
                name = "Hamburguer Especial",
                price = BigDecimal("11.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um hamburguer ainda mais delicioso",
                imageUrl = "http://example.com/hamburguer-especial.jpg",
                preparationTime = BigDecimal("20")
        )

        mockMvc.perform(
                put("/products/${createdProduct.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapper().writeValueAsString(updatedProductRequest))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(createdProduct.id.toString()))
                .andExpect(jsonPath("$.name").value(updatedProductRequest.name))
    }

    @Test
    suspend fun `When deleting a product, it should return a 204 status`() {
        val productRequest = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val createdProduct = productService.register(productRequest.toModel())

        mockMvc.perform(delete("/products/${createdProduct.id}"))
                .andExpect(status().isNoContent)

        val deletedProduct = createdProduct.id?.let { productService.findBy(it) }
        assertNull(deletedProduct)
    }

    @Test
    suspend fun `When finding products by category, it should return a 200 status`() {
        val product1 = ProductRequest(
                name = "Hamburguer",
                price = BigDecimal("9.99"),
                category = CategoryRequest(name = "Lanche"),
                description = "Um delicioso hamburguer",
                imageUrl = "http://example.com/hamburguer.jpg",
                preparationTime = BigDecimal("15")
        )

        val product2 = ProductRequest(
                name = "Batata Frita",
                price = BigDecimal("5.99"),
                category = CategoryRequest(name = "Acompanhamento"),
                description = "Batatas fritas crocantes",
                imageUrl = "http://example.com/batatafrita.jpg",
                preparationTime = BigDecimal("15")
        )

        productService.register(product1.toModel())
        productService.register(product2.toModel())

        mockMvc.perform(get("/products/category/${product1.category.name}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(1))
    }
}
