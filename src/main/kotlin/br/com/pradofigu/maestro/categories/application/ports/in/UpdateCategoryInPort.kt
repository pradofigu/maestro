package br.com.pradofigu.maestro.categories.application.ports.`in`

import br.com.pradofigu.maestro.categories.domain.Category
import java.util.*

interface UpdateCategoryInPort {

    fun update(fromString: UUID, toUpdateCategory: Category.UpdateCategory): Category

}
