package br.com.pradofigu.maestro.usecase.model

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.persistence.entity.CustomerEntity
import java.time.LocalDate
import java.util.UUID

data class Customer(
    val id: UUID? = UUID.randomUUID(),
    val name: String?,
    val email: String?,
    val phone: String?,
    val cpf: CPF?,
    val birthDate: LocalDate?
) {

    fun toEntity(): CustomerEntity = CustomerEntity(
        name = this.name,
        email = this.email,
        phone = this.phone,
        cpf = this.cpf?.number,
        birthDate = this.birthDate
    ).apply { this.id = this@Customer.id }
}

data class CPF(val number: String) {

    init {
        CPFValidator().assertValid(number)
    }
}
