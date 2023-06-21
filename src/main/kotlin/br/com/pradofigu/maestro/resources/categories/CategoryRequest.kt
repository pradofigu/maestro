package br.com.pradofigu.maestro.resources.categories

import br.com.pradofigu.maestro.domain.categories.Category.CreateCategory
import br.com.pradofigu.maestro.domain.categories.Category.UpdateCategory

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
