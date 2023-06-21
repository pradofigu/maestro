package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

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

@SpringBootTest
@AutoConfigureMockMvc
class CreateCategoryResourceIntegrationTest(
    @Autowired val createCategoryResource: CreateCategoryResource,
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Test
    @Rollback
    @Transactional
    @Throws(Exception::class)
    fun `When create a category should returns 201`() {

        val request = CategoryRequest("Chef's Plate")

        val body: String = objectMapper.writeValueAsString(request)

        val mvcResult = mvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(request().asyncStarted())
            .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("name").value(request.name))
            .andReturn()

    }

}