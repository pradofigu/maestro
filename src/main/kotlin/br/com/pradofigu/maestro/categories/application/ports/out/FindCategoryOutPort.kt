package br.com.pradofigu.maestro.categories.application.ports.out

import br.com.pradofigu.maestro.categories.domain.Category
import java.util.*

interface FindCategoryOutPort {

    fun findBy(id: UUID): Category?

}
