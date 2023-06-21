package br.com.pradofigu.maestro.customers.application.ports.out

import br.com.pradofigu.maestro.customers.domain.Customer

interface RegisterCustomerOutPort {

    fun save(customer: Customer.CreateCustomer): Customer?

}