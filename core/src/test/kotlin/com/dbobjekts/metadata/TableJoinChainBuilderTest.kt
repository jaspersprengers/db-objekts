package com.dbobjekts.metadata
import com.dbobjekts.integration.h2.Catalogdefinition
import com.dbobjekts.integration.h2.core.*
import com.dbobjekts.integration.h2.hr.Hobby
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


class TableJoinChainBuilderTest {

    fun assertChain(chain: TableJoinChain, sql: String) {
        assertEquals(sql, chain.toSQL().trim())
    }

    val defaultBuilder = TableJoinChainBuilder(Catalogdefinition, Employee, listOf(Address, Country, Employee))


    @Test
    fun `get table pairings`() {
        val pairs = defaultBuilder.getTablePairings(listOf(Address, Country, Employee))
        assertEquals(3, pairs.size)
    }

    @Test
    fun `find Join`() {
        val join = defaultBuilder.findJoin(Pair(Address, Country))!!
        assertEquals("COUNTRY_ID", join.foreignKey.dbName)
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
        assertChain(TableJoinChainBuilder(Catalogdefinition, Employee, listOf(Employee)).build(), "core.EMPLOYEE e")
    }

    @Test
    fun `two joined tables in schema`() {
        assertChain(
            TableJoinChainBuilder(Catalogdefinition, Employee, listOf(Employee, Hobby)).build(),
            "core.EMPLOYEE e left join hr.HOBBY h on e.HOBBY_ID = h.ID"
        )
    }

    @Test
    fun `two tables joined through n-m table`() {
        assertChain(
            TableJoinChainBuilder(Catalogdefinition, Employee, listOf(Employee, Address, Hobby)).build(),
            "core.EMPLOYEE e left join core.EMPLOYEE_ADDRESS ea on e.ID = ea.EMPLOYEE_ID left join hr.HOBBY h on e.HOBBY_ID = h.ID left join core.ADDRESS a on ea.ADDRESS_ID = a.ID"
        )
    }

    @Test
    fun `two unrelated tables will throw`() {
        Assertions.assertThatThrownBy { TableJoinChainBuilder(Catalogdefinition, Employee, listOf(Employee, Country)).build() }
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
