package br.com.pradofigu.maestro.customers.application.ports.out

import br.com.pradofigu.maestro.customers.domain.CPF
import br.com.pradofigu.maestro.customers.domain.Customer

interface FindCustomerOutPort {

    fun findBy(cpf: CPF): Customer?

}