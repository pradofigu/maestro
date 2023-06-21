package br.com.pradofigu.maestro.customers.application.services

import br.com.pradofigu.maestro.customers.application.ports.`in`.FindCustomerInPort
import br.com.pradofigu.maestro.customers.application.ports.out.FindCustomerOutPort
import br.com.pradofigu.maestro.customers.domain.CPF
import br.com.pradofigu.maestro.customers.domain.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FindCustomer(@Autowired private val repository: FindCustomerOutPort): FindCustomerInPort {

    override fun findBy(cpf: CPF): Customer? {
        return repository.findBy(cpf)
    }

}
