package br.com.pradofigu.maestro.customers.adapters.`in`.web.apis

import com.fasterxml.jackson.databind.ObjectMapper
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
class RegisterCustomerResourceIntegrationTest(
    @Autowired val registerCustomerResource: RegisterCustomerResource,
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Test
    @Rollback
    @Transactional
    @Throws(Exception::class)
    fun `When create a customers should returns 201`() {

        val request = CustomerRequest(
            "John Smith",
            "09506336237",
            "john.smith@example.com.br",
            "(11)98555-4321",
            LocalDate.of(1980, Month.SEPTEMBER, 3)
        )
        val body: String = objectMapper.writeValueAsString(request)

        val mvcResult = mvc.perform(
            post("/customers")
                .contentType(APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(request().asyncStarted())
            .andReturn();

        mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("name").value(request.name))
            .andExpect(jsonPath("cpf").value(request.cpf))
            .andExpect(jsonPath("email").value(request.email))
            .andExpect(jsonPath("phone").value(request.phone))
            .andExpect(jsonPath("birthDate").value(request.birthDate.toString()))
            .andReturn()

    }

}