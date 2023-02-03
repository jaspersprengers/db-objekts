package com.dbobjekts.component

import com.dbobjekts.api.Tuple6
import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.testdb.acme.Aliases
import com.dbobjekts.testdb.acme.HasAliases
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Certificate
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SelectStatementComponentTest : HasAliases by Aliases {


    companion object {
        val tm = AcmeDB.transactionManager
        @BeforeAll
        @JvmStatic
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `verify ranges`() {
        assertEquals(7, tm { it.select(em.salary).where(em.salary.notIn(30000.0, 31000.0, 32000.0)).asList().size })
        assertEquals(3, tm { it.select(em.salary).where(em.salary.within(30000.0, 31000.0, 32000.0)).asList().size })
        assertEquals(4, tm { it.select(em.salary).where(em.salary.gt(35000.0)).asList().size })
        assertEquals(5, tm { it.select(em.salary).where(em.salary.ge(35000.0)).asList().size })
        assertEquals(4, tm { it.select(em.salary).where(em.salary.lt(34000.0)).asList().size })
        assertEquals(5, tm { it.select(em.salary).where(em.salary.le(34000.0)).asList().size })
        assertEquals(3, tm { it.select(em.salary).where(em.salary.ge(33000.0).and(em.salary).le(35000.0)).asList().size })

        assertEquals(39000.0, tm { it.select(em.salary).orderDesc(em.salary).first() })
        assertEquals(30000.0, tm { it.select(em.salary).orderAsc(em.salary).first() })
    }


    @Test
    fun `test LIKE conditions on strings`() {
        tm { tr ->
            fun checkCount(result: SelectStatementExecutor<*, *>, size: Int) {
                assertEquals(size, result.asList().size)
            }
            checkCount(tr.select(em.id).where(em.name.startsWith("Gi")), 1)
            checkCount(tr.select(em.id).where(em.name.contains("d")), 2)
            checkCount(tr.select(em.id).where(em.name.endsWith("e")), 4)
        }
    }

    @Test
    fun `select all from employee and hobby`() {
        tm({
            val (employee, hobby) =
                it.select(Employee, ho.name).where(em.name.eq("Eve")).first()
            assertThat(hobby).isEqualTo("Chess")
            assertThat(employee.name).isEqualTo("Eve")
        })
    }

    @Test
    fun `do not use default value for null result in non-nullable column throws`() {
        tm({
            assertThatThrownBy { it.select(em.name, ho.name).where(em.name.eq("Bob")).useOuterJoins().firstOrNull() }
        })
    }

    @Test
    fun `test select two columns from two tables`() {
        tm({
            val (salary, street) =
                it.select(em.salary, ad.street).where(em.name.eq("Gina").and(ad.street).eq("Am Hof")).first()
            assertThat(salary).isEqualTo(31000.0)
            assertThat(street).isEqualTo("Am Hof")
        })
    }

    @Test
    fun `test select, IN whereclause`() {
        tm({ tr ->
            tr.select(em.name).asList()
            val name = tr.select(em.name).where(em.name.within("Eve", "Hardeep").and(em.married).eq(true)).first()
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
            val ret = tr.select(em.name).orderAsc(em.name).first()
            assertNotNull(ret)
            assertEquals("Alice", ret)
        })
    }

    @Test
    fun `test left join select person name where hobby is null`() {
        tm({ s ->
            assertThat(s.select(em.name).from(em.leftJoin(ho)).where(em.hobbyId.isNull()).orderDesc(em.name).first()).isEqualTo("Hardeep")
        })
    }


    // test with outer joins and explicit join chains

    @Test
    fun `use nullable counterpart`() {
        tm({
            val (name, hobby) =
                it.select(em.name, ho.name.nullable).where(em.name.eq("Bob")).useOuterJoins().first()
            assertThat(hobby).isNull()
        })
    }

    @Test
    fun `test inner join for person based on country`() {
        tm({ s ->
            assertThat(
                s.select(em.name).from(
                    em.leftJoin(EmployeeAddress)
                        .leftJoin(Address)
                        .leftJoin(Country)
                )
                    .where(Country.name.eq("Nederland")).first()
            ).isEqualTo("Eve")

        })
    }

    @Test
    fun `test left join select person name and hobby name without where clause`() {
        tm({ s ->
            val (name, hobby) = s.select(em.name, ho.name.nullable).from(em.leftJoin(ho)).where(ho.name.isNull()).first()
            assertThat(hobby).isNull()
        })
    }

    @Test
    fun `join all tables in outer join`() {
        tm { tr ->
            val derivedJoin: Tuple6<EmployeeRow, String, AddressType, String, String?, String?> =
                tr.select(Employee, Address.street, EmployeeAddress.kind, Department.name, Hobby.name.nullable, Certificate.name.nullable)
                .from(Employee
                    .leftJoin(ea)
                    .leftJoin(ad)
                    .leftJoin(ho)
                    .leftJoin(ed)
                    .leftJoin(de)
                    .leftJoin(Certificate))
                .orderAsc(Employee.name).first()
            assertThat(derivedJoin.v1.name).isEqualTo("Alice")

            val manualJoin: Tuple6<EmployeeRow, String, AddressType, String, String?, String?> =
                tr.select(Employee, Address.street, EmployeeAddress.kind, Department.name, Hobby.name.nullable, Certificate.name.nullable)
                    .from(Employee
                        .leftJoin(ea).on(ea.employeeId.eq(em.id))
                        .leftJoin(ad).on(ea.addressId.eq(ad.id))
                        .leftJoin(ho).on(em.hobbyId.eq(ho.id))
                        .leftJoin(ed).on(em.id.eq(ed.employeeId))
                        .leftJoin(de).on(ed.departmentId.eq(de.id))
                        .leftJoin(ce).on(ce.employeeId.eq(em.id)))
                    .orderAsc(Employee.name).first()
            assertThat(manualJoin.v1.name).isEqualTo("Alice")

            val autoJoin = tr.select(Employee, Address.street, EmployeeAddress.kind, Department.name, Hobby.name.nullable, Certificate.name.nullable)
                .orderAsc(Employee.name)
                .useOuterJoins().first()
            assertThat(autoJoin.v1.name).isEqualTo("Alice")
        }
    }

    @Test
    fun `test select the same column twice as a list is OK`() {
        val result =
            tm({ s -> s.select(em.name, em.name).where(em.name.eq("Eve")).first() })
        assertEquals("Eve", result.v1)
        assertEquals("Eve", result.v2)
    }

    @Test
    fun `use the same column in two conditions`() {
        val result =
            tm({ s ->
                s.select(em.name).where(em.salary.gt(38001.0).and(em.salary).lt(39001.0)).first()
            })
        assertEquals("Jasper", result)
    }

    //Test limit and order by
    @Test
    fun `limit clause with invalid value returns one`() {
        tm({ s ->
            s.select(em.name).where(em.id.gt(5)).limit(0).first()
        })
    }

    @Test
    fun `Order by clause`() {
        tm({ tr ->
            val alice = tr.select(em.name).where(em.id.gt(5)).orderAsc(em.name).first()
            assertThat(alice).isEqualTo("Alice")
        })
    }

    @Test
    fun `Multiple order by clauses`() {
        tm({ s ->
            assertThat(s.select(em.name).where(em.id.gt(5)).orderAsc(em.name).orderDesc(em.salary).asList().last())
                .isEqualTo("Jasper")
        })
    }

    @Test
    fun `limit clause with positive value produces proper clause`() {
        tm({ s ->
            s.select(em.name).where(em.id.gt(5)).limit(3).first()
        })
    }



}
