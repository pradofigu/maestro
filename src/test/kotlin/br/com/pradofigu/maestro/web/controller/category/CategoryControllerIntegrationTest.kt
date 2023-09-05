package br.com.pradofigu.maestro.web.controller.category

import br.com.pradofigu.maestro.application.MaestroApplication
import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.service.CategoryService
import br.com.pradofigu.maestro.web.dto.CategoryRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@SpringBootTest(classes = [MaestroApplication::class])
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired private lateinit var mockMvc: MockMvc
//    @Autowired private lateinit var categoryService: CategoryService

    @Test
    fun `When create a category should return 201`() {
//        runBlocking {
            val categoryName = "Test Category " + System.currentTimeMillis()
            val categoryRequest = CategoryRequest(name = categoryName)

            mockMvc.perform(
                post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryRequest.toJson())
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value(categoryName))
//        }
    }

//    @Test
//    fun `When find all categories should return 200`() {
//        mockMvc.perform(get("/categories"))
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.length()").value(2))
//    }
//
//    @Test
//    fun `When find category by id should return 200`() {
//        runBlocking {
//            val categoryName = "Test Category " + System.currentTimeMillis()
//            val category = categoryService.create(Category(name = categoryName))
//
//            mockMvc.perform(get("/categories/${category.id}"))
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.name").value(categoryName))
//        }
//    }
//
//    @Test
//    fun `When update a category should return 200`() {
//        runBlocking {
//            val categoryName = "Test Category " + System.currentTimeMillis()
//            val category = categoryService.create(Category(name = categoryName))
//            val updatedCategoryRequest = CategoryRequest(name = "Updated Category")
//
//            mockMvc.perform(
//                put("/categories/${category.id}")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(updatedCategoryRequest.toJson())
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.name").value("Updated Category"))
//        }
//    }
//
//    @Test
//    fun `When delete a category should return 204`() {
//        runBlocking {
//            val categoryName = "Test Category " + System.currentTimeMillis()
//            val category = categoryService.create(Category(name = categoryName))
//
//            mockMvc.perform(delete("/categories/${category.id}"))
//                .andExpect(status().isNoContent)
//        }
//    }

    private fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}
