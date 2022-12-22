package com.dbobjekts.metadata

import com.dbobjekts.api.*
import com.dbobjekts.metadata.column.IsPrimaryKey
import com.dbobjekts.metadata.joins.JoinFactory
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.util.Errors
import com.dbobjekts.util.ValidateDBObjectName

abstract class Table(
    internal val dbName: String
) : TableOrJoin, SerializableToSQL {

    abstract val columns: List<AnyColumn>

    private lateinit var schema: Schema

    internal val tableName: TableName = TableName(dbName)

    init {
        if (!ValidateDBObjectName(tableName.value))
            throw IllegalArgumentException("Not a valid table name: " + tableName)
    }

    internal val foreignKeys: List<AnyForeignKey> by lazy { columns.filter { it is AnyForeignKey }.map { it as AnyForeignKey } }

    internal fun getForeignKeyToParent(parent: Table): AnyForeignKey? = foreignKeys.find { it.parentColumn.table == parent }

    internal fun columnByName(column: String): AnyColumn? = columns.find { it.nameInTable == column }

    internal fun alias(): String = schema.aliasForTable(this)

    internal fun schemaAndName(): String = "${schema.dottedName}$tableName"

    internal  fun schemaName(): SchemaName = schema.schemaName

    internal fun withSchema(schema: Schema) {
        this.schema = schema
    }

    internal fun isInitialized(): Boolean {
        return this::schema.isInitialized
    }

    /**
     * The Table's primary key column, or None if no Column is configured as a primary key.
     */
    internal fun primaryKey(): IsPrimaryKey? {
        val idCols = columns.filter { it is IsPrimaryKey }
        Errors.require(idCols.size < 2, "table cannot have more than one primary key")
        return if (idCols.isNotEmpty()) idCols[0] as IsPrimaryKey else null
    }

    fun leftJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createLeftJoin(this, table))

    fun rightJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createRightJoin(this, table))

    fun innerJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createInnerJoin(this, table))

    override fun toSQL(): String = "${schema.dottedName}$tableName ${alias()}"

    override fun toString(): String = toSQL()

}

