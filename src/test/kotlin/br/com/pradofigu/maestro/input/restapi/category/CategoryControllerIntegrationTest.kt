package br.com.pradofigu.maestro.input.restapi.category

import br.com.pradofigu.maestro.factory.CategoryFactory
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
P@DisplayName("/categories")
class CategoryControllerIntegrationTest {

    @Autowired private lateinit var categoryFactory: CategoryFactory
    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    inner class HappyPathIntegrationTest {

        @Test
        fun `When create a category should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                CategoryRequest(name = "Chef's Plate")
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
                .andExpect(jsonPath("name").value("Chef's Plate"))
                .andReturn()
        }

        @Test
        fun `When get a category by id should returns 200`() {
            val category = categoryFactory.create("Chef's Plate")!!

            val mvcResult = mvc.perform(
                get("/categories/${category.id}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(category.id))
                .andExpect(jsonPath("name").value("Chef's Plate"))
        }

        @Test
        fun `When update a category should returns 200`() {
            val category = categoryFactory.create("Special Chef's Plate")!!

            val body: String = objectMapper.writeValueAsString(
                CategoryRequest(name = category.name)
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
                .andExpect(jsonPath("id").value(category.id))
                .andExpect(jsonPath("name").value("Special Chef's Plate"))
                .andReturn()
        }

        @Test
        fun `When delete a category should returns 204`() {
            val category = categoryFactory.create("Special Chef's Plate")!!

            val mvcResult = mvc.perform(delete("/categories/${category.id}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}