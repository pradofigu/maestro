package br.com.pradofigu.maestro.input.restapi.customer

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.Month

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CustomerControllerIntegrationTest(
    private val mvc: MockMvc,
    private val objectMapper: ObjectMapper
) {

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPathIntegrationTest {
        private val CPF = CPFValidator().generateRandomValid()
        private var customerId: String? = null

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun `When create a customers should returns 201`() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    "John Smith",
                    CPF,
                    "john.smith@example.com.br",
                    "(11)98555-4321",
                    LocalDate.of(1980, Month.SEPTEMBER, 3)
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
                .andExpect(jsonPath("cpf").value(CPF))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()

            this.customerId =
                objectMapper.readValue(response.response.contentAsString, CustomerResponse::class.java).id
            assertNotNull(customerId, "Created test didn't return the customer id")
        }

        @Test
        @Order(2)
        @Throws(java.lang.Exception::class)
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
                .andExpect(jsonPath("cpf").value(CPF))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
        }

        @Test
        @Order(3)
        @Throws(java.lang.Exception::class)
        fun `When get a customer by cpf should returns 200`() {
            val mvcResult = mvc.perform(get("/customers/cpf/${CPF}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(CPF))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
        }

        @Test
        @Order(4)
        @Throws(java.lang.Exception::class)
        fun `When update a customer should returns 200`() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    "John Smith",
                    CPF,
                    "john.smith@example-altera-email.com.br",
                    "(11)98555-4321",
                    LocalDate.of(1980, Month.SEPTEMBER, 3)
                )
            )
            val mvcResult = mvc.perform(
                put("/customers/${customerId}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(CPF))
                .andExpect(jsonPath("email").value("john.smith@example-altera-email.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()
        }

        @Test
        @Order(5)
        @Throws(java.lang.Exception::class)
        fun `When delete a customer should returns 204`() {
            val mvcResult = mvc.perform(delete("/customers/${customerId}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }
}