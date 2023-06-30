package br.com.pradofigu.maestro.input.restapi.category

import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CategoryControllerIntegrationTest(
    private val mvc: MockMvc,
    private val objectMapper: ObjectMapper
) {

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private var categoryId: String? = null

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun `When create a category should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                CategoryRequest("Chef's Plate")
            )

            val mvcResult = mvc.perform(
                post("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("Chef's Plate"))
                .andReturn()

            this.categoryId = objectMapper.readValue(response.response.toString(), CategoryResponse::class.java).id

            Assertions.assertNotNull(categoryId, "Created test didn't return the category id")
        }

        @Test
        @Order(2)
        @Throws(java.lang.Exception::class)
        fun `When get a category by id should returns 200`() {
            val mvcResult = mvc.perform(
                get("/categories/$categoryId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(categoryId))
                .andExpect(jsonPath("name").value("Chef's Plate"))
        }

        @Test
        @Order(3)
        @Throws(java.lang.Exception::class)
        fun `When update a category should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                CategoryRequest("Special Chef's Plate")
            )

            val mvcResult = mvc.perform(
                put("/categories/${categoryId}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(categoryId))
                .andExpect(jsonPath("name").value("Special Chef's Plate"))
                .andReturn()
        }

        @Test
        @Order(4)
        @Throws(java.lang.Exception::class)
        fun `When delete a category should returns 204`() {
            val mvcResult = mvc.perform(delete("/categories/${categoryId}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }

    }

}