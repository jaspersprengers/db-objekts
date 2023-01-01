package com.dbobjekts.component

import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Certificate
import org.junit.jupiter.api.Order

class SelectStatementComponentTest {


    companion object {

        val e = Employee
        val a = Address
        val h = Hobby


        val tm = AcmeDB.transactionManager
        
        @BeforeAll
        @JvmStatic
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `verify ranges`() {
        assertEquals(7, tm { it.select(e.salary).where(e.salary.notIn(30000.0,31000.0,32000.0)).asList().size })
        assertEquals(3, tm { it.select(e.salary).where(e.salary.within(30000.0,31000.0,32000.0)).asList().size })
        assertEquals(4, tm { it.select(e.salary).where(e.salary.gt(35000.0)).asList().size })
        assertEquals(5, tm { it.select(e.salary).where(e.salary.ge(35000.0)).asList().size })
        assertEquals(4, tm { it.select(e.salary).where(e.salary.lt(34000.0)).asList().size })
        assertEquals(5, tm { it.select(e.salary).where(e.salary.le(34000.0)).asList().size })
        assertEquals(3, tm { it.select(e.salary).where(e.salary.ge(33000.0).and(e.salary).le(35000.0)).asList().size })

        assertEquals(39000.0, tm { it.select(e.salary).orderDesc(e.salary).first() })
        assertEquals(30000.0, tm { it.select(e.salary).orderAsc(e.salary).first() })

    }

    @Test
    fun `select all`(){
        AcmeDB.newTransaction { tr ->
            tr.select(Employee, Address.street, EmployeeAddress.kind, Department.name, Hobby.name.nullable, Certificate.name.nullable).useOuterJoins().asList().forEach {
                (emp,street,addType, dept,hobby,cert) ->
                println("${emp.id}\t${emp.name}\t${emp.salary}\t${emp.married}\t${emp.children}\t${emp.dateOfBirth}\t$hobby\t$street\t$addType\t$dept\t$cert")
            }
        }
    }

    @Test
    fun `select all from employee and hobby`() {
        tm({
            val (employee, hobby) =
                it.select(Employee, h.name).where(e.name.eq("Eve")).first()
            assertThat(hobby).isEqualTo("Chess")
            assertThat(employee.name).isEqualTo("Eve")
        })
    }

    @Test
    fun `use nullable counterpart`() {
        tm({
            val (name, hobby) =
                it.select(e.name, h.name.nullable).where(e.name.eq("Bob")).useOuterJoins().first()
            assertThat(hobby).isNull()
        })
    }

    @Test
    fun `do not use default value for null result in non-nullable column throws`() {
        tm({
            Assertions.assertThatThrownBy { it.select(e.name, h.name).where(e.name.eq("Bob")).useOuterJoins().firstOrNull() }
        })
    }

    @Test
    fun `test select two columns from two tables`() {
        tm({
            val (salary, street) =
                it.select(e.salary, a.street).where(e.name.eq("Gina").and(a.street).eq("Am Hof")).first()
            assertThat(salary).isEqualTo(31000.0)
            assertThat(street).isEqualTo("Am Hof")
        })
    }

    @Test
    fun `test select two rows with custom mapper`() {
        tm({
            val buffer = mutableListOf<String?>()
            it.select(e.name).where(e.id.lt(11)).orderAsc(e.name).forEachRow({ row ->
                buffer.add(row)
                //there are three rows in the resultset, but we stop fetching after two
                buffer.size != 2
            })
            assertThat(buffer.size).isEqualTo(2)
            assertThat(buffer[0]).isEqualTo("Alice")
            assertThat(buffer[1]).isEqualTo("Bob")
        })
    }

    @Test
    fun `test select, IN whereclause`() {
        tm({ tr ->
            tr.select(e.name).asList()
            val name = tr.select(e.name).where(e.name.within("Eve", "Hardeep").and(e.married).eq(true)).first()
            assertThat(name == "Eve")
        })
    }

    @Test
    fun `test select all without where clause`() {
        tm({ tr ->
            val all = tr.select(Hobby.name).asList()
            assertThat(all).hasSize(3)
        })
    }

    @Test
    fun `test select all with optionally one row`() {
        tm({ tr ->
            val ret = tr.select(e.name).orderAsc(e.name).first()
            assertNotNull(ret)
            assertEquals("Alice", ret)
        })
    }

    @Test
    fun `test left join select person name where hobby is null`() {
        tm({ s ->
            assertThat(s.select(e.name).from(e.leftJoin(h)).where(e.hobbyId.isNull()).orderDesc(e.name).first()).isEqualTo("Hardeep")
        })
    }

    @Test
    fun `test inner join for person based on country`() {
        tm({ s ->
            assertThat(
                s.select(e.name).from(
                    e.innerJoin(EmployeeAddress)
                        .innerJoin(Address)
                        .innerJoin(Country)
                )
                    .where(Country.name.eq("Nederland")).first()
            ).isEqualTo("Eve")

        })
    }

    @Test
    fun `test left join select person name and hobby name without where clause`() {
        tm({ s ->
            val (name, hobby) = s.select(e.name, h.name.nullable).from(e.leftJoin(h)).where(h.name.isNull()).first()
            assertThat(hobby).isNull()
        })
    }

    @Test
    fun `test select the same column twice as a list is OK`() {
        val result =
            tm({ s -> s.select(e.name, e.name).where(e.name.eq("Eve")).first() })
        assertEquals("Eve", result.v1)
        assertEquals("Eve", result.v2)
    }

    @Test
    fun `use the same column in two conditions`() {
        val result =
            tm({ s ->
                s.select(e.name).where(e.salary.gt(38001.0).and(e.salary).lt(39001.0)).first()
            })
        assertEquals("Jasper", result)
    }

    @Test
    fun `limit clause with invalid value returns one`() {
        tm({ s ->
            s.select(e.name).where(e.id.gt(5)).limit(0).first()
        })
    }

    @Test
    fun `Order by clause`() {
        tm({ s ->
            val alice = s.select(e.name).where(e.id.gt(5)).orderAsc(e.name).first()
            assertThat(alice).isEqualTo("Alice")
        })
    }

    @Test
    fun `Multiple order by clauses`() {
        tm({ s ->
            assertThat(s.select(e.name).where(e.id.gt(5)).orderAsc(e.name).orderDesc(e.salary).asList().last())
                .isEqualTo("Jasper")
        })
    }

    @Test
    fun `limit clause with positive value produces proper clause`() {
        tm({ s ->
            s.select(e.name).where(e.id.gt(5)).limit(3).first()
        })
    }


    @Test
    @Order(11)
    fun `test LIKE conditions on strings`() {
        tm { tr ->

            fun checkCount(result: SelectStatementExecutor<*, *>, size: Int) {
                assertEquals(size, result.asList().size)
            }

            checkCount(tr.select(e.id).where(e.name.startsWith("Gi")), 1)

            checkCount(tr.select(e.id).where(e.name.contains("d")), 2)

            checkCount(tr.select(e.id).where(e.name.endsWith("e")), 4)
        }
    }


}
