package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TableAliasesBuilderTest {

    val s1 = Schema("s1", listOf(Members, Address, MemberAddress))
    val s2 = Schema("s2", listOf(Members2, Address2))
    val c = Catalog("vendor", listOf(s1, s2))

    fun doAssert(tables: List<String>, tableToTest: String, field: String) {
        val map = TableAliasesBuilder()
            .addSchemaAndTables(SchemaName("public"), tables.map { TableName(it) })
            .build()
        assertEquals(field, map.aliasForSchemaAndTable(SchemaName("public"), TableName(tableToTest)))
    }

    @Test
    fun `foreign key`() {
        assertEquals(2, EmployeeAddress.foreignKeys.size)
        assertEquals("EMPLOYEE_ID", EmployeeAddress.foreignKeys[0].nameInTable)
        assertEquals("ADDRESS_ID", EmployeeAddress.foreignKeys[1].nameInTable)

        assertEquals("EMPLOYEE.ID", EmployeeAddress.foreignKeys[0].parentColumn.tableDotName)
        assertEquals("ADDRESS.ID", EmployeeAddress.foreignKeys[1].parentColumn.tableDotName)
    }

    @Test
    fun `three tables names, no conflicts`() {
        fun myAssert(tbl: String, alias: String) = doAssert(listOf("members", "address", "member_address"), tbl, alias)
        myAssert("members", "m")
        myAssert("address", "a")
        myAssert("member_address", "ma")
    }

    @Test
    fun `three tables, no conflicts`() {
        assertEquals("m", Members.alias())
        assertEquals("a", Address.alias())
        assertEquals("ma", MemberAddress.alias())
    }

    @Test
    fun `three tables names with one conflicts uses increment`() {
        fun myAssert(tbl: String, alias: String) = doAssert(listOf("member_address_type", "member_actions", "member_address"), tbl, alias)
        myAssert("member_actions", "ma")
        myAssert("member_address", "ma1")
        myAssert("member_address_type", "mat")
    }

    @Test
    fun `Three tables with two increments`() {
        fun myAssert(tbl: String, alias: String) = doAssert(listOf("all_types", "all_tuples", "all_traits"), tbl, alias)

        myAssert("all_traits", "at")
        myAssert("all_tuples", "at1")
        myAssert("all_types", "at2")
    }

    @Test
    fun `two tables with same names in different schemas`() {
        assertEquals("m", Members.alias())
        assertEquals("a", Address.alias())
        assertEquals("m1", Members2.alias())
        assertEquals("a1", Address2.alias())
    }

    object Members : Table("members") {
        override val columns = listOf<AnyColumn>()
    }

    object Address : Table("address") {
        override val columns = listOf<AnyColumn>()
    }

    object MemberAddress : Table("member_address") {
        override val columns = listOf<AnyColumn>()
    }

    object Members2 : Table("members") {
        override val columns = listOf<AnyColumn>()
    }

    object Address2 : Table("address") {
        override val columns = listOf<AnyColumn>()
    }
}



