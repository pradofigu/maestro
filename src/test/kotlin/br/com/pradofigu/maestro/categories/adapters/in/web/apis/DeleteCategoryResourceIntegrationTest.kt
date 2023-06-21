package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Category.CATEGORY
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CategoryRecord
import org.jooq.DSLContext
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class DeleteCategoryResourceIntegrationTest(
    @Autowired val deleteCategoryResource: DeleteCategoryResource,
    @Autowired val mvc: MockMvc,
    @Autowired val context: DSLContext
) {

    @Test
    @Rollback
    @Transactional
    @Throws(Exception::class)
    fun `When get a category by id should returns 200`() {

        val categorySaved = CategoryRecord().setId(UUID.randomUUID()).setName("Sushi")

        context.insertInto(CATEGORY).set(categorySaved).execute()

        val mvcResult = mvc.perform(delete("/categories/${categorySaved.id}"))
            .andExpect(status().isOk())
            .andExpect(request().asyncStarted())
            .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNoContent())
    }

}