package br.com.pradofigu.maestro.domain.category.model

import java.util.UUID

data class Category(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
)
