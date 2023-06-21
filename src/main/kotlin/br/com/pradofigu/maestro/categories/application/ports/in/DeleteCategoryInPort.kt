package br.com.pradofigu.maestro.categories.application.ports.`in`

import java.util.*

interface DeleteCategoryInPort {

    fun deleteBy(fromString: UUID): Boolean

}
