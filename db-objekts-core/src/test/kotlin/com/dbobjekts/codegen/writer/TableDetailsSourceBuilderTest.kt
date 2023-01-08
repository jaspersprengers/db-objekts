package com.dbobjekts.codegen.writer

import com.dbobjekts.api.*
import com.dbobjekts.codegen.metadata.DBAutoGeneratedKeyDefinition
import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.metadata.ColumnFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TableDetailsSourceBuilderTest {

    fun column(
        colName: String,
        col: AnyColumn
    ): DBColumnDefinition =
        DBColumnDefinition(SchemaName("core"), TableName("Employee"), ColumnName(colName), col,false,false,  null)

    val id = DBAutoGeneratedKeyDefinition(
        schema = SchemaName("core"),
        table = TableName("Employee"),
        columnName = ColumnName("id"),
        columnType = ColumnFactory.AUTOKEY_LONG
    )
    val name = column("name", ColumnFactory.VARCHAR)
    val age = column("age", ColumnFactory.INTEGER_NIL)
    val hobbies = column("hobbies", ColumnFactory.VARCHAR_NIL)
    val married = column("married", ColumnFactory.BOOLEAN_NIL)
    val dbDef = DBTableDefinition(
        PackageName("public"),
        SchemaName("public"),
        TableName("Employee"),
        "t",
        listOf(id, name, age, hobbies, married)
    )


    @Test
    fun `test conversion`() {
        val bld = TableDetailsSourceBuilder(dbDef)
        val fields = bld.fields
        assert(fields.size == 5)
        fun assertField(
            data: FieldData,
            field: String,
            fieldType: String,
            defaultClause: String,
            nullable: Boolean,
            autoGenPK: Boolean
        ) {
            assertEquals(field, data.field)
            assertEquals(fieldType, data.fieldType)
            assertEquals(defaultClause, data.defaultClause)
            assertEquals(nullable, data.nullable)
            assertEquals(autoGenPK, data.autoGenPK)
        }
        assertField(fields[0], "id", "Long", "", false, true)
        assertField(fields[1], "name", "String", "", false, false)
        assertField(fields[2], "age", "Int?", " = 0", true, false)
        assertField(fields[3], "hobbies", "String?", " = null", true, false)
        assertField(fields[4], "married", "Boolean?", " = false", true, false)
    }

}



