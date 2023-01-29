package com.dbobjekts.metadata

import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class SubClauseTest {

    val c = CatalogDefinition

    @Test
    fun `create sub clause with column`(){
        val clause = Employee.name.eq(Hobby.name)
        assertThat(SubClause.toSQL(clause, SQLOptions(true))).isEqualTo("em.NAME = ho.NAME")
    }
}
