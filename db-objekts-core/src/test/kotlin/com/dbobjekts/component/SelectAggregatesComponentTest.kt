package com.dbobjekts.component

import com.dbobjekts.metadata.column.Aggregate
import com.dbobjekts.testdb.acme.core.Address
import com.dbobjekts.testdb.acme.core.Department
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeDepartment
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

    @Test
    fun `count distinct number of children`() {
        tm {
            val kiddos = it.select(e.children.countDistinct()).asList()
            assertThat(kiddos).hasSize(1)
            assertThat(kiddos[0]).isEqualTo(5)
        }
    }

    @Test
    fun `count employees per department`() {
        val highest = tm {
            it.select(ea.departmentId.count(), Department.name).orderDesc(ea.departmentId).first()
        }
        assertThat(highest.v1).isEqualTo(5)
        assertThat(highest.v2).isEqualTo("Information Technology")
    }

    @Test
    fun `get department with more than 2 and less than 5 employees `() {
        val highest = tm {
            it.select(ea.departmentId.count(), Department.name).having(Aggregate.gt(2).and().lt(5)).first()
        }
        assertThat(highest.v1).isEqualTo(3)
        assertThat(highest.v2).isEqualTo("Human Resources")
    }

    //DISTINCT queries
    @Test
    fun `get distinct number of children`() {
        tm { tr ->
            val kiddos = tr.select(e.children.distinct()).asList()
            assertThat(kiddos).containsExactlyInAnyOrder(0, 1, 2, 3, 5)

            val hobbies = tr.select(e.hobbyId.distinct()).where(e.hobbyId.isNotNull()).asList()
            assertThat(hobbies).containsExactlyInAnyOrder("c", "p", "f")
        }

    }

    //MAX operations
    @Test
    fun `get highest salary`() {
        val highest = tm {
            it.select(e.salary.max()).first()
        }
        assertThat(highest).isEqualTo(39000.0)
    }


    @Test
    fun `test order by clause by aggregate column`() {
        val list = tm {
            it.select(e.children.max(), Department.name).orderDesc(e.children).asList()
        }
        assertThat(list).hasSize(3)
        val (kiddos, name) = list[0]
        assertThat(name).isEqualTo("Information Technology")
        assertThat(kiddos).isEqualTo(5)
    }

    @Test
    fun `get lowest salary`() {
        val lowest = tm {
            it.select(e.salary.min()).first()
        }
        assertThat(lowest).isEqualTo(30000.0)
    }

    @Test
    fun `get average salary`() {
        val average = tm {
            it.select(e.salary.avg()).first()
        }
        assertThat(average).isEqualTo(34500.0)
    }

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

    @Test
    fun `count number of certificates per person`() {
        // aggregate over Long, Int, Double, Float (later add Byte, Short, BigDecimal)
        // use MIN, Max, sum, avg
    }

}
