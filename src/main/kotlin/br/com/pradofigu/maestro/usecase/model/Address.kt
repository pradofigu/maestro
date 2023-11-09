package br.com.pradofigu.maestro.usecase.model

import br.com.pradofigu.maestro.persistence.entity.AddressEntity
import java.util.*

data class Address(
    val id: UUID? = UUID.randomUUID(),
    val street: String?,
    val city: String?,
    val uf: String?,
    val postalCode: String?
) {

    fun toEntity(): AddressEntity = AddressEntity(
        street = this.street,
        city = this.city,
        uf = this.uf,
        postalCode = this.postalCode
    ).apply { this.id = this@Address.id }
}