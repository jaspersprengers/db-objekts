package com.dbobjekts.metadata
import com.dbobjekts.testdb.acme.core.Address
import com.dbobjekts.testdb.acme.core.Country
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.metadata.joins.DerivedJoin
import com.dbobjekts.metadata.joins.DerivedJoinChainBuilder
import com.dbobjekts.testdb.acme.CatalogDefinition
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class DerivedJoinChainBuilderTest {

    fun assertChain(chain: DerivedJoin, sql: String) {
        assertEquals(sql, chain.toSQL().trim())
    }

    val defaultBuilder = DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Address, Country, Employee))


    @Test
    fun `get table pairings`() {
        val pairs = defaultBuilder.getTablePairings(listOf(Address, Country, Employee))
        assertEquals(3, pairs.size)
    }

    @Test
    fun `find join for unjoined pair`() {
        assertEquals(EmployeeAddress, defaultBuilder.findJoinTableForUnjoinedPair(Pair(Address, Employee)))
        assertEquals(EmployeeAddress, defaultBuilder.findJoinTableForUnjoinedPair(Pair(Employee, Address)))
    }


    @Test
    fun `one table in schema`() {
        assertChain(DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Employee)).build(), "CORE.EMPLOYEE e")
    }

    @Test
    fun `two joined tables in schema`() {
        assertChain(
            DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Employee, Hobby)).build(),
            "CORE.EMPLOYEE e join HR.HOBBY h on e.HOBBY_ID = h.ID"
        )
    }

    @Test
    fun `two joined tables in schema with left join`() {
        assertChain(
            DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Employee, Hobby), useOuterJoins = true).build(),
            "CORE.EMPLOYEE e left join HR.HOBBY h on e.HOBBY_ID = h.ID"
        )
    }

    @Test
    fun `two tables joined through n-m table`() {
        assertChain(
            DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Employee, Address, Hobby), useOuterJoins = true).build(),
            "CORE.EMPLOYEE e left join CORE.EMPLOYEE_ADDRESS ea on e.ID = ea.EMPLOYEE_ID left join HR.HOBBY h on e.HOBBY_ID = h.ID left join CORE.ADDRESS a on ea.ADDRESS_ID = a.ID"
        )
    }

    @Test
    fun `two unrelated tables will throw`() {
        Assertions.assertThatThrownBy { DerivedJoinChainBuilder(CatalogDefinition, Employee, listOf(Employee, Country)).build() }
            .hasMessage("The following table(s) could not be joined: COUNTRY")
    }

  /*  @Test
    fun `Manually building join chain`() {
        val chain = DerivedJoin(Employee, listOf())
        assert(chain.canJoin(EmployeeAddress))
        assert(chain.canJoin(Hobby))
        assert(!chain.canJoin(Address))
        chain.leftJoin(EmployeeAddress)
        assert(chain.canJoin(Address))
    }*/

}
