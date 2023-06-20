package br.com.pradofigu.maestro.resources.customers

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

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CustomerResourceIntegrationTest(
    @Autowired val customerResource: CustomerResource,
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @Nested
    @TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class HappyPath {

        private var customerId: String? = null

        @Test
        @Order(1)
        @Throws(Exception::class)
        fun save_whenCreateCustomer_returns201() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    "John Smith",
                    "321.456.789-10",
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
                .andExpect(jsonPath("cpf").value("321.456.789-10"))
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
        fun get_whenGetCustomer_returns200() {
            val mvcResult = mvc.perform(get("/customers/$customerId")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            val response = mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customerId))
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value("321.456.789-10"))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
        }

        @Test
        @Order(3)
        @Throws(java.lang.Exception::class)
        fun update_whenUpdateCustomer_returns200() {
            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    "John Smith",
                    "321.456.789-10",
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
                .andExpect(jsonPath("cpf").value("321.456.789-10"))
                .andExpect(jsonPath("email").value("john.smith@example-altera-email.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()
        }

        @Test
        @Order(4)
        @Throws(java.lang.Exception::class)
        fun delete_whenDeleteCustomer_returns204() {
            val mvcResult = mvc.perform(delete("/customers/${customerId}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }


    }

}