package br.com.pradofigu.maestro.customers.adapters.`in`.web.apis

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Customer
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CustomerRecord
import org.jooq.DSLContext
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
import java.time.LocalDate
import java.time.Month

@SpringBootTest
@AutoConfigureMockMvc
class FindCustomerResourceIntegrationTest(
    @Autowired val findCustomerResource: FindCustomerResource,
    @Autowired val mvc: MockMvc,
    @Autowired val context: DSLContext
) {

    @Test
    @Transactional
    @Rollback
    @Throws(java.lang.Exception::class)
    fun `When get a customer by cpf should returns 200`() {

        val record = CustomerRecord()
            .setName("John Smith")
            .setEmail("john.smith@example.com")
            .setCpf("91980968853")
            .setBirthDate(LocalDate.of(1980, Month.SEPTEMBER, 3))
            .setPhone("(11)9999-8888")

        context.insertInto(Customer.CUSTOMER).set(record).execute()

        val mvcResult = mvc.perform(get("/customers/cpf/${record.cpf}")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(request().asyncStarted())
            .andReturn()

        mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("name").value(record.name))
            .andExpect(jsonPath("cpf").value(record.cpf))
            .andExpect(jsonPath("email").value(record.email))
            .andExpect(jsonPath("phone").value(record.phone))
            .andExpect(jsonPath("birthDate").value(record.birthDate.toString()))
            .andReturn()
    }

}