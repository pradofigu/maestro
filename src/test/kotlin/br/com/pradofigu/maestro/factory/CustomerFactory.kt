package br.com.pradofigu.maestro.factory

import br.com.caelum.stella.validation.CPFValidator
import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.output.persistence.customer.repository.CustomerRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
class CustomerFactory(private val customerRepository: CustomerRepository) {

        fun create() = customerRepository.save(
            Customer(
                name = "John Doe",
                email = "john@doe.co",
                phone = "+5511999998888",
                cpf = CPF(CPFValidator().generateRandomValid()),
                birthDate = LocalDate.of(1980, 1, 1)
            )
        )

        fun findById(id: UUID) = customerRepository.findBy(id)
}