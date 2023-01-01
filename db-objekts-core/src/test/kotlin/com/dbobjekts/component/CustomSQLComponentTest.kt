package com.dbobjekts.component

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CustomSQLComponentTest {

    @Test
    fun `test select two columns from two tables`() {
        AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        val tm = AcmeDB.transactionManager
        tm ({
            LocalDate.of(1990, 12, 5)
            val (id, name, salary, married, children, hobby) =
                it.sql(
                    "select e.id,e.name,e.salary,e.married, e.children, h.NAME from core.employee e join hr.HOBBY h on h.ID = e.HOBBY_ID where e.name = ?",
                    "Eve"
                ).withResultTypes().long().string().double().booleanNil().intNil().stringNil()
                    .first()
            assertThat(id).isPositive()
            assertThat(name).isEqualTo("Eve")
            assertThat(salary).isEqualTo(34000.0)
            assertThat(married).isTrue()
            assertThat(children).isEqualTo(2)
            assertThat(hobby).isEqualTo("Chess")


        })
    }


}
