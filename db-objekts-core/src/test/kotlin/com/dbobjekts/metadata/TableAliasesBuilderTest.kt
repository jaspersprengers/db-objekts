package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.testdb.acme.core.EmployeeAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TableAliasesBuilderTest {

    val s1 = Schema("s1", listOf(Members, Address, MemberAddress))
    val s2 = Schema("s2", listOf(Members2, Address2, AddressDomain))
    val s3 = Schema("s3", listOf(AssetSize, AddressSign, FirstUnitName))
    val c = Catalog(1, "vendor", listOf(s1, s2, s3))

    fun doAssert(tables: List<String>, tableToTest: String, field: String) {
        val map = TableAliasesBuilder()
            .addSchemaAndTables(SchemaName("public"), tables.map { TableName(it) })
            .build()
        assertEquals(field, map.aliasForSchemaAndTable(SchemaName("public"), TableName(tableToTest)))
    }

    @Test
    fun `foreign key`() {
        assertEquals(2, EmployeeAddress.foreignKeys.size)
        assertEquals("EMPLOYEE_ID", EmployeeAddress.foreignKeys[0].column.nameInTable)
        assertEquals("ADDRESS_ID", EmployeeAddress.foreignKeys[1].column.nameInTable)

        assertEquals("EMPLOYEE.ID", EmployeeAddress.foreignKeys[0].parentColumn.tableDotName)
        assertEquals("ADDRESS.ID", EmployeeAddress.foreignKeys[1].parentColumn.tableDotName)
    }

    @Test
    fun `three tables names, no conflicts`() {
        fun myAssert(tbl: String, alias: String) = doAssert(listOf("members", "address", "member_address"), tbl, alias)
        myAssert("members", "me")
        myAssert("address", "ad")
        myAssert("member_address", "ma")
    }

    @Test
    fun `three tables, no conflicts`() {
        assertEquals("me", Members.alias())
        assertEquals("ad", Address.alias())
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
        assertEquals("me", Members.alias())
        assertEquals("ad", Address.alias())
        assertEquals("me1", Members2.alias())
        assertEquals("ad1", Address2.alias())
        assertEquals("ad2", AddressDomain.alias())
    }

    @Test
    fun `reserved words use increment`(){
        assertEquals("as1", AddressSign.alias())
        assertEquals("as2", AssetSize.alias())
        assertEquals("fun1", FirstUnitName.alias())
    }

    object Members : Table<String>("members") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object Address : Table<String>("address") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object MemberAddress : Table<String>("member_address") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object Members2 : Table<String>("members") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object Address2 : Table<String>("address") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object AddressDomain : Table<String>("address_domain") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    //resolves to 'as', which is a reserved word, hence becomes as1
    object AssetSize : Table<String>("asset_size") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object AddressSign : Table<String>("address_sign") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }

    object FirstUnitName : Table<String>("first_unit_name") {
        override val columns = listOf<AnyColumn>()
        override fun toValue(values: List<Any?>): String = ""
    }
}



