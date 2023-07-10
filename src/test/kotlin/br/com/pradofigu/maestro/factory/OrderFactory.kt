package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.domain.order.model.CreateOrder
import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.order.model.PendingPaymentOrder
import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random

@Component
class OrderFactory(
    private val customerFactory: CustomerFactory,
    private val orderRepository: OrderRepository
) {

//    fun create(
//        customerId: UUID? = null,
//        products: List<Product> = listOf(Product(
//            name = "X-Egg",
//            price = BigDecimal(29.90),
//            category = Category(name = "Lanche"),
//            preparationTime = BigDecimal(30)
//        )),
//        paymentStatus: PaymentStatus = PaymentStatus.PENDING
//    ): PendingPaymentOrder {
//        val customer = if (customerId == null) {
//            customerFactory.create()
//        } else {
//            customerFactory.findById(customerId)!!
//        }
//
//        return orderRepository.save(
//            CreateOrder(
//                customerId = customer.id,
//                paymentStatus = paymentStatus
//            )
//        )
//    }

//    fun findById(id: UUID): Order? = orderRepository.findBy(id)
}