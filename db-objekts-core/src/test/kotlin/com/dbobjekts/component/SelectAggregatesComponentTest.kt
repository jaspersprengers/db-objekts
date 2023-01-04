package com.dbobjekts.component

import com.dbobjekts.metadata.column.Aggregate
import com.dbobjekts.testdb.acme.core.Address
import com.dbobjekts.testdb.acme.core.Department
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeDepartment
import com.dbobjekts.testdb.acme.hr.Certificate
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SelectAggregatesComponentTest {


    companion object {

        val e = Employee
        val a = Address
        val ea = EmployeeDepartment
        val h = Hobby
        val tm = AcmeDB.transactionManager

        @BeforeAll
        @JvmStatic
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `use two aggregates columns fails`() {
        tm {
            Assertions.assertThatThrownBy { it.select(e.name, e.salary.avg(), e.children.sum()).asList() }
                .hasMessage("You can only use one aggregation type (sum/min/max/avg/count) in a select query, but you used 2")
        }
    }

    @Test
    fun `use having clause without an aggregate fails`() {
        tm {
            Assertions.assertThatThrownBy { it.select(e.name, e.children).having(Aggregate.gt(2)).asList() }
                .hasMessage("Use of the 'having' clause requires exactly one column to be designated as an aggregate with sum(), min(), max(), avg(), count() or distinct().")
        }
    }

    //count operations

    @Test
    fun `count distinct number of children`() {
        tm {
            val kiddos = it.select(e.children.countDistinct()).asList()
            assertThat(kiddos).hasSize(1)
            assertThat(kiddos[0]).isEqualTo(5)
        }
    }

    @Test
    fun `count certificates per person`() {
        tm {
            val rows = it.select(e.name, Certificate.name.count()).having(Aggregate.gt(0))
                .orderDesc(Certificate.name)
                .orderAsc(e.name).asList()
            assertThat(rows[0].v1).isEqualTo("Alice")
            assertThat(rows[0].v2).isEqualTo(2)
            assertThat(rows[1].v1).isEqualTo("Bob")
            assertThat(rows[1].v2).isEqualTo(1)
            assertThat(rows[2].v1).isEqualTo("Charlie")
            assertThat(rows[2].v2).isEqualTo(1)
        }
    }

    @Test
    fun `count employees per department`() {
        tm {
            val highest = it.select(ea.departmentId.count(), Department.name).orderDesc(ea.departmentId).first()
            assertThat(highest.v1).isEqualTo(5)
            assertThat(highest.v2).isEqualTo("Information Technology")
        }
    }

    @Test
    fun `get department with more than 2 and less than 5 employees `() {
        tm {
            val highest = it.select(ea.departmentId.count(), Department.name)
                .having(Aggregate.gt(2).and().lt(5)).first()
            assertThat(highest.v1).isEqualTo(3)
            assertThat(highest.v2).isEqualTo("Human Resources")
        }
    }

    //DISTINCT queries
    @Test
    fun `get distinct number of children and hobbies`() {
        tm { tr ->
            val kiddos = tr.select(e.children.distinct()).asList()
            assertThat(kiddos).containsExactlyInAnyOrder(0, 1, 2, 3, 5)

            val hobbies = tr.select(e.hobbyId.distinct()).where(e.hobbyId.isNotNull()).asList()
            assertThat(hobbies).containsExactlyInAnyOrder("c", "p", "f")

            Assertions.assertThatThrownBy { tr.select(e.name.distinct()).having(Aggregate.gt(2)).asList() }
        }

    }

    //MAX operations
    @Test
    fun `get highest salary`() {
        tm { assertThat(it.select(e.salary.max()).first()).isEqualTo(39000.0) }
    }

    @Test
    fun `Which department has the employee with the most number of children`() {
        tm {
            val list = it.select(e.children.max(), Department.name).orderDesc(e.children).asList()
            assertThat(list).hasSize(3)
            val (kiddos, name) = list[0]
            assertThat(name).isEqualTo("Information Technology")
            assertThat(kiddos).isEqualTo(5)
        }
    }

    //MIN operations
    @Test
    fun `get lowest salary`() {
        tm { assertThat(it.select(e.salary.min()).first()).isEqualTo(30000.0) }
        tm { assertThat(it.select(e.children.min(), Department.name).orderAsc(e.children).first().v2).isEqualTo("Human Resources") }
    }

    //Average operations
    @Test
    fun `get average salary`() {
        tm { assertThat(it.select(e.salary.avg()).first()).isEqualTo(34500.0) }
    }

    //SUM operations
    @Test
    fun `sum of salaries by hobby name`() {
        tm({
            it.select(e.salary.sum(), h.name)
                .orderDesc(h.name)
                .having(Aggregate.gt(70000).and().lt(100000))
                .asList().forEach {
                    println(it)
                }
        })
    }

    @Test
    fun `average salaries by country of residence`() {
        tm({
            it.select(e.salary.sum(), h.name)
                .orderDesc(h.name)
                .having(Aggregate.gt(70000).and().lt(100000))
                .asList().forEach {
                    println(it)
                }
        })
    }


}
