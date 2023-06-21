package br.com.pradofigu.maestro.categories.application.ports.out

import java.util.*

interface DeleteCategoryOutPort {

    fun deleteBy(id: UUID): Boolean

}
