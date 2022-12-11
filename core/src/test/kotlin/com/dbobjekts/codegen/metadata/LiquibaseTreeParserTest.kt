package com.dbobjekts.codegen.metadata

import com.dbobjekts.SchemaName
import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.parsers.LiquibaseSchemaParser
import com.dbobjekts.util.XMLUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class LiquibaseTreeParserTest {

    val tree = """
        <databaseChangeLog>
        <changeSet author="jsprenge (generated)" id="1576823787268-1">
            <createTable tableName="parent">
                <column autoIncrement="true" name="id" type="INT">
                    <constraints primaryKey="true"/>
                </column>
                <column name="kind" type="varchar">
                    <constraints nullable="false"/>
                </column>
            </createTable>
            <createTable tableName="child">
                <column name="id" type="INT" autoIncrement="true">
                    <constraints primaryKey="true"/>
                </column>
                <column name="parent_id" type="INT">
                    <constraints nullable="false"/>
                </column>
            </createTable>
            <addForeignKeyConstraint baseColumnNames="parent_id"
                                     baseTableName="child"
                                     referencedColumnNames="id"
                                     referencedTableName="parent"/>
        </changeSet>
    </databaseChangeLog>
    """.trimIndent()

        @Test
        fun `open and parse`() {
            val parser = LiquibaseSchemaParser(XMLUtil.read(tree), SchemaName("public"), ProgressLogger())
            val tables: List<TableMetaData> = parser.parse()
            assert(tables.size == 2)
            val t1 = tables.first()
            val t2 = tables[1]
            assertEquals("parent", t1.tableName.value)
            assertEquals("child", t2.tableName.value)
            assertEquals("id", t1.columns.first().columnName.value)
            assertEquals("kind", t1.columns[1].columnName.value)

            assertEquals("id", t2.columns[0].columnName.value )
            assertEquals("parent_id", t2.columns[1].columnName.value)
            val fk = t2.foreignKeys.first()
            assertEquals("id", fk.parentColumn.value)
            assertEquals("parent_id", fk.col.value)

        }

}
