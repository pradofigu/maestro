package br.com.pradofigu.maestro.output.database.customer.entity

import br.com.pradofigu.maestro.domain.customer.model.Customer
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Table("customers")
data class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    val name: String,

    val email: String,

    val phone: String,

    val cpf: String,

    val birthDate: LocalDate,

    @CreatedDate
    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toModel() = Customer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        cpf = cpf,
        birthDate = birthDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        fun fromModel(customer: Customer) = CustomerEntity(
            id = customer.id,
            name = customer.name,
            email = customer.email,
            phone = customer.phone,
            cpf = customer.cpf,
            birthDate = customer.birthDate,
            createdAt = customer.createdAt,
            updatedAt = customer.updatedAt
        )
    }
}