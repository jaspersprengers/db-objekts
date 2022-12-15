package com.dbobjekts.metadata

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyForeignKey
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.metadata.column.IsPrimaryKey
import com.dbobjekts.util.Errors
import com.dbobjekts.util.ValidateDBObjectName

abstract class Table(
    val dbName: String
) : TableOrJoin, SerializableToSQL {

    abstract val columns: List<AnyColumn>

    private lateinit var schema: Schema

    val tableName: TableName = TableName(dbName)

    init {
        if (!ValidateDBObjectName(tableName.value))
            throw IllegalArgumentException("Not a valid table name: " + tableName)
    }

    val foreignKeys: List<AnyForeignKey> by lazy { columns.filter { it is AnyForeignKey }.map { it as AnyForeignKey } }

    fun getForeignKeyToParent(parent: Table): AnyForeignKey? = foreignKeys.find { it.parentColumn.table == parent }

    fun columnByName(column: String): AnyColumn? = columns.find { it.dbName == column }

    fun alias(): String = schema.aliasForTable(this)

    fun schemaAndName(): String = "${schema.dottedName}$tableName"

    fun schemaName(): SchemaName = schema.schemaName

    fun withSchema(schema: Schema) {
        this.schema = schema
    }

    internal fun isInitialized(): Boolean {
        return this::schema.isInitialized
    }

    /**
     * The Table's primary key column, or None if no Column is configured as a primary key.
     */
    fun primaryKey(): IsPrimaryKey? {
        val idCols = columns.filter { it is IsPrimaryKey }
        Errors.require(idCols.size < 2, "table cannot have more than one primary key")
        return if (idCols.isNotEmpty()) idCols[0] as IsPrimaryKey else null
    }

    fun hasForeignKeyTo(source: Table): Boolean = foreignKeys.any { it.parentColumn.table == source }

    fun leftJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createLeftJoin(this, table))


    fun leftJoin(tableToJoin: AnyColumn, keyInCurrentTable: AnyColumn): TableJoinChain {
        validateThatKeyIsForCurrentTable(keyInCurrentTable)
        return TableJoinChain(this).addJoin(JoinFactory.createLeftJoin(keyInCurrentTable, tableToJoin))
    }

    fun rightJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createRightJoin(this, table))


    fun rightJoin(tableToJoin: AnyColumn, keyInCurrentTable: AnyColumn): TableJoinChain {
        validateThatKeyIsForCurrentTable(keyInCurrentTable)
        return TableJoinChain(this).addJoin(JoinFactory.createRightJoin(keyInCurrentTable, tableToJoin))
    }

    fun innerJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createInnerJoin(this, table))


    fun innerJoin(tableToJoin: AnyColumn, keyInCurrentTable: AnyColumn): TableJoinChain {
        validateThatKeyIsForCurrentTable(keyInCurrentTable)
        return TableJoinChain(this).addJoin(JoinFactory.createInnerJoin(keyInCurrentTable, tableToJoin))
    }

    private fun validateThatKeyIsForCurrentTable(left: AnyColumn) {
        if (left.table != this)
            throw IllegalStateException("The second argument of the *join() call is a column in table ${left.table.tableName}, but it should be the column of the table you wish to join, namely ${tableName}.")
    }

    override fun toSQL(): String = "${schema.dottedName}$tableName ${alias()}"

    override fun toString(): String = schemaAndName()

}

