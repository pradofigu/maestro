package br.com.pradofigu.maestro.input.http.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.Address
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class CustomerDTO(
    val id: UUID,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate,
    val address: AddressDTO,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class AddressDTO(
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val country: String,
    val zipCode: String
)

fun Customer.toDTO() = CustomerDTO(
    id = id,
    name = name,
    cpf = cpf,
    email = email,
    phone = phone,
    birthDate = birthDate,
    address = address.toDTO(),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Address.toDTO() = AddressDTO(
    street = street,
    number = number,
    complement = complement,
    neighborhood = neighborhood,
    city = city,
    state = state,
    country = country,
    zipCode = zipCode
)