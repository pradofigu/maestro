package br.com.pradofigu.maestro.customers.adapters.`in`.web.apis

import br.com.pradofigu.maestro.customers.application.ports.`in`.RegisterCustomerInPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class RegisterCustomerResource(@Autowired private val service: RegisterCustomerInPort) {

    @PostMapping
    suspend fun register(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = service.register(request.toCreateCustomer())
        return ResponseEntity(CustomerResponse.from(customer), CREATED)
    }

}