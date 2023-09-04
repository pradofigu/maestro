package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.persistence.repository.OrderRepository
import br.com.pradofigu.maestro.usecase.model.Category
import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import br.com.pradofigu.maestro.usecase.model.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@Component
class OrderFactory(
    private val customerFactory: CustomerFactory,
    private val productFactory: ProductFactory,
    private val orderRepository: OrderRepository

) {

    fun create(
        customerId: UUID? = null,
        products: List<Product> = generateProducts(),
        paymentStatus: PaymentStatus = PaymentStatus.PENDING
    ): Order {
        val customer = if (customerId == null) {
            customerFactory.create()
        } else {
            customerFactory.findById(customerId)
        }

        return orderRepository.save(
            Order(
                customer = customer,
                products = productFactory.create(products)
            ).toEntity()
        ).toModel()
    }

    private fun generateProducts(): List<Product> = listOf(
        Product(
            name = "X-Egg",
            description = "Lorem Ipsum",
            imageUrl = "https://my-image.com",
            price = BigDecimal(29.90),
            category = Category(name = "Lanche ${Random.nextInt(1, 9999)}"),
            preparationTime = 30
        )
    )
}