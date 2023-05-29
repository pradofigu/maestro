package br.com.pradofigu.maestro.domain.customer.usecase

import br.com.pradofigu.maestro.domain.customer.ports.input.CustomerInputPort
import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CustomerUseCase(private val customerDataAccessPort: CustomerDataAccessPort) : CustomerInputPort {

    override suspend fun findAll() = customerDataAccessPort.findAll()

    override suspend fun findById(id: UUID) = customerDataAccessPort.findById(id)
        ?: throw ResourceNotFoundException("Customer not found")

    override suspend fun findByCpf(cpf: String) = customerDataAccessPort.findByCpf(cpf)
        ?: throw ResourceNotFoundException("Customer not found")
}