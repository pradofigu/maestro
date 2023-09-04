package br.com.pradofigu.maestro.usecase.model

import br.com.pradofigu.maestro.persistence.entity.CategoryEntity
import java.util.UUID

data class Category(
    val id: UUID? = null,
    val name: String,
) {

    fun toEntity(): CategoryEntity = CategoryEntity(name = this.name).apply {
        this.id = this@Category.id
    }
}
