package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.Order

interface OrderDataAccessPort {

    suspend fun findByNumber(number: Long): Order?

    suspend fun save(order: Order): Order
}
