package com.dbobjekts.component

import com.dbobjekts.api.ColumnClasses
import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.fixture.columns.AddressTypeColumn
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.EnumAsStringColumn
import com.dbobjekts.metadata.column.NullableNumberAsBooleanColumn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class CustomSQLComponentTest {


    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }

        val tm = AcmeDB.transactionManager
    }

    val sql = """
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

    @Test
    fun `test all employees with home and work address`() {

        tm({
            val rows =
                it.sql(sql).withResultTypes().long().string().double().booleanNil().intNil().date().stringNil().stringNil().stringNil()
                    .string()
                    .stringNil()
                    .asList()
            assertThat(rows).hasSize(11)
        })
    }

    @Test
    fun `select with iterator`() {

        tm({
            val iterator = it.sql("select e.id from core.EMPLOYEE e").withResultTypes().long()
                .iterator()
            var counter = 0
            iterator.forEachRemaining { row ->
                counter += 1
            }
            assertThat(counter).isEqualTo(10)
        })
    }

    @Test
    fun `select with foreach loop`() {
        tm({
            var counter = 0
            it.sql("select e.id from core.EMPLOYEE e").withResultTypes().long()
                .forEachRow({ nmb, row ->
                    counter += 1
                    nmb < 5 // break after 5 rows
                })
            assertThat(counter).isEqualTo(5)
        })
    }


    @Test
    fun `test custom`() {
        tm({
            val rows = it.sql(
                """
                        select e.children,home.kind,e.id
                        from core.EMPLOYEE e 
                            left join core.EMPLOYEE_ADDRESS home on home.EMPLOYEE_ID = e.ID 
                            left join core.ADDRESS ha on ha.ID = home.ADDRESS_ID                            
                    """.trimIndent()
            ).withResultTypes()
                .customNil(NullableNumberAsBooleanColumn::class.java)
                .custom(AddressTypeColumn::class.java)
                .asList()
            assertThat(rows).hasSize(20)
        })
    }

}
