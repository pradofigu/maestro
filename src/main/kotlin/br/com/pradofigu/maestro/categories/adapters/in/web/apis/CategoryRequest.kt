package br.com.pradofigu.maestro.categories.adapters.`in`.web.apis

import br.com.pradofigu.maestro.categories.domain.Category.CreateCategory
import br.com.pradofigu.maestro.categories.domain.Category.UpdateCategory

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
