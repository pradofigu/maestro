package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Category
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CategoryRecord
import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.DSLContext
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

@SpringBootTest
@AutoConfigureMockMvc
class UpdateCategoryResourceIntegrationTest(
    @Autowired val updateCategoryResource: UpdateCategoryResource,
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val context: DSLContext
) {

    @Test
    @Rollback
    @Transactional
    @Throws(Exception::class)
    fun `When update a category should returns 200`() {

        val categorySaved = CategoryRecord().setId(UUID.randomUUID()).setName("Rolls")
        context.insertInto(Category.CATEGORY).set(categorySaved).execute()

        val request = CategoryRequest("Special Rolls")

        val body: String = objectMapper.writeValueAsString(request)

        val mvcResult = mvc.perform(
            put("/categories/${categorySaved.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(request().asyncStarted())
            .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(categorySaved.id.toString()))
            .andExpect(jsonPath("name").value(request.name))
            .andReturn()
    }

}