package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import br.com.pradofigu.maestro.usecase.service.CustomerService
import br.com.pradofigu.maestro.web.dto.CustomerRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@SpringBootTest(classes = [CustomerController::class])
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
    }

    @Test
    suspend fun testRegisterCustomer() {
        val customerRequest = CustomerRequest(
                name = "Test Customer",
                cpf = "12345678901",
                email = "test@example.com",
                phone = "123-456-7890",
                birthDate = LocalDate.of(1990, 1, 1)
        )

        val customer = Customer(
                name = customerRequest.name,
                cpf = CPF(customerRequest.cpf),
                email = customerRequest.email,
                phone = customerRequest.phone,
                birthDate = customerRequest.birthDate
        )

        val savedCustomer = customerService.register(customer)

        mockMvc.perform(
                post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(savedCustomer.toJson())
        )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.phone").value("123-456-7890"))
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"))
    }

    @Test
    suspend fun testFindAllCustomers() {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Customer 1"))
                .andExpect(jsonPath("$[0].cpf").value("11111111111"))
                .andExpect(jsonPath("$[0].email").value("customer1@example.com"))
                .andExpect(jsonPath("$[0].phone").value("111-111-1111"))
                .andExpect(jsonPath("$[0].birthDate").value("1980-02-02"))
                .andExpect(jsonPath("$[1].name").value("Customer 2"))
                .andExpect(jsonPath("$[1].cpf").value("22222222222"))
                .andExpect(jsonPath("$[1].email").value("customer2@example.com"))
                .andExpect(jsonPath("$[1].phone").value("222-222-2222"))
                .andExpect(jsonPath("$[1].birthDate").value("1975-03-03"))
    }

    @Test
    suspend fun testFindCustomerById() {
        val customer = customerService.register(Customer(
                name = "Test Customer",
                cpf = CPF("33333333333"),
                email = "test@example.com",
                phone = "333-333-3333",
                birthDate = LocalDate.of(1995, 4, 4)
        ))

        mockMvc.perform(get("/customers/${customer.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.cpf").value("33333333333"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.phone").value("333-333-3333"))
                .andExpect(jsonPath("$.birthDate").value("1995-04-04"))
    }

    @Test
    suspend fun testUpdateCustomer() {
        val customer = customerService.register(Customer(
                name = "Original Customer",
                cpf = CPF("44444444444"),
                email = "original@example.com",
                phone = "444-444-4444",
                birthDate = LocalDate.of(1985, 5, 5)
        ))

        val updatedCustomerRequest = CustomerRequest(
                name = "Updated Customer",
                cpf = "55555555555",
                email = "updated@example.com",
                phone = "555-555-5555",
                birthDate = LocalDate.of(1975, 6, 6)
        )

        mockMvc.perform(
                put("/customers/${customer.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCustomerRequest.toJson())
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Updated Customer"))
                .andExpect(jsonPath("$.cpf").value("55555555555"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.phone").value("555-555-5555"))
                .andExpect(jsonPath("$.birthDate").value("1975-06-06"))
    }

    @Test
    suspend fun testDeleteCustomer() {
        val customer = customerService.register(Customer(
                name = "Test Customer",
                cpf = CPF("66666666666"),
                email = "test@example.com",
                phone = "666-666-6666",
                birthDate = LocalDate.of(1999, 7, 7)
        ))

        mockMvc.perform(delete("/customers/${customer.id}"))
                .andExpect(status().isNoContent)
    }

    @Test
    suspend fun testFindCustomerByCPF() {
        val customer = customerService.register(Customer(
                name = "Test Customer",
                cpf = CPF("77777777777"),
                email = "test@example.com",
                phone = "777-777-7777",
                birthDate = LocalDate.of(2000, 8, 8)
        ))

        mockMvc.perform(get("/customers/cpf/${customer.cpf}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Test Customer"))
                .andExpect(jsonPath("$.cpf").value("77777777777"))
    }

    private fun Any.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}
