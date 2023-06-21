package br.com.pradofigu.maestro.infrastructure.repositories.customers

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Customer.CUSTOMER
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CustomerRecord
import br.com.pradofigu.maestro.domain.customers.CPF
import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.customers.Customer.CreateCustomer
import br.com.pradofigu.maestro.domain.customers.Customers
import br.com.pradofigu.maestro.infrastructure.repositories.JooqRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class CustomerRepository(@Autowired private val context: DSLContext) : Customers, JooqRepository<CustomerRecord> {

    @Transactional
    override fun save(customer: CreateCustomer): Customer? {
        val record = CustomerRecord()
            .setName(customer.name)
            .setEmail(customer.email)
            .setCpf(customer.cpf.number)
            .setBirthDate(customer.birthDate)
            .setPhone(customer.phone)

        return context.insertInto(CUSTOMER).set(record)
            .returning()
            .fetchOne(this::toCustomer)
    }

    override fun findBy(id: UUID): Customer? {
        return context.selectFrom(CUSTOMER).where(CUSTOMER.ID.eq(id))
            .fetchOne(this::toCustomer)
    }

    override fun findBy(cpf: CPF): Customer? {
        return context.selectFrom(CUSTOMER).where(CUSTOMER.CPF.eq(cpf.number))
            .fetchOne(this::toCustomer)
    }

    @Transactional
    override fun update(id: UUID, customer: Customer.UpdateCustomer): Customer? {
        return context.selectFrom(CUSTOMER).where(CUSTOMER.ID.eq(id)).fetchOne()
            ?.let { record ->

                record.setName(customer.name)
                    .setEmail(customer.email)
                    .setCpf(customer.cpf.number)
                    .setBirthDate(customer.birthDate)
                    .setPhone(customer.phone)
            }
            ?.let(this::optimizeColumnsUpdateOf)
            ?.let { record ->
                context.update(CUSTOMER).set(record).where(CUSTOMER.ID.eq(id))
                    .returning()
                    .fetchOne(this::toCustomer)
            }
    }

    @Transactional
    override fun delete(id: UUID): Boolean {
        val result = context.delete(CUSTOMER).where(CUSTOMER.ID.eq(id)).execute()
        return 1 == result;
    }

    private fun toCustomer(record: CustomerRecord): Customer {
        return Customer(
            id = record.id,
            name = record.name,
            email = record.email,
            phone = record.phone,
            cpf = CPF(record.cpf),
            birthDate = record.birthDate
        )
    }

}