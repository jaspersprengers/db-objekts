package com.dbobjekts.component

import com.dbobjekts.api.ColumnClasses
import com.dbobjekts.metadata.column.EnumAsStringColumn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
            assertThat(rows).hasSize(11)
        })
    }


    @Test
    fun `test custom`() {
        AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        val tm = AcmeDB.transactionManager
        tm({
            val rows = it.sql(
                """
                        select e.children,home.kind,e.id
                        from core.EMPLOYEE e 
                            left join core.EMPLOYEE_ADDRESS home on home.EMPLOYEE_ID = e.ID 
                            left join core.ADDRESS ha on ha.ID = home.ADDRESS_ID                            
                    """.trimIndent()
            ).withResultTypes()
                .custom(ColumnClasses.NUMBER_AS_BOOLEAN)

                .asList()
            assertThat(rows).hasSize(20)
        })
    }

}
