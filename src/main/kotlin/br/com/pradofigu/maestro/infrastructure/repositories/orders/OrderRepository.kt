package br.com.pradofigu.maestro.infrastructure.repositories.orders

import br.com.pradofigu.maestro.infrastructure.entities.maestro.tables.Order.ORDER
import br.com.pradofigu.maestro.infrastructure.entities.maestro.tables.records.OrderRecord
import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import br.com.pradofigu.maestro.domain.orders.OrderStatus
import br.com.pradofigu.maestro.domain.orders.Orders
import br.com.pradofigu.maestro.domain.orders.PaymentStatus
import br.com.pradofigu.maestro.infrastructure.repositories.JooqRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class OrderRepository(@Autowired private val context: DSLContext) : Orders, JooqRepository<OrderRecord> {

    @Transactional
    override fun save(order: CreateOrder): Order? {
        val record = OrderRecord()
                .setCustomerId(order.customerId)
                .setProducts(order.products)
                .setStatus(order.status)
                .setPaymentStatus(order.paymentStatus)

        return context.insertInto(ORDER).set(record)
                .returning()
                .fetchOne(this::toOrder)
    }

    override fun findAll(): List<Order> {
        return context.selectFrom(ORDER).fetchOne(this::toOrder)
    }

    override fun findBy(id: UUID): Order? {
        return context.selectFrom(ORDER).where(ORDER.ID.eq(id))
                .fetchOne(this::toOrder)
    }

    override fun findBy(number: Long): Order? {
        return context.selectFrom(ORDER).where(ORDER.NUMBER.eq(number))
                .fetchOne(this::toOrder)
    }

    @Transactional
    override fun update(id: UUID, paymentStatus: PaymentStatus): Order {
        return context.selectFrom(ORDER).where(ORDER.ID.eq(id)).fetchOne()
                ?.let { record ->
                    record.setPaymentStatus(paymentStatus)
                }
                ?.let(this::optimizeColumnsUpdateOf)
                ?.let { record ->
                    context.update(ORDER).set(record).where(ORDER.ID.eq(id))
                            .returning()
                            .fetchOne(this::toOrder)
                }
    }

    @Transactional
    override fun delete(id: UUID): Boolean {
        val result = context.delete(ORDER).where(ORDER.ID.eq(id)).execute()
        return 1 == result;
    }

    private fun toOrder(record: OrderRecord): Order {
        return Order(
                id = record.id,
                number = record.number,
                customer = record.customer,
                products = record.products,
                paymentStatus = record.paymentStatus
        )
    }

}