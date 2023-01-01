package com.dbobjekts.component

import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.testdb.acme.core.EmployeeRow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DeleteStatementComponentTest {

    val e = Employee
    val h = Hobby

    companion object {
        val tm = AcmeDB.transactionManager

        @JvmStatic
        @BeforeAll
        @AfterAll
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `delete clause correct sql`() {
        tm { tr ->
            tr.deleteFrom(Employee).where(Employee.id.eq(5L).and(Employee.name).notIn("bob", "alice", "eve"))

            tr.deleteFrom(Employee)
                .where(Employee.name.notIn("Bob", "Alice", "Eve").and(Employee.married.eq(false).or(Employee.salary.gt(34000.0))))
            assertThat(tr.select(e.name).asList()).containsExactlyInAnyOrder("Eve", "Bob", "Diane", "Alice", "Gina", "Charlie")

            assertThat(tr.deleteFrom(Employee).where(Employee.id.eq(1))).isEqualTo(1)

            Assertions.assertThatThrownBy { tr.deleteFrom(e.innerJoin(Hobby)).where(h.name.eq("curling")) }
                .hasMessage("Your database does not support DELETE statements with JOIN syntax.")

            tr.deleteFrom(Employee).where()
            assertThat(tr.select(Employee).asList()).isEmpty()
        }
    }

}
