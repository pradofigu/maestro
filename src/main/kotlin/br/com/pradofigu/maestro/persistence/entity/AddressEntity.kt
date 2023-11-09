package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.Address
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "address")
data class AddressEntity(
    @Column
    var street: String? = null,

    @Column
    var city: String? = null,

    @Column
    var uf: String? = null,

    @Column(name = "postal_code")
    var postalCode: String? = null
) : AbstractEntity() {

    fun toModel(): Address = Address(
        id = this.id,
        street = this.street,
        city = this.city,
        uf = this.uf,
        postalCode = this.postalCode
    )
}
