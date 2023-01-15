package com.dbobjekts.metadata.joins

import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Address
import com.dbobjekts.testdb.acme.core.Country
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import com.dbobjekts.testdb.acme.library.Composite
import com.dbobjekts.testdb.acme.library.CompositeForeignKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ManualJoinBuilderTest {

    val e = Employee
    val a = Address
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
        assertThat(Employee
            .leftJoin(EmployeeAddress).on(e.id.eq(ea.employeeId))
            .leftJoin(Address).on(a.id.eq(ea.addressId))
            .leftJoin(Country).on(c.id.eq(a.countryId)).toSQL())
            .isEqualTo("CORE.EMPLOYEE e LEFT JOIN CORE.EMPLOYEE_ADDRESS ea on e.ID = ea.EMPLOYEE_ID LEFT JOIN CORE.ADDRESS a on a.ID = ea.ADDRESS_ID LEFT JOIN CORE.COUNTRY c on c.ID = a.COUNTRY_ID")
    }

    @Test
    fun `composite join`(){
        val from: ManualJoinChain = co
            .innerJoin(cof).on(co.isbn.eq(cof.isbn).and(cof.title).eq(co.title))
        assertThat(from.toSQL()).isEqualTo("LIBRARY.COMPOSITE c2 INNER JOIN LIBRARY.COMPOSITE_FOREIGN_KEY cfk on c2.ISBN = cfk.ISBN and cfk.TITLE = c2.TITLE")
    }

    @Test
    fun `right join`(){
        val from: ManualJoinChain = co
            .rightJoin(cof).on(co.isbn.eq(cof.isbn).and(cof.title).eq(co.title))
        assertThat(from.toSQL()).isEqualTo("LIBRARY.COMPOSITE c2 RIGHT JOIN LIBRARY.COMPOSITE_FOREIGN_KEY cfk on c2.ISBN = cfk.ISBN and cfk.TITLE = c2.TITLE")
    }
}
