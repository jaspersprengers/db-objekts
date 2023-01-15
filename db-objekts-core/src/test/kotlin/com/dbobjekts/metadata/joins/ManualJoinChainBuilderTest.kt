package com.dbobjekts.metadata.joins

import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Country
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.testdb.acme.library.Composite
import com.dbobjekts.testdb.acme.library.CompositeForeignKey
import com.dbobjekts.testdb.acme.library.CompositeJoinChain
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ManualJoinChainBuilderTest {

    val e = Employee
    val ea = EmployeeAddress
    val c = Country
    val co = Composite
    val cof = CompositeForeignKey

    @BeforeEach
    fun setup() {
        val c = CatalogDefinition
    }

    @Test
    fun `join Employee and Country`() {
        assertThat(Employee.leftJoin(Country).on(e.name.eq(c.name)).toString())
            .isEqualTo("")
    }

    @Test
    fun `join Employee and EmployeeAddress`() {
        assertThat(Employee
            .leftJoin(EmployeeAddress).on(e.name.eq(ea.employeeId)).toString())
            .isEqualTo("")
    }

    @Test
    fun `three joins`(){
        val from: ManualJoinChainBuilder = co
            .leftJoin(cof).on(co.isbn.eq(cof.isbn).and(cof.title).eq(co.title))
        assertThat(from.toString()).isEqualTo("COMPOSITE c2 LEFT JOIN COMPOSITE_FOREIGN_KEY cfk on c2.ISBN = cfk.ISBN and cfk.TITLE = c2.TITLE")
    }

}
