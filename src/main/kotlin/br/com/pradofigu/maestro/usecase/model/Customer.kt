package br.com.pradofigu.maestro.usecase.model

import br.com.caelum.stella.validation.CPFValidator
import java.time.LocalDate
import java.util.UUID

data class Customer(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val email: String,
    val phone: String,
    val cpf: CPF,
    val birthDate: LocalDate
)

data class CPF(val number: String) {

    init {
        CPFValidator().assertValid(number)
    }
}
