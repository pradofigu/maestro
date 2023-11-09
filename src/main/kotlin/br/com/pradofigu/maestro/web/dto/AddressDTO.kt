package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Address
import java.util.*

data class AddressRequest(
    val street: String,
    val city: String,
    val uf: String,
    val postalCode: String
) {
    fun toModel() = Address(
        street = street,
        city = city,
        uf = uf,
        postalCode = postalCode
    )
}

data class AddressResponse(
    val id: UUID,
    val street: String?,
    val city: String?,
    val uf: String?,
    val postalCode: String?
) {
    companion object {
        fun from(address: Address) = AddressResponse(
            id = address.id!!,
            street = address.street,
            city = address.city,
            uf = address.uf,
            postalCode = address.postalCode
        )
    }
}
