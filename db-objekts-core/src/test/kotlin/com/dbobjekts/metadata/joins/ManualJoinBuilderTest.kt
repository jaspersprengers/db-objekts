package com.dbobjekts.metadata.joins

import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Address
import com.dbobjekts.testdb.acme.core.Country
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import com.dbobjekts.testdb.acme.library.Composite
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ManualJoinBuilderTest {

    val e = Employee
    val a = Address
    val ea = EmployeeAddress
    val c = Country
    val co = Composite

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
            .isEqualTo("CORE.EMPLOYEE em LEFT JOIN CORE.EMPLOYEE_ADDRESS ea on em.ID = ea.EMPLOYEE_ID LEFT JOIN CORE.ADDRESS ad on ad.ID = ea.ADDRESS_ID LEFT JOIN CORE.COUNTRY co on co.ID = ad.COUNTRY_ID")
    }

}
