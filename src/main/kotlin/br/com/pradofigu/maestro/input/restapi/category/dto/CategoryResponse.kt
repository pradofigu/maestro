package br.com.pradofigu.maestro.input.restapi.category.dto

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID

class CategoryResponse(
    val id: UUID,
    val name: String
) {

    companion object {
        fun from(category: Category) = CategoryResponse(
            id = category.id!!,
            name = category.name
        )
    }

}
