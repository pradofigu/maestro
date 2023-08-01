package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.domain.category.model.Category

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
