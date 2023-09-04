package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.Product
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
data class ProductEntity(

    @Column
    val name: String = "",

    @Column
    val description: String? = null,

    @Column(precision = 6, scale = 2)
    val price: BigDecimal = BigDecimal.ZERO,

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    val category: CategoryEntity? = null,

    @ManyToMany(mappedBy = "products")
    val orders: Set<OrderEntity> = HashSet(),

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @Column(name = "preparation_time")
    val preparationTime: Int = 0
): AbstractEntity() {

    fun toModel(): Product = Product(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        price = this.price,
        category = this.category!!.toModel(),
        imageUrl = this.imageUrl,
        preparationTime = this.preparationTime
    )
}