package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.persistence.entity.OrderEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository: CrudRepository<OrderEntity, UUID> {

    fun findByNumber(number: Long): OrderEntity?
}