package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

data class CategoryRequest(val id: UUID? = null, val name: String) {

    fun toModel() = if (id != null) Category(id = id, name = name) else Category(name = name)
}

data class CategoryResponse(
    val id: String,
    val name: String
) {

    companion object {
        fun from(category: Category) = CategoryResponse(
            id = category.id!!.toString(),
            name = category.name
        )
    }
}