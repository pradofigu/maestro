package br.com.pradofigu.maestro.categories.application.ports.out

import br.com.pradofigu.maestro.categories.domain.Category

interface CreateCategoryOutPort {

    fun save(category: Category.CreateCategory): Category?

}