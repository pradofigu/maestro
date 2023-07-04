package br.com.pradofigu.maestro.output.persistence.product.repository

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import br.com.pradofigu.maestro.output.persistence.category.repository.CategoryRepository
import br.com.pradofigu.maestro.output.persistence.customer.repository.CustomerRepository
import br.com.pradofigu.maestro.tables.Product.PRODUCT
import br.com.pradofigu.maestro.tables.records.ProductRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class ProductRepository(
    private val context: DSLContext,
    private val categoryRepository: CategoryRepository
): JooqRepository<ProductRecord> {

    fun save(product: Product): Product? = ProductRecord()
        .setId(product.id)
        .setName(product.name)
        .setPrice(product.price)
        .setCategory(product.category.id)
        .setPreparationTime(product.preparationTime)
        .let {
            context
                .insertInto(PRODUCT)
                .set(it)
                .returning()
                .fetchOne(this::toModel)
        }

    fun findBy(id: UUID): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne(this::toModel)

    fun findByCategory(category: UUID): List<Product> = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.CATEGORY.eq(category))
        .fetch(this::toModel)

    @Transactional
    fun update(id: UUID, product: Product): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne()
        ?.apply {
            this.setName(product.name)
            this.setPrice(product.price)
            this.setCategory(product.category.id)
            this.setPreparationTime(product.preparationTime)
        }
        ?.let(this::optimizeColumnsUpdateOf)
        ?.let {
            context
                .update(PRODUCT)
                .set(it)
                .where(PRODUCT.ID.eq(id))
                .returning()
                .fetchOne(this::toModel)
        }

    fun delete(id: UUID): Boolean = context
        .delete(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .execute()
        .let { it == 1 }

    private fun toModel(record: ProductRecord): Product {
        val category = categoryRepository.findBy(record.category)
            ?: throw IllegalStateException("Category not found for id ${record.category}")

        return Product(
            id = record.id,
            name = record.name,
            price = record.price,
            category = category,
            preparationTime = record.preparationTime
        )
    }
}