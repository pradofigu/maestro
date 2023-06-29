package br.com.pradofigu.maestro.output.persistence.product.repository

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Product.PRODUCT
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.ProductRecord
import br.com.pradofigu.maestro.domain.product.model.Product.CreateProduct
import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.domain.product.ports.output.ProductDataAccessPort
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ProductRepository(@Autowired private val context: DSLContext): JooqRepository<ProductRecord> {

    @Transactional
    fun save(product: CreateProduct): Product? {
        val record = ProductRecord()
            .setName(product.name)
            .setPrice(product.price)
            .setCategory(product.category)
            .setPreparationTime(product.preparationTime)

        return context.insertInto(PRODUCT).set(record)
            .returning()
            .fetchOne(this::toProduct)
    }

    fun findBy(id: UUID): Product? {
        return context.selectFrom(PRODUCT).where(PRODUCT.ID.eq(id))
            .fetchOne(this::toProduct)
    }

    fun findBy(category: String): List<Product> {
        return context.selectFrom(PRODUCT).where(PRODUCT.CATEGORY.eq(category))
            .fetch(this::toProduct)
    }

    @Transactional
    fun update(id: UUID, product: Product.UpdateProduct): Product? {
        return context.selectFrom(PRODUCT).where(PRODUCT.ID.eq(id)).fetchOne()
            ?.let { record ->

                record.setName(product.name)
                    .setName(product.name)
                    .setPrice(product.price)
                    .setCategory(product.category)
                    .setPreparationTime(product.preparationTime)
            }
            ?.let(this::optimizeColumnsUpdateOf)
            ?.let { record ->
                context.update(PRODUCT).set(record).where(PRODUCT.ID.eq(id))
                    .returning()
                    .fetchOne(this::toProduct)
            }
    }

    @Transactional
    fun delete(id: UUID): Boolean {
        val result = context.delete(PRODUCT).where(PRODUCT.ID.eq(id)).execute()
        return 1 == result;
    }

    private fun toProduct(record: ProductRecord): Product {
        return Product(
            id = record.id,
            name = record.name,
            price = record.price,
            category = record.category,
            preparationTime = record.preparationTime
        )
    }

}