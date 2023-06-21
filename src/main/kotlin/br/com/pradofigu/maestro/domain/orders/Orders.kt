package br.com.pradofigu.maestro.domain.orders

import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import br.com.pradofigu.maestro.domain.orders.Order.UpdateStatus
import java.util.*

interface Orders {
    fun save(order: CreateOrder): Order?
    fun findBy(id: UUID): Order?
    fun update(id: UUID, order: UpdateStatus): Order?
    fun delete(id: UUID): Boolean
    fun findBy(orderNumber: Long): Order?
}
