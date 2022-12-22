package com.dbobjekts.metadata
import com.dbobjekts.integration.h2.TestCatalog
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Country
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.core.EmployeeAddress
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.metadata.joins.TableJoinChainBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


class TableJoinChainBuilderTest {

    fun assertChain(chain: TableJoinChain, sql: String) {
        assertEquals(sql, chain.toSQL().trim())
    }

    val defaultBuilder = TableJoinChainBuilder(TestCatalog, Employee, listOf(Address, Country, Employee))


    @Test
    fun `get table pairings`() {
        val pairs = defaultBuilder.getTablePairings(listOf(Address, Country, Employee))
        assertEquals(3, pairs.size)
    }

    @Test
    fun `find Join`() {
        val join = defaultBuilder.findJoin(Pair(Address, Country))!!
        assertEquals("COUNTRY_ID", join.foreignKey.nameInTable)
    }

    @Test
    fun `no join`() {
        val join = defaultBuilder.findJoin(Pair(Address, Employee))
        assertNull(join)
    }

    @Test
    fun `create join`() {
        val join = defaultBuilder.createJoin(Address, Country)
        assertNull(join)
    }

    @Test
    fun `find join for unjoined pair`() {
        assertEquals(EmployeeAddress, defaultBuilder.findJoinTableForUnjoinedPair(Pair(Address, Employee)))
        assertEquals(EmployeeAddress, defaultBuilder.findJoinTableForUnjoinedPair(Pair(Employee, Address)))
    }


    @Test
    fun `one table in schema`() {
        assertChain(TableJoinChainBuilder(TestCatalog, Employee, listOf(Employee)).build(), "CORE.EMPLOYEE e")
    }

    @Test
    fun `two joined tables in schema`() {
        assertChain(
            TableJoinChainBuilder(TestCatalog, Employee, listOf(Employee, Hobby)).build(),
            "CORE.EMPLOYEE e join HR.HOBBY h on e.HOBBY_ID = h.ID"
        )
    }

    @Test
    fun `two joined tables in schema with left join`() {
        assertChain(
            TableJoinChainBuilder(TestCatalog, Employee, listOf(Employee, Hobby), useOuterJoins = true).build(),
            "CORE.EMPLOYEE e left join HR.HOBBY h on e.HOBBY_ID = h.ID"
        )
    }

    @Test
    fun `two tables joined through n-m table`() {
        assertChain(
            TableJoinChainBuilder(TestCatalog, Employee, listOf(Employee, Address, Hobby), useOuterJoins = true).build(),
            "CORE.EMPLOYEE e left join CORE.EMPLOYEE_ADDRESS ea on e.ID = ea.EMPLOYEE_ID left join HR.HOBBY h on e.HOBBY_ID = h.ID left join CORE.ADDRESS a on ea.ADDRESS_ID = a.ID"
        )
    }

    @Test
    fun `two unrelated tables will throw`() {
        Assertions.assertThatThrownBy { TableJoinChainBuilder(TestCatalog, Employee, listOf(Employee, Country)).build() }
            .hasMessage("The following table(s) could not be joined: COUNTRY")
    }

    @Test
    fun `Manually building join chain`() {
        val chain = TableJoinChain(Employee)
        assert(chain.canJoin(EmployeeAddress))
        assert(chain.canJoin(Hobby))
        assert(!chain.canJoin(Address))
        chain.leftJoin(EmployeeAddress)
        assert(chain.canJoin(Address))
    }

}
