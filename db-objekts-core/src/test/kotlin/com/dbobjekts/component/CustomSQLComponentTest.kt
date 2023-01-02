package com.dbobjekts.component

import ColumnClasses
import com.dbobjekts.api.Tuple2
import com.dbobjekts.api.Tuple3
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.testdb.AddressType
import com.dbobjekts.testdb.AddressTypeAsStringColumn
import com.dbobjekts.testdb.NullableAddressTypeAsStringColumn
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CustomSQLComponentTest {

    @Test
    fun `test all employees with home and work address`() {
        AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        val tm = AcmeDB.transactionManager
        tm({
            val rows = it.sql(
                """
                        select e.id,e.name,e.SALARY,e.MARRIED,e.CHILDREN,
                          e.DATE_OF_BIRTH, h.NAME, ha.STREET as home_addess, wa.STREET as street_address, d.NAME, c.NAME
                        from core.EMPLOYEE e left join hr.HOBBY h on h.ID = e.HOBBY_ID
                            left join core.EMPLOYEE_ADDRESS home on home.EMPLOYEE_ID = e.ID and home.KIND = 'HOME'
                            left join core.ADDRESS ha on ha.ID = home.ADDRESS_ID
                            left join core.EMPLOYEE_ADDRESS work on work.EMPLOYEE_ID = e.ID and work.KIND = 'WORK'
                            left join core.ADDRESS wa on wa.ID = work.ADDRESS_ID
                        left join core.EMPLOYEE_DEPARTMENT ed on ed.EMPLOYEE_ID = e.id
                        left join core.DEPARTMENT d on d.ID = ed.DEPARTMENT_ID
                        left join hr.CERTIFICATE c on c.EMPLOYEE_ID = e.ID order by e.id asc;
                    """.trimIndent()
            ).withResultTypes().long().string().double().booleanNil().intNil().date().stringNil().stringNil().stringNil().string()
                .stringNil()
                .asList()
            rows.forEach { t ->
                println("${t.v1}\t${t.v2}\t${t.v3}\t${t.v4}\t${t.v5}\t${t.v6}\t${t.v7}\t${t.v8}\t${t.v9}\t${t.v10}")
            }
            assertThat(rows).hasSize(10)
        })
    }


    @Test
    fun `test custom`() {
        AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        val tm = AcmeDB.transactionManager
        tm({
            val rows: List<Tuple2<Boolean, AddressType?>> = it.sql(
                "select e.has_children,e.address_type from EMPLOYEE e"
            ).withResultTypes()
                .custom(ColumnClasses.NUMBER_AS_BOOLEAN)
                .customNil(NullableAddressTypeAsStringColumn::class.java)
                .asList()
            rows.forEach { t ->
                println("${t.v1}\t${t.v2}")
            }
            assertThat(rows).hasSize(20)
        })
    }

}
