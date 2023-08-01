package br.com.pradofigu.maestro.usecase.model

import java.util.UUID

data class Category(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
)
