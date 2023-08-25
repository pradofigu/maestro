package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.service.CategoryService
import br.com.pradofigu.maestro.web.dto.CategoryRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(classes = [CategoryController::class])
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setUp() {
    }

    @Test
    suspend fun testCreateCategory() {
        val categoryRequest = CategoryRequest(name = "Test Category")

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryRequest.toJson())
        )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value("Test Category"))
    }

    @Test
    suspend fun testFindAllCategories() {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    suspend fun testFindCategoryById() {
        val category = categoryService.create(Category(name = "Test Category"))

        mockMvc.perform(get("/categories/${category.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Test Category"))
    }

    @Test
    suspend fun testUpdateCategory() {
        val category = categoryService.create(Category(name = "Test Category"))
        val updatedCategoryRequest = CategoryRequest(name = "Updated Category")

        mockMvc.perform(
                put("/categories/${category.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryRequest.toJson())
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Updated Category"))
    }

    @Test
    suspend fun testDeleteCategory() {
        val category = categoryService.create(Category(name = "Test Category"))

        mockMvc.perform(delete("/categories/${category.id}"))
                .andExpect(status().isNoContent)
    }

    private fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}
