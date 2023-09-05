package br.com.pradofigu.maestro.factory

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import java.time.LocalDate
import java.util.UUID

object CustomerFactory {

    fun create(cpf: String = generateCpfAsString()) = Customer(
        id = UUID.randomUUID(),
        name = "John Doe",
        email = "john@doe.co",
        phone = "+5511999998888",
        cpf = CPF(cpf),
        birthDate = LocalDate.of(1980, 1, 1)
    )

    private fun generateCpfAsString() = CPFValidator().generateRandomValid()!!
}