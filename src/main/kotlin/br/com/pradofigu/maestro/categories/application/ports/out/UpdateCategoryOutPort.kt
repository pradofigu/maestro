package br.com.pradofigu.maestro.categories.application.ports.out

import br.com.pradofigu.maestro.categories.domain.Category
import java.util.*

interface UpdateCategoryOutPort {

    fun update(id: UUID, category: Category.UpdateCategory): Category?

}
