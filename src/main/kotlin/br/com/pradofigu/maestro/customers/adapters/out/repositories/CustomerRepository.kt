package br.com.pradofigu.maestro.customers.adapters.out.repositories

import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.Customer.CUSTOMER
import br.com.pradofigo.maestro.infrastructure.entities.maestro.tables.records.CustomerRecord
import br.com.pradofigu.maestro.customers.application.ports.out.FindCustomerOutPort
import br.com.pradofigu.maestro.customers.application.ports.out.RegisterCustomerOutPort
import br.com.pradofigu.maestro.customers.domain.CPF
import br.com.pradofigu.maestro.customers.domain.Customer
import br.com.pradofigu.maestro.infrastructure.repositories.JooqRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CustomerRepository(@Autowired private val context: DSLContext) : RegisterCustomerOutPort, FindCustomerOutPort, JooqRepository<CustomerRecord> {

    @Transactional
    override fun save(customer: Customer.CreateCustomer): Customer? {
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

    override fun findBy(cpf: CPF): Customer? {
        return context.selectFrom(CUSTOMER).where(CUSTOMER.CPF.eq(cpf.number))
            .fetchOne(this::toCustomer)
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