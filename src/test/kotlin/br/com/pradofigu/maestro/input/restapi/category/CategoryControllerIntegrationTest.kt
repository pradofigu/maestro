package br.com.pradofigu.maestro.input.restapi.category

import br.com.pradofigu.maestro.factory.CategoryFactory
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
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
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CategoryControllerIntegrationTest {

    @Autowired private lateinit var categoryFactory: CategoryFactory
    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    @Transactional
    inner class HappyPathIntegrationTest {

        @Test
        @Rollback
        fun `When create a category should returns 201`() {
            val categoryName = "Chef's Plate ${Random.nextInt(1, 9999)}"
            val body: String = objectMapper.writeValueAsString(
                CategoryRequest(name = categoryName)
            )

            val mvcResult = mvc.perform(
                post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(categoryName))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When get a category by id should returns 200`() {
            val category = categoryFactory.create()

            val mvcResult = mvc.perform(
                get("/categories/${category.id}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(category.id.toString()))
                .andExpect(jsonPath("name").value(category.name))
        }

        @Test
        @Rollback
        fun `When update a category should returns 200`() {
            val category = categoryFactory.create()

            val body: String = objectMapper.writeValueAsString(
                category.copy(name = "New Special Chef's Plate")
            )

            val mvcResult = mvc.perform(
                put("/categories/${category.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(category.id.toString()))
                .andExpect(jsonPath("name").value("New Special Chef's Plate"))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When delete a category should returns 204`() {
            val category = categoryFactory.create()

            val mvcResult = mvc.perform(delete("/categories/${category.id}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}