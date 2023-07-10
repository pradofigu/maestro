package br.com.pradofigu.maestro.output.persistence.order.repository

import br.com.pradofigu.maestro.domain.order.model.*
import br.com.pradofigu.maestro.domain.product.model.ProductPreparation
import br.com.pradofigu.maestro.flyway.Tables.*
import br.com.pradofigu.maestro.flyway.tables.records.OrderProductRecord
import br.com.pradofigu.maestro.flyway.tables.records.OrderRecord
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import org.jooq.DSLContext
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
            )}

        order.productsId
            .map { productId -> OrderProductRecord().setOrderId(orderSaved?.id).setProductId(productId) }
            .map { record -> context.insertInto(ORDER_PRODUCT).set(record).execute() }

       return orderSaved ?: throw DatabaseOperationException("Error on save order", order)
    }

    @Transactional
    fun update(orderPayment: OrderPayment): Order {
        return context
            .update(ORDER)
            .set(ORDER.PAYMENT_STATUS, orderPayment.status.name)
            .where(ORDER.ID.eq(orderPayment.id).and(ORDER.NUMBER.eq(orderPayment.number)))
            .returning()
            .fetchOne(this::toModel)
            ?: throw DatabaseOperationException("Error to update order", orderPayment)
    }

    fun findByNumber(number: Long): Order? = context
        .selectFrom(ORDER)
        .where(ORDER.NUMBER.eq(number))
        .fetchOne(this::toModel)

    fun findTracking(orderId: String): OrderTracking? = context.select()
        .from(ORDER_TRACKING)
        .join(ORDER)
        .on(ORDER_TRACKING.ORDER_ID.eq(ORDER.ID))
        .where(ORDER_TRACKING.ORDER_ID.eq(UUID.fromString(orderId)))
        .fetchOne { record ->
            OrderTracking(
                id = record.get(ORDER_TRACKING.ID),
                orderId = record.get(ORDER_TRACKING.ORDER_ID),
                status = OrderStatus.valueOf(record.get(ORDER_TRACKING.STATUS)),
                createdAt = record.get(ORDER_TRACKING.CREATED_AT)
            ).apply {
                this.orderNumber = record.get(ORDER.NUMBER).toLong()
            }
        }

    fun findTrackingDetails(orderId: String): OrderTracking {
        val orderTrackingRecord = context.select()
            .from(ORDER_TRACKING)
            .join(ORDER)
            .on(ORDER_TRACKING.ORDER_ID.eq(ORDER.ID))
            .join(ORDER_PRODUCT)
            .on(ORDER_PRODUCT.ORDER_ID.eq(ORDER.ID))
            .join(PRODUCT)
            .on(ORDER_PRODUCT.PRODUCT_ID.eq(PRODUCT.ID))
            .where(ORDER_TRACKING.ORDER_ID.eq(UUID.fromString(orderId)))
            .fetch()

        if (orderTrackingRecord.isEmpty()) {
            throw DatabaseOperationException("Error to find tracking details for orderId $orderId")
        }

        val products = orderTrackingRecord.map { record ->
            ProductPreparation(
                id = record.get(PRODUCT.ID),
                preparationTime = record.get(PRODUCT.PREPARATION_TIME)
            )
        }

        return orderTrackingRecord.first().let { record ->
            OrderTracking(
                id = record.get(ORDER_TRACKING.ID),
                orderId = record.get(ORDER_TRACKING.ORDER_ID),
                status = OrderStatus.valueOf(record.get(ORDER_TRACKING.STATUS)),
                createdAt = record.get(ORDER_TRACKING.CREATED_AT)
            ).apply {
                this.orderNumber = record.get(ORDER.NUMBER).toLong()
                this.products = products
            }
        }
    }

    private fun toModel(record: OrderRecord): Order = Order(
        id = record.id,
        number = record.number.toLong(),
        customerId = record.customerId,
        paymentStatus = PaymentStatus.valueOf(record.paymentStatus)
    )
}