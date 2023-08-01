package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.flyway.Tables.PRODUCT
import br.com.pradofigu.maestro.flyway.tables.records.ProductRecord
import br.com.pradofigu.maestro.persistence.config.JooqRepository
import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.usecase.model.Product
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProductRepository(
    private val context: DSLContext,
    private val categoryRepository: CategoryRepository
): JooqRepository<ProductRecord> {

    fun save(product: Product): Product = ProductRecord()
        .setId(product.id)
        .setName(product.name)
        .setDescription(product.description)
        .setPrice(product.price)
        .setCategoryId(product.category.id)
        .setImageUrl(product.imageUrl)
        .setPreparationTime(product.preparationTime)
        .let {
            context
                .insertInto(PRODUCT)
                .set(it)
                .returning()
                .fetchOne(this::toModel)
        } ?: throw DatabaseOperationException("Error on save product", product)

    fun findAll(): List<Product> = context
        .selectFrom(PRODUCT)
        .fetch(this::toModel)

    fun findBy(id: UUID): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne(this::toModel)

    fun findByCategory(categoryId: UUID): List<Product> = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.CATEGORY_ID.eq(categoryId))
        .fetch(this::toModel)

    @Transactional
    fun update(id: UUID, product: Product): Product? = context
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .fetchOne()
        ?.apply {
            this.setName(product.name)
            this.setPrice(product.price)
            this.setCategoryId(product.category.id)
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
        val category = categoryRepository.findBy(record.categoryId)
            ?: throw IllegalStateException("Category not found for id ${record.categoryId}")

        return Product(
            id = record.id,
            name = record.name,
            price = record.price,
            category = category,
            description = record.description,
            imageUrl = record.imageUrl,
            preparationTime = record.preparationTime
        )
    }
}