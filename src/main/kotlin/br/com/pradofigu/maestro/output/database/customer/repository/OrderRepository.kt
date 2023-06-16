package br.com.pradofigu.maestro.output.database.customer.repository

import br.com.pradofigu.maestro.output.database.customer.entity.CustomerEntity
import br.com.pradofigu.maestro.output.database.customer.entity.OrderEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface OrderRepository : ReactiveCrudRepository<OrderEntity, UUID> {
    @Query("SELECT * FROM orders WHERE orderNumber = :orderNumber", nativeQuery = true)
    fun findByOrderNumber(orderNumber: Number): Mono<OrderEntity>
}