package br.com.pradofigu.maestro.input.restapi.category.dto

import br.com.pradofigu.maestro.domain.category.model.Category.CreateCategory
import br.com.pradofigu.maestro.domain.category.model.Category.UpdateCategory

data class CategoryRequest(
    val name: String,
) {

    fun toCreateCategory(): CreateCategory {
       return CreateCategory(
           name = this.name,
       )
    }

    fun toUpdateCategory(): UpdateCategory {
        return UpdateCategory(
            name = this.name,
        )
    }

}
