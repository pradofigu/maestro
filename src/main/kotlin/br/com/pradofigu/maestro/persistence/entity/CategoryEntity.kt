package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.Category
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "category")
data class CategoryEntity(

    @Column(unique = true, nullable = false)
    val name: String = "",
) : AbstractEntity() {

    fun toModel() = Category(id = this.id, name = this.name)
}