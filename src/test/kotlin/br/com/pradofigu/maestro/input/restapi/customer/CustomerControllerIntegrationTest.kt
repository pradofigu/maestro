package br.com.pradofigu.maestro.input.restapi.customer

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.Month
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CustomerControllerIntegrationTest {

    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private val cpf = CPFValidator().generateRandomValid()
        private var customerId = UUID.randomUUID().toString()

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun `When create a customers should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    name = "John Smith",
                    cpf = cpf,
                    email = "john.smith@example.com.br",
                    phone = "(11)98555-4321",
                    birthDate = LocalDate.of(1980, Month.SEPTEMBER, 3)
                )
            )

            val mvcResult = mvc.perform(
                post("/customers")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(cpf))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()

            this.customerId = objectMapper.readValue(
                response.response.contentAsString,
                CustomerResponse::class.java
            ).id

            assertNotNull(customerId, "Created test didn't return the customer id")
        }

        @Test
        @Order(2)
        fun `When get a customer by id should returns 200`() {
            val mvcResult = mvc.perform(get("/customers/$customerId")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(cpf))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
        }

        @Test
        @Order(3)
        fun `When get a customer by cpf should returns 200`() {
            val mvcResult = mvc.perform(get("/customers/cpf/${cpf}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(cpf))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
        }

        @Test
        @Order(4)
        fun `When update a customer should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    name = "John Smith",
                    cpf = cpf,
                    email = "john.smith@example-altera-email.com.br",
                    phone = "(11)98555-4321",
                    birthDate = LocalDate.of(1980, Month.SEPTEMBER, 3)
                )
            )

            val mvcResult = mvc.perform(
                put("/customers/${customerId}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(cpf))
                .andExpect(jsonPath("email").value("john.smith@example-altera-email.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()
        }

        @Test
        @Order(5)
        fun `When delete a customer should returns 204`() {
            val mvcResult = mvc.perform(delete("/customers/${customerId}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}