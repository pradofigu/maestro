package br.com.pradofigu.maestro.infrastructure.repositories.categories

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Category.CATEGORY
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CategoryRecord
import br.com.pradofigu.maestro.domain.categories.Category
import br.com.pradofigu.maestro.infrastructure.repositories.JooqRepository
import br.com.pradofigu.maestro.resources.categories.Categories
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CategoryRepository(@Autowired private val context: DSLContext) : Categories, JooqRepository<CategoryRecord> {

    override fun save(category: Category.CreateCategory): Category? {
        return context.insertInto(CATEGORY)
            .set(CATEGORY.NAME, category.name)
            .returning()
            .fetchOne(this::toCategory)
    }

    override fun findBy(id: UUID): Category? {
        return context.selectFrom(CATEGORY).where(CATEGORY.ID.eq(id))
            .fetchOne(this::toCategory)
    }

    override fun update(id: UUID, category: Category.UpdateCategory): Category? {
        return context.selectFrom(CATEGORY).where(CATEGORY.ID.eq(id)).fetchOne()
            ?.let { it.setName(category.name) }
            ?.let(this::optimizeColumnsUpdateOf)
            ?.let { record ->
                context.update(CATEGORY).set(record).where(CATEGORY.ID.eq(id))
                    .returning()
                    .fetchOne(this::toCategory)
            }
    }

    override fun delete(id: UUID): Boolean {
        val result = context.delete(CATEGORY).where(CATEGORY.ID.eq(id)).execute()
        return 1 == result;
    }

    private fun toCategory(record: CategoryRecord): Category {
        return Category(
            id = record.id,
            name = record.name
        )
    }
}