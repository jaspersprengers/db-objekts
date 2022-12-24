package com.dbobjekts.codegen

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.metadata.DBForeignKeyDefinition
import com.dbobjekts.codegen.metadata.DBSchemaDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.vendors.Vendors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class ValidateForeignKeyConstraintsTest {

    val pkg = PackageName("com.acme")
    val hr = SchemaName("hr")
    val finance = SchemaName("fin")
    val peopleName = TableName("people")
    val hobbyName = TableName("hobbies")
    val wagesName = TableName("wages")

    val hobbiesToPeople =
        DBForeignKeyDefinition(hobbyName, ColumnName("people_id"), ColumnFactory.FOREIGN_KEY_LONG, hr, peopleName, ColumnName("id"))
    val wagesToPeople =
        DBForeignKeyDefinition(wagesName, ColumnName("people_id"), ColumnFactory.FOREIGN_KEY_LONG, hr, peopleName, ColumnName("id"))

    val peopleTable: DBTableDefinition = DBTableDefinition(pkg, hr, peopleName, "p", listOf())
    val hobbiesTable: DBTableDefinition = DBTableDefinition(pkg, hr, hobbyName, "h", listOf(hobbiesToPeople))
    val wagesTable: DBTableDefinition = DBTableDefinition(pkg, finance, wagesName, "w", listOf(wagesToPeople))


    @Test
    fun `When all tables are included validation is ok`() {
        val hrSchema = DBSchemaDefinition(pkg, hr, listOf(peopleTable, hobbiesTable), listOf())
        val financeSchema = DBSchemaDefinition(pkg, SchemaName("hr"), listOf(wagesTable), listOf())
        val catalog = DBCatalogDefinition(pkg, Vendors.H2, listOf(hrSchema, financeSchema), "CatalogDefinition")
        assertTrue(ValidateForeignKeyConstraints(catalog))
    }

    @Test
    fun `When both wages and hobbies are absent, validation is ok`() {
        val hrSchema = DBSchemaDefinition(pkg, hr, listOf(peopleTable, hobbiesTable), listOf(hobbiesTable))
        val financeSchema = DBSchemaDefinition(pkg, finance, listOf(wagesTable), listOf(wagesTable))
        val catalog = DBCatalogDefinition(pkg, Vendors.H2, listOf(hrSchema, financeSchema), "CatalogDefinition")
        assertTrue(ValidateForeignKeyConstraints(catalog))
    }

    @Test
    fun `When people table is missing, validation fails`() {
        val hrSchema = DBSchemaDefinition(pkg, hr, listOf(peopleTable, hobbiesTable), listOf(peopleTable))
        val financeSchema = DBSchemaDefinition(pkg, finance, listOf(wagesTable), listOf())
        val catalog = DBCatalogDefinition(pkg, Vendors.H2, listOf(hrSchema, financeSchema), "CatalogDefinition")
        assertFalse(ValidateForeignKeyConstraints(catalog))
        val (p1, p2) = ValidateForeignKeyConstraints.reportMissing(catalog).first()
        assertEquals("hr.hobbies.people_id", p1)
        assertEquals("hr.people.id", p2)
    }

}
