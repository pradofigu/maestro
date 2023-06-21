package br.com.pradofigu.maestro.categories.application.ports.`in`

import br.com.pradofigu.maestro.categories.domain.Category

interface CreateCategoryInPort {

    fun create(category: Category.CreateCategory): Category

}