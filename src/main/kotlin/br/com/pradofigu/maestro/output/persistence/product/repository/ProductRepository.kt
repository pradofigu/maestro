package br.com.pradofigu.maestro.output.persistence.product.repository

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import br.com.pradofigu.maestro.tables.Product.PRODUCT
import br.com.pradofigu.maestro.tables.records.ProductRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class ProductRepository(
    private val context: DSLContext
): JooqRepository<ProductRecord> {

    fun save(product: Product): Product? = ProductRecord()
        .setId(product.id)
        .setName(product.name)
        .setPrice(product.price)
        .setCategory(product.category)
        .setPreparationTime(product.preparationTime)
        .let {
            context
                .insertInto(PRODUCT)
                .set(it)
                .returning()
                .fetchOne(this::toProduct)
        }

    fun findBy(id: UUID): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne(this::toProduct)

    fun findBy(category: String): List<Product> = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.CATEGORY.eq(category))
        .fetch(this::toProduct)

    @Transactional
    fun update(id: UUID, product: Product): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne()
        ?.apply {
            this.setName(product.name)
            this.setPrice(product.price)
            this.setCategory(product.category)
            this.setPreparationTime(product.preparationTime)
        }
        ?.let(this::optimizeColumnsUpdateOf)
        ?.let {
            context
                .update(PRODUCT)
                .set(it)
                .where(PRODUCT.ID.eq(id))
                .returning()
                .fetchOne(this::toProduct)
        }

    fun delete(id: UUID): Boolean = context
        .delete(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .execute()
        .let { it == 1 }

    private fun toProduct(record: ProductRecord): Product = Product(
        id = record.id,
        name = record.name,
        price = record.price,
        category = record.category,
        preparationTime = record.preparationTime
    )
}