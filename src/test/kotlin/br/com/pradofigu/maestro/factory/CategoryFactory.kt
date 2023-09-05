package br.com.pradofigu.maestro.factory

import br.com.pradofigu.maestro.usecase.model.Category
import java.util.UUID
import kotlin.random.Random

object CategoryFactory {

        fun create(name: String = "Category ${Random.nextInt(1, 9999)}") =
                Category(id = UUID.randomUUID(), name = name)
}