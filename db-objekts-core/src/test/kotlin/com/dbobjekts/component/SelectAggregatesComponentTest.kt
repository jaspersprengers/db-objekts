package com.dbobjekts.component

import com.dbobjekts.metadata.column.Aggregate
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Certificate
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SelectAggregatesComponentTest {


    companion object {

        val e = Employee
        val a = Address
        val ed = EmployeeDepartment
        val h = Hobby
        val c = Country
        val tm = AcmeDB.transactionManager

        @BeforeAll
        @JvmStatic
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `use aggregate in whereclause fails`() {
        tm {
            assertThatThrownBy { it.select(e.name, e.salary.avg()).where(e.salary.avg().gt(100.0)).asList() }
                .hasMessage("Cannot use aggregate method AVG for column SALARY in a whereclause.")
        }
    }

    @Test
    fun `use two aggregates columns fails`() {
        tm {
            assertThatThrownBy { it.select(e.name, e.salary.avg(), e.children.sum()).asList() }
                .hasMessage("You can only use one aggregation type (sum/min/max/avg/count) in a select query, but you used 2")
        }
    }

    @Test
    fun `use having clause without an aggregate fails`() {
        tm {
            assertThatThrownBy { it.select(e.name, e.children).having(Aggregate.gt(2)).asList() }
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
            val rows = it.select(e.name, Certificate.name.count()).having(Aggregate.gt(0).and().lt(3))
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
            val (count, name) = it.select(ed.departmentId.count(), Department.name).orderDesc(ed.departmentId).first()
            assertThat(count).isEqualTo(5)
            assertThat(name).isEqualTo("Information Technology")

            val itCount = it.select(ed.departmentId.count()).where(Department.name.eq("Information Technology")).first()
            assertThat(itCount).isEqualTo(5)
        }
    }

    @Test
    fun `get department with more than 2 and less than 5 employees `() {
        tm {
            val (count, name) = it.select(ed.departmentId.count(), Department.name)
                .having(Aggregate.gt(2).and().lt(5)).first()
            assertThat(count).isEqualTo(3)
            assertThat(name).isEqualTo("Human Resources")
        }
    }

    //DISTINCT queries
    @Test
    fun `get distinct number of children and hobbies`() {
        tm { tr ->
            val kiddos = tr.select(e.children.distinct()).asList()
            assertThat(kiddos).containsExactlyInAnyOrder(null, 0, 1, 2, 3, 5)

            val hobbies = tr.select(e.hobbyId.distinct()).where(e.hobbyId.isNotNull()).asList()
            assertThat(hobbies).containsExactlyInAnyOrder("c", "p", "f")

            assertThatThrownBy { tr.select(e.name.distinct()).having(Aggregate.gt(2)).asList() }
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

    @Test
    fun `use same column in aggregate as well as whereclause`() {
        tm { assertThat(it.select(e.salary.max().nullable).where(e.salary.gt(40000.0)).firstOrNull()).isNull() }
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
            val record = it.select(e.salary.sum(), h.name)
                .orderDesc(h.name)
                .having(Aggregate.gt(70000).and().lt(100000))
                .first()
            assertThat(record.v1).isEqualTo(98000.0)
            assertThat(record.v2).isEqualTo("Photography")
        })
    }

    @Test
    fun `average salaries by country of residence`() {
        tm({
            val record = it.select(e.salary.sum(), a.countryId)
                .orderDesc(a.countryId)
                .having(Aggregate.gt(70000).and().lt(100000))
                .first()
        assertThat(record.v1).isEqualTo(99000.0)
        assertThat(record.v2).isEqualTo("be")
        })
    }


}
