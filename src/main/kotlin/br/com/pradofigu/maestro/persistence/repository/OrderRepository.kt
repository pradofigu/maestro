package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.usecase.order.model.*
import br.com.pradofigu.maestro.usecase.model.ProductPreparation
import br.com.pradofigu.maestro.flyway.Tables.*
import br.com.pradofigu.maestro.flyway.tables.records.OrderProductRecord
import br.com.pradofigu.maestro.flyway.tables.records.OrderRecord
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.persistence.config.JooqRepository
import br.com.pradofigu.maestro.usecase.model.*
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class OrderRepository(
    private val context: DSLContext
) : JooqRepository<OrderRecord> {

    @Transactional
    fun save(order: CreateOrder): PendingPaymentOrder {
        val orderRecord = OrderRecord()
            .setCustomerId(order.customerId)
            .setPaymentStatus(order.paymentStatus.name)

        val orderSaved = context.insertInto(ORDER)
            .set(orderRecord)
            .returning()
            .fetchOne{record -> PendingPaymentOrder(
                id = record.id,
                number = record.number,
            )
            }

        order.productsId
            .map { productId -> OrderProductRecord().setOrderId(orderSaved?.id).setProductId(productId) }
            .map { record -> context.insertInto(ORDER_PRODUCT).set(record).execute() }

       return orderSaved ?: throw DatabaseOperationException("Error on save order", order)
    }

    @Transactional
    fun update(orderPayment: OrderPayment): Order {
        val orderPaymentRecord = context
            .update(ORDER)
            .set(ORDER.PAYMENT_STATUS, orderPayment.status.name)
            .where(ORDER.ID.eq(orderPayment.id).and(ORDER.NUMBER.eq(orderPayment.number)))
            .returning()
            .fetchOne(this::toModel)
            ?: throw DatabaseOperationException("Error to update order", orderPayment)

        when (orderPaymentRecord.paymentStatus) {
            PaymentStatus.PAID -> {
                context.insertInto(ORDER_TRACKING)
                    .set(ORDER_TRACKING.ORDER_ID, orderPaymentRecord.id)
                    .set(ORDER_TRACKING.STATUS, OrderStatus.RECEIVED.name)
                    .execute()
            }
            else -> {
                context.insertInto(ORDER_TRACKING)
                    .set(ORDER_TRACKING.ORDER_ID, orderPaymentRecord.id)
                    .set(ORDER_TRACKING.STATUS, OrderStatus.FINISHED.name)
                    .execute()
            }
        }

        return orderPaymentRecord
    }

    fun findByNumber(number: Long): Order? = context
        .selectFrom(ORDER)
        .where(ORDER.NUMBER.eq(number))
        .fetchOne(this::toModel)

    fun findTrackingDetails(): List<OrderTracking> {
        val orderTrackingRecord = context.select(
            ORDER.ID,
            ORDER_TRACKING.ID,
            ORDER_TRACKING.STATUS,
            ORDER.NUMBER,
            PRODUCT.ID,
            PRODUCT.PREPARATION_TIME,
            ORDER_TRACKING.CREATED_AT
        )
            .from(ORDER_TRACKING)
            .join(ORDER)
            .on(ORDER_TRACKING.ORDER_ID.eq(ORDER.ID))
            .join(ORDER_PRODUCT)
            .on(ORDER_PRODUCT.ORDER_ID.eq(ORDER.ID))
            .join(PRODUCT)
            .on(ORDER_PRODUCT.PRODUCT_ID.eq(PRODUCT.ID))
            .where(ORDER_TRACKING.STATUS.notEqual(OrderStatus.FINISHED.name))
            .groupBy(
                ORDER.ID,
                ORDER_TRACKING.ID,
                ORDER_TRACKING.STATUS,
                ORDER.NUMBER,
                PRODUCT.ID,
                PRODUCT.PREPARATION_TIME,
                ORDER_TRACKING.CREATED_AT
            )
            .having(ORDER_TRACKING.CREATED_AT.eq(
                context.select(DSL.max(ORDER_TRACKING.CREATED_AT))
                    .from(ORDER_TRACKING)
                    .where(ORDER_TRACKING.ORDER_ID.eq(ORDER.ID))
            ))
            .orderBy(ORDER_TRACKING.CREATED_AT.desc())
            .fetch()

        val products = orderTrackingRecord.map { record ->
            ProductPreparation(
                id = record.get(PRODUCT.ID),
                preparationTime = record.get(PRODUCT.PREPARATION_TIME)
            )
        }

        return orderTrackingRecord.map { record ->
            OrderTracking(
                id = record.get(ORDER_TRACKING.ID),
                orderId = record.get(ORDER.ID),
                status = OrderStatus.valueOf(record.get(ORDER_TRACKING.STATUS)),
                createdAt = record.get(ORDER_TRACKING.CREATED_AT)
            ).apply {
                this.orderNumber = record.get(ORDER.NUMBER).toLong()
                this.products = products
            }
        }
    }

    fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking = context
        .update(ORDER_TRACKING)
        .set(ORDER_TRACKING.STATUS, orderStatus.name)
        .where(ORDER_TRACKING.ORDER_ID.eq(UUID.fromString(id)))
        .returning()
        .fetchOne()?.let { record ->
            OrderTracking(
                id = record.get(ORDER_TRACKING.ID),
                orderId = record.get(ORDER_TRACKING.ORDER_ID),
                status = OrderStatus.valueOf(record.get(ORDER_TRACKING.STATUS)),
                createdAt = record.get(ORDER_TRACKING.CREATED_AT)
            )
        } ?: throw DatabaseOperationException("Error to update order tracking for orderId $id")

    private fun toModel(record: OrderRecord): Order = Order(
        id = record.id,
        number = record.number.toLong(),
        customerId = record.customerId,
        paymentStatus = PaymentStatus.valueOf(record.paymentStatus)
    )
}