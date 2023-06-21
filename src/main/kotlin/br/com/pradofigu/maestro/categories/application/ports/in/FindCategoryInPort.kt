package br.com.pradofigu.maestro.categories.application.ports.`in`

import br.com.pradofigu.maestro.categories.domain.Category
import java.util.*

interface FindCategoryInPort {

    fun findBy(fromString: UUID): Category?

}
