package br.com.pradofigu.maestro.input.restapi.category.dto

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.*

data class CategoryRequest(val id: UUID?, val name: String){
    constructor(name: String) : this(null, name)
    fun toModel() = Category(
        id = id,
        name = name
    )
}
