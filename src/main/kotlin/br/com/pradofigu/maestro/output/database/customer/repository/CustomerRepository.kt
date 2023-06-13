package br.com.pradofigu.maestro.output.database.customer.repository

import br.com.pradofigu.maestro.output.database.customer.entity.CustomerEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface CustomerRepository : ReactiveCrudRepository<CustomerEntity, UUID> {
    @Query("SELECT * FROM customers WHERE cpf = :cpf", nativeQuery = true)
    fun findByCpf(cpf: String): Mono<CustomerEntity>
}