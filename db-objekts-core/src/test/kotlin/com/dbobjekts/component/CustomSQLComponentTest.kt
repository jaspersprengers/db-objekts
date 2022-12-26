package com.dbobjekts.component

import com.dbobjekts.sampledbs.h2.acme.core.Employee
import com.dbobjekts.sampledbs.h2.acme.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CustomSQLComponentTest {

    @Test
    fun `test select two columns from two tables`() {
        AcmeDB.setupDatabaseObjects()
        AcmeDB.newTransaction({
            //it.insert(AllTypes)
            LocalDate.of(1990, 12, 5)
            it.insert(Hobby).mandatoryColumns("chess", "The game of champions").execute()
            it.insert(Employee).mandatoryColumns("John", 300.50, LocalDate.of(1990, 12, 5)).hobbyId("chess").execute()
            val (id, name, salary, married, children, hobby) =
                it.sql(
                    "select e.id,e.name,e.salary,e.married, e.children, h.NAME from core.employee e join hr.HOBBY h on h.ID = e.HOBBY_ID where e.name = ?",
                    "John"
                ).withResultTypes().long().string().double().booleanNil().intNil().stringNil()
                    .first()
            assertThat(id).isPositive()
            assertThat(name).isEqualTo("John")
            assertThat(salary).isEqualTo(300.5)
            assertThat(married).isNull()
            assertThat(children).isNull()
            assertThat(hobby).isEqualTo("The game of champions")


        })
    }


}
