package com.dbobjekts.component

import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

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
    fun `Delete statement scenarios`() {
        tm { tr ->

            tr.deleteFrom(Employee)
                .where(Employee.name.notIn("Bob", "Alice", "Eve").and(Employee.married.eq(false).or(Employee.salary.gt(34000.0))))
            assertThat(tr.select(e.name).asList()).containsExactlyInAnyOrder("Eve", "Bob", "Diane", "Alice", "Gina", "Charlie")

            assertThat(tr.deleteFrom(Employee).where(Employee.id.eq(1))).isEqualTo(1)
            assertThat(tr.deleteFrom(Employee).where(Employee.id.eq(1))).isEqualTo(0)

            //this is not supported by H2, but possible in (e.g.) MariaDB

            assertThatThrownBy { tr.deleteFrom(e.innerJoin(Hobby)).where(h.name.eq("curling")) }

            //delete all Employee records
            tr.deleteFrom(Employee).where()
            assertThat(tr.select(Employee).asList()).isEmpty()
        }
    }

}

