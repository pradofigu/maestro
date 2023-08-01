package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.flyway.tables.Customer.CUSTOMER
import br.com.pradofigu.maestro.flyway.tables.records.CustomerRecord
import br.com.pradofigu.maestro.persistence.config.JooqRepository
import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class CustomerRepository(
    private val context: DSLContext
): JooqRepository<CustomerRecord> {

    fun save(customer: Customer): Customer = CustomerRecord()
        .setName(customer.name)
        .setEmail(customer.email)
        .setCpf(customer.cpf.number)
        .setBirthDate(customer.birthDate)
        .setPhone(customer.phone)
        .let {
            context
                .insertInto(CUSTOMER).set(it)
                .returning()
                .fetchOne(this::toModel)
        } ?: throw DatabaseOperationException("Error on save customer", customer)

    fun findAll(): List<Customer> = context
        .selectFrom(CUSTOMER)
        .fetch(this::toModel)

    fun findBy(id: UUID): Customer? = context
        .selectFrom(CUSTOMER)
        .where(CUSTOMER.ID.eq(id))
        .fetchOne(this::toModel)

    fun findBy(cpf: CPF): Customer? = context
        .selectFrom(CUSTOMER)
        .where(CUSTOMER.CPF.eq(cpf.number))
        .fetchOne(this::toModel)

    @Transactional
    fun update(id: UUID, customer: Customer): Customer? = context
        .selectFrom(CUSTOMER)
        .where(CUSTOMER.ID.eq(id))
        .fetchOne()
        ?.apply {
            this.setName(customer.name)
            this.setEmail(customer.email)
            this.setCpf(customer.cpf.number)
            this.setBirthDate(customer.birthDate)
            this.setPhone(customer.phone)
        }?.let(this::optimizeColumnsUpdateOf)
        ?.let {
            context
                .update(CUSTOMER)
                .set(it)
                .where(CUSTOMER.ID.eq(id))
                .returning()
                .fetchOne(this::toModel)
            }

    fun delete(id: UUID): Boolean = context
        .delete(CUSTOMER)
        .where(CUSTOMER.ID.eq(id))
        .execute().let { it == 1 }

    private fun toModel(record: CustomerRecord): Customer = Customer(
        id = record.id,
        name = record.name,
        email = record.email,
        phone = record.phone,
        cpf = CPF(record.cpf),
        birthDate = record.birthDate
    )
}