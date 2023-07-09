package br.com.pradofigu.maestro.output.persistence.order.repository

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.flyway.Tables.ORDER
import br.com.pradofigu.maestro.flyway.tables.records.OrderRecord
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class OrderRepository(
    private val context: DSLContext
): JooqRepository<OrderRecord> {

    fun save(order: Order): Order = OrderRecord()
        .setId(order.id ?: UUID.randomUUID())
        .setNumber(order.number.toInt())
        .setCustomerId(order.customerId)
        .setProducts(order.products)
        .setPaymentStatus(order.paymentStatus.name)
        .let {
            context
                .insertInto(ORDER)
                .set(it)
                .returning()
                .fetchOne(this::toModel)
        } ?: throw DatabaseOperationException("Error on save order", order)

    fun findAll(): List<Order> = context
        .selectFrom(ORDER)
        .fetch(this::toModel)

    fun findBy(id: UUID): Order? = context
        .selectFrom(ORDER)
        .where(ORDER.ID.eq(id))
        .fetchOne(this::toModel)

    fun findBy(number: Long): Order? = context
        .selectFrom(ORDER)
        .where(ORDER.NUMBER.eq(number.toInt()))
        .fetchOne(this::toModel)

    @Transactional
    fun update(id: UUID, paymentStatus: PaymentStatus): Order? = context
        .selectFrom(ORDER)
        .where(ORDER.ID.eq(id))
        .fetchOne()
        ?.setPaymentStatus(paymentStatus.name)
        ?.let(this::optimizeColumnsUpdateOf)
        ?.let {
            context
                .update(ORDER)
                .set(it)
                .where(ORDER.ID.eq(id))
                .returning()
                .fetchOne(this::toModel)
        }

    fun delete(id: UUID): Boolean = context
        .delete(ORDER)
        .where(ORDER.ID.eq(id))
        .execute()
        .let { it == 1 }

    private fun toModel(record: OrderRecord): Order = Order(
        id = record.id,
        number = record.number.toLong(),
        customerId = record.customerId,
        products = record.products,
        paymentStatus = PaymentStatus.valueOf(record.paymentStatus)
    )
}