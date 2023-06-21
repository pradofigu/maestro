package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.domain.Category

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
