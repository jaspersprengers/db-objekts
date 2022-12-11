package com.dbobjekts.codegen.parsers

import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.codegen.metadata.ForeignKeyProperties
import com.dbobjekts.codegen.metadata.TableMetaData
import com.dbobjekts.util.XMLUtil
import org.w3c.dom.Attr
import org.w3c.dom.Element

class LiquibaseSchemaParser(xmlTree: Element,
                            private val schema: SchemaName,
                            private val logger: ProgressLogger) {

    private var changeSets: List<Element>
    private var foreignKeyProperties: List<ForeignKeyProperties>

    init {
        changeSets = getChildElements(xmlTree, "changeSet")
        foreignKeyProperties = createForeignKeyProperties()
    }

    fun parse(): List<TableMetaData> {
        return changeSets.flatMap { getChildElements(it, "createTable") }
            .map { parseTable(it) }
    }


    fun parseTable(node: Element): TableMetaData {
        val table = attr(node, "tableName")
        logger.info("Parsed schema.table $schema.$table")
        val columnNodes = getChildElements(node, "column")
        val columns = columnNodes.map { parseColum(it, table) }
        val foreignKeys = foreignKeyProperties.filter { it.table.value == table && it.schema.value == schema.value }
        return TableMetaData(
            schema = schema,
            tableName = TableName(table),
            columns = columns,
            foreignKeys = foreignKeys
        )
    }

    fun createForeignKeyProperties(): List<ForeignKeyProperties> {
        return changeSets.flatMap {
            getChildElements(it, "addForeignKeyConstraint")
        }.map {
            val baseTable = attr(it, "baseTableName")
            val parentTable = attr(it, "referencedTableName")
            val referencedCatalog = attrOpt(it, "referencedTableCatalogName")
            val referencedSchema = attrOpt(it, "referencedTableSchemaName")
            val parentSchema: String = referencedSchema ?: referencedCatalog ?: schema.value
            ForeignKeyProperties(
                col = ColumnName(attr(it, "baseColumnNames")),
                table = TableName(baseTable),
                schema = schema,
                parentSchema = SchemaName(parentSchema),
                parentColumn = ColumnName(attr(it, "referencedColumnNames")),
                parentTable = TableName(parentTable)
            )
        }
    }

    fun parseColum(columnNode: Element, tableName: String): ColumnMetaData {
        val columnName = attr(columnNode, "name")
        val columnType = attr(columnNode, "type")
        val columnRemarks = attrOpt(columnNode, "remark")
        val isAutoIncrement = attrOpt(columnNode, "autoIncrement") == "true"
        val isPrimaryKey = parseConstraint(columnNode, "primaryKey") == "true"
        val isNullable = parseConstraint(columnNode, "nullable") != "false"
        logger.info("Parsed Column name: $columnName, type: $columnType, auto-incr: $isAutoIncrement, pk: $isPrimaryKey, nullable: $isNullable, remarks: $columnRemarks")
        return ColumnMetaData(
            columnName = ColumnName(columnName),
            columnType = columnType,
            isAutoIncrement = isAutoIncrement,
            isPrimaryKey = isPrimaryKey,
            remarks = columnRemarks,
            nullable = if (isPrimaryKey || isAutoIncrement) false else isNullable
        )
    }

    fun parseConstraint(columnNode: Element, constraint: String): String? {
        val nodeList: List<Element> = getChildElements(columnNode, "constraints")
        return if (nodeList.isEmpty())
            null
        else attrOpt(nodeList[0], constraint)

    }

    fun attr(node: Element, attr: String): String = attrOpt(node, attr) ?: ""

    fun attrOpt(node: Element, attr: String): String? {
        val attrOpt: Attr? = node.getAttributeNode(attr)
        return if (attrOpt == null || !attrOpt.hasChildNodes())
            return null
        else {
            attrOpt.childNodes.item(0)?.textContent
        }
    }

    private fun getChildElements(parent: Element?, childElementName: String): List<Element> =
        XMLUtil.getChildElements(parent, childElementName)


}
