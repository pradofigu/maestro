package br.com.pradofigu.maestro.output.database.customer.repository

import br.com.pradofigu.maestro.output.database.customer.entity.ProductEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface ProductRepository : ReactiveCrudRepository<ProductEntity, UUID> {
    fun findByCategory(category: String): Flux<ProductEntity>
}