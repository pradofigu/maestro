package br.com.pradofigu.maestro.customers.application.ports.`in`

import br.com.pradofigu.maestro.customers.domain.Customer
import br.com.pradofigu.maestro.customers.domain.Customer.CreateCustomer

interface RegisterCustomerInPort {

    fun register(customer: CreateCustomer): Customer

}