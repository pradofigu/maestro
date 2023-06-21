package br.com.pradofigu.maestro.resources.categories

import br.com.pradofigu.maestro.domain.categories.Category

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
