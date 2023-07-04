package br.com.pradofigu.maestro.output.persistence.category.repository

import br.com.pradofigu.maestro.domain.category.model.Category
import br.com.pradofigu.maestro.output.persistence.JooqRepository
import br.com.pradofigu.maestro.flyway.tables.Category.CATEGORY
import br.com.pradofigu.maestro.flyway.tables.records.CategoryRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CategoryRepository(
    private val context: DSLContext
): JooqRepository<CategoryRecord> {

    fun save(category: Category): Category? = context
        .insertInto(CATEGORY)
        .set(CATEGORY.NAME, category.name)
        .returning()
        .fetchOne(this::toModel)

    fun findBy(id: UUID): Category? = context
        .selectFrom(CATEGORY)
        .where(CATEGORY.ID.eq(id))
        .fetchOne(this::toModel)

    fun update(category: Category): Category? = context
        .selectFrom(CATEGORY)
        .where(CATEGORY.ID.eq(category.id))
        .fetchOne()
        ?.setName(category.name)
        ?.let(this::optimizeColumnsUpdateOf)
        ?.let {
            context
                .update(CATEGORY)
                .set(it)
                .where(CATEGORY.ID.eq(category.id))
                .returning()
                .fetchOne(this::toModel)
        }

    fun delete(id: UUID): Boolean = context
        .delete(CATEGORY)
        .where(CATEGORY.ID.eq(id))
        .execute().let { it == 1 }

    private fun toModel(record: CategoryRecord): Category = Category(
        id = record.id,
        name = record.name
    )
}