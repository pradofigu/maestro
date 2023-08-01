package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.flyway.Tables.CATEGORY
import br.com.pradofigu.maestro.flyway.tables.records.CategoryRecord
import br.com.pradofigu.maestro.persistence.config.JooqRepository
import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.usecase.model.Category
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CategoryRepository(
    private val context: DSLContext
): JooqRepository<CategoryRecord> {

    fun save(category: Category): Category {
      return context
          .insertInto(CATEGORY)
          .columns(CATEGORY.ID, CATEGORY.NAME)
          .values(category.id, category.name)
          .returning()
          .fetchOne(this::toModel) ?: throw DatabaseOperationException("Error on save category", category)
    }

    fun findAll(): List<Category> = context
        .selectFrom(CATEGORY)
        .fetch(this::toModel)

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