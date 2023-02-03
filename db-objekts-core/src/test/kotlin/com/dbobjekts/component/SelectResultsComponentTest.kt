package com.dbobjekts.component

import com.dbobjekts.testdb.acme.Aliases
import com.dbobjekts.testdb.acme.HasAliases
import com.dbobjekts.testdb.acme.core.Employee
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SelectResultsComponentTest : HasAliases by Aliases {


    companion object {
        val tm = AcmeDB.transactionManager
        @BeforeAll
        @JvmStatic
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `select with foreach loop`() {
        tm({
            var counter = 0
            it.select(Employee.name)
                .forEachRow({ nmb, row ->
                    counter += 1
                    nmb < 5 // break after 5 rows
                })
            assertThat(counter).isEqualTo(5)
        })
    }

    @Test
    fun `test select all employees with iterator`() {
        tm({
            val buffer = mutableListOf<String?>()
            val iterator = it.select(em.name).where(em.id.lt(11))
                .orderAsc(em.name).iterator()
            while (iterator.hasNext()) {
                buffer.add(iterator.next())
            }
            assertThat(buffer.size).isEqualTo(10)
        })
    }

    @Test
    fun `cannot return iterator from transaction block`() {
        assertThatThrownBy {
            val ret: Iterator<String> = tm({ it.select(em.name).where(em.id.lt(11)).iterator() })
        }.hasMessageEndingWith("An Iterator over a ResultSet must be consumed within the transaction block.")
    }

    @Test
    fun `cannot fetch results twice`() {
        tm({
            val statement = it.select(em.name).where(em.id.lt(11))
            statement.asList()
            assertThatThrownBy {
                statement.asList()
            }.hasMessageEndingWith("Cannot retrieve results twice.")

            assertThatThrownBy {
                statement.first()
            }.hasMessageEndingWith("Cannot retrieve results twice.")

            assertThatThrownBy {
                statement.forEachRow({ _, _ -> true })
            }.hasMessageEndingWith("Cannot retrieve results twice.")

            assertThatThrownBy {
                statement.iterator()
            }.hasMessageEndingWith("Cannot retrieve results twice.")
        })
    }

    @Test
    fun `select slice of all employees`() {
        tm({
            val two: List<String> = it.select(em.name).orderAsc(em.name).asSlice(2, 4)
            //skipping Alice and Bob
            assertThat(two).containsExactly("Charlie", "Diane", "Eve", "Fred")
        })
    }

    @Test
    fun `select slice for unavailable range`() {
        tm({
            val list = it.select(em.name).orderAsc(em.name).asSlice(11, 4)
            assertThat(list).isEmpty()
        })
    }


}
