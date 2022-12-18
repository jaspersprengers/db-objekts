package com.dbobjekts.integration.h2

import com.dbobjekts.Tuple6
import com.dbobjekts.Tuple9
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.statement.customsql.ResultTypes
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class CustomSQLIntegrationTest {

    @Test
    fun `test select two columns from two tables`() {
        H2DB.setupDatabaseObjects()
        H2DB.newTransaction({
            //it.insert(AllTypes)
            val dob = LocalDate.of(1990, 12, 5)
            it.insert(Hobby).mandatoryColumns("chess", "The game of champions").execute()
            it.insert(Employee).mandatoryColumns("John", 300.50, LocalDate.of(1990, 12, 5)).hobbyId("chess").execute()
            val (id, name, salary, married, children, hobby) =
                it.select(
                    "select e.id,e.name,e.salary,e.married, e.children, h.NAME from core.employee e join hr.HOBBY h on h.ID = e.HOBBY_ID where e.name = ?",
                    "John"
                )
                    .returning(ResultTypes.long().string().double().booleanNil().intNil().stringNil())
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
