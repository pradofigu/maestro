package br.com.pradofigu.maestro.domain.categories

import java.util.UUID

data class Category(
    val id: UUID,
    val name: String,
) {

    class CreateCategory(
        val name: String,
    )

    class UpdateCategory(
        val name: String,
    )

}
