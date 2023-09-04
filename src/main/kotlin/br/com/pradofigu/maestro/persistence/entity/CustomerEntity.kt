package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "customer")
data class CustomerEntity(
    @Column
    var name: String? = null,

    @Column(unique = true)
    var cpf: String? = null,

    @Column
    var email: String? = null,

    @Column
    var phone: String? = null,

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null
) : AbstractEntity() {

    fun toModel(): Customer = Customer(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        cpf = if (this.cpf == null) null else CPF(this.cpf!!),
        birthDate = this.birthDate
    )
}
