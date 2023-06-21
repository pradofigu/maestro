package br.com.pradofigu.maestro.customers.adapters.`in`.web.apis

import br.com.pradofigu.maestro.customers.application.ports.`in`.FindCustomerInPort
import br.com.pradofigu.maestro.customers.domain.CPF
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class FindCustomerResource(@Autowired private val findCustomers: FindCustomerInPort) {

    @GetMapping("/cpf/{cpf}")
    suspend fun findById(@PathVariable cpf: String): CustomerResponse? {
        val maybeCustomer = findCustomers.by(CPF(cpf))
        return maybeCustomer?.let { customer -> CustomerResponse.from(customer) }
    }

}