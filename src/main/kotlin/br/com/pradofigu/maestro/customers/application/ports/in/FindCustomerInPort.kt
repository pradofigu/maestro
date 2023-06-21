package br.com.pradofigu.maestro.customers.application.ports.`in`

import br.com.pradofigu.maestro.customers.domain.CPF
import br.com.pradofigu.maestro.customers.domain.Customer

interface FindCustomerInPort {

    fun by(id: CPF): Customer?

}