package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import jakarta.persistence.*

@Entity
@Table(name = "\"order\"")  // The table name is a reserved keyword, so we use quotes around it
data class OrderEntity(

    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val number: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    val customer: CustomerEntity? = null,

    @ManyToMany
    @JoinTable(
        name = "order_product",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: Set<ProductEntity> = HashSet(),


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
): AbstractEntity() {

    fun toModel(): Order = Order(
        id = this.id,
        number = this.number,
        customer = this.customer?.toModel(),
        paymentStatus = this.paymentStatus
    )
}