package br.com.pradofigu.maestro.web.customer

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.factory.CustomerFactory
import br.com.pradofigu.maestro.web.dto.CustomerRequest
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
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/customers")
class CustomerControllerIntegrationTest {

    @Autowired private lateinit var customerFactory: CustomerFactory
    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Nested
    @Transactional
    inner class HappyPathIntegrationTest {

        @Test
        @Rollback
        fun `When create a customers should returns 201`() {
            val customerCpf = customerFactory.generateCpfAsString()

            val body: String = objectMapper.writeValueAsString(
                CustomerRequest(
                    name = "John Smith",
                    cpf = customerCpf,
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

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value("John Smith"))
                .andExpect(jsonPath("cpf").value(customerCpf))
                .andExpect(jsonPath("email").value("john.smith@example.com.br"))
                .andExpect(jsonPath("phone").value("(11)98555-4321"))
                .andExpect(jsonPath("birthDate").value("1980-09-03"))
                .andReturn()
        }

        @Test
        @Rollback
        fun `When get a customer by id should returns 200`() {
            val customer = customerFactory.create()
            val mvcResult = mvc.perform(get("/customers/${customer.id}")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer.id.toString()))
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("cpf").value(customer.cpf.number))
                .andExpect(jsonPath("email").value("john@doe.co"))
                .andExpect(jsonPath("phone").value("+5511999998888"))
                .andExpect(jsonPath("birthDate").value("1980-01-01"))
        }

        @Test
        @Rollback
        fun `When get a customer by cpf should returns 200`() {
            val customerCpf = customerFactory.generateCpfAsString()
            val customer = customerFactory.create(cpf = customerCpf)
            customerFactory.create()

            val mvcResult = mvc.perform(get("/customers/cpf/${customerCpf}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer.id.toString()))
                .andExpect(jsonPath("cpf").value(customerCpf))
        }

        @Test
        @Rollback
        fun `When update a customer should returns 200`() {
            val customer = customerFactory.create()

            val body: String = objectMapper.writeValueAsString(
                customer.toRequest().copy(
                    name = "John Smith",
                    cpf = customerFactory.generateCpfAsString()
                )
            )

            val mvcResult = mvc.perform(
                put("/customers/${customer.id}")
                    .contentType(APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer.id.toString()))
                .andExpect(jsonPath("name").value("John Smith"))
        }

        @Test
        @Rollback
        fun `When delete a customer should returns 200`() {
            val customer = customerFactory.create()

            val mvcResult = mvc.perform(delete("/customers/${customer.id}"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent())
        }
    }

    /**
     * It's necessary in order to send only numbers to CPF field, otherwise we could
     * generate something like CPF(number=12312312345)
     * */
    private fun Customer.toRequest() = CustomerRequest(
        name = name,
        email = email,
        phone = phone,
        cpf = cpf.number,
        birthDate = birthDate
    )
}