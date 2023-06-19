package br.com.pradofigu.maestro.domain.customers

import java.util.*

interface Customers {

    fun findAll(): List<Customer>

    fun findById(customerId: UUID): Customer

    fun findByCpf(cpf: String): Customer

}
