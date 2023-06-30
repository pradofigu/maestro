package br.com.pradofigu.maestro.output.persistence

import org.jooq.Record

internal interface JooqRepository<T : Record> {

    /**
     * Sets the column change to false when the value didn't change, avoiding updating
     * all columns unnecessary. Feature suppose to be introduced on JOOQ-3.19V
     * (https://github.com/jOOQ/jOOQ/issues/5394)
     **/
    fun optimizeColumnsUpdateOf(record: T): T {
        for (i in 0 until  record.size()) {
            if (record.get(i) == record.original(i)) record.changed(i, false)
        }

        return record
    }
}