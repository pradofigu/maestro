package br.com.pradofigu.maestro.input.restapi.category.dto

import br.com.pradofigu.maestro.domain.category.model.Category

class CategoryResponse(
    val id: String,
    val name: String
) {

    companion object {
        fun from(category: Category): CategoryResponse {
            return CategoryResponse(
                id = category.id.toString(),
                name = category.name
            )
        }
    }

}
