package br.com.pradofigu.maestro.factory

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import br.com.pradofigu.maestro.persistence.repository.CustomerRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CustomerFactory(private val customerRepository: CustomerRepository) {

    fun create(cpf: String = generateCpfAsString()) = customerRepository.save(
        Customer(
            name = "John Doe",
            email = "john@doe.co",
            phone = "+5511999998888",
            cpf = CPF(cpf),
            birthDate = LocalDate.of(1980, 1, 1)
        ).toEntity()
    ).toModel()

    fun findById(id: UUID) = customerRepository.findById(id).get().toModel()

    fun generateCpfAsString() = CPFValidator().generateRandomValid()!!
}