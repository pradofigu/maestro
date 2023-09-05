package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import br.com.pradofigu.maestro.usecase.model.Product
import java.util.UUID
import kotlin.random.Random

object OrderFactory {

    fun create(
        products: List<Product> = ProductFactory.create(batchSize = 5),
        paymentStatus: PaymentStatus = PaymentStatus.PENDING
    ) = Order(
        id = UUID.randomUUID(),
        number = Random.nextLong(1, 9999),
        customer = CustomerFactory.create(),
        products = products,
        paymentStatus = paymentStatus
    )
}