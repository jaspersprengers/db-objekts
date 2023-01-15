package com.dbobjekts.metadata

import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class SubClauseTest {

    @Test
    fun `create sub clause with column`(){
        val c = CatalogDefinition
        val clause = Employee.name.eq(Hobby.name)
        Assertions.assertThat(clause.toSQL(SQLOptions(true))).isEqualTo("and(e.NAME = h.NAME)")

    }
}
