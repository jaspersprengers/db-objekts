package com.dbobjekts.metadata

import com.dbobjekts.api.*
import com.dbobjekts.api.exception.DBObjektsException
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.metadata.column.IsGeneratedPrimaryKey
import com.dbobjekts.metadata.joins.DerivedJoin
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.SQLOptions.Companion.ALIAS
import com.dbobjekts.util.Errors
import com.dbobjekts.util.ObjectNameValidator

/**
 * Parent of all the generated [Table] objects that represent the tables in the database and act as metadata for the query engine.
 *
 */
abstract class Table<I>(
    internal val dbName: String
) : Selectable<I> {

    override abstract val columns: List<AnyColumn>

    private lateinit var schema: Schema

    internal val tableName: TableName = TableName(dbName)

    init {
        ObjectNameValidator.validate(tableName.value, "Not a valid table name: " + tableName)
    }

    fun leftJoin(table: AnyTable): DerivedJoin = DerivedJoin(this).join(table, JoinType.LEFT)
    fun innerJoin(table: AnyTable): DerivedJoin = DerivedJoin(this).join(table, JoinType.INNER)
    fun rightJoin(table: AnyTable): DerivedJoin = DerivedJoin(this).join(table, JoinType.RIGHT)

    internal val foreignKeys: List<IsForeignKey<*, *>> by lazy {
        columns.filter { it is IsForeignKey<*, *> }.map { it as IsForeignKey<*, *> }
    }

    internal fun getForeignKeyToParent(parent: AnyTable): IsForeignKey<*, *>? = foreignKeys.find { it.parentColumn.table == parent }

    internal fun getForeignKeysToParent(parent: AnyTable): List<IsForeignKey<*, *>> = foreignKeys.filter { it.parentColumn.table == parent }

    internal fun columnByName(column: String): AnyColumn? = columns.find { it.nameInTable.equals(column, true) }

    internal open fun alias(): String = ensureSchema().aliasForTable(this)

    internal fun schemaAndName(): String = "${ensureSchema().dottedName}$tableName"

    internal fun schemaName(): SchemaName = ensureSchema().schemaName

    internal fun withSchema(schema: Schema) {
        this.schema = schema
    }

    internal fun ensureSchema(): Schema {
        if (!this::schema.isInitialized)
            throw DBObjektsException(
                "Table $dbName is not associated with a Schema yet. " +
                        "This typically happens when it does not belong to the Catalog associated with the current TransactionManager or when you have not provided a Catalog when building the TransactionManager. You must provide a Catalog in order to use the metadata objects in q ueries."
            )
        return schema
    }

    internal fun isInitialized(): Boolean {
        return this::schema.isInitialized
    }

    /**
     * The Table's primary key column, or None if no Column is configured as a primary key.
     */
    internal fun primaryKey(): IsGeneratedPrimaryKey? {
        val idCols = columns.filter { it is IsGeneratedPrimaryKey }
        Errors.require(idCols.size < 2, "table cannot have more than one primary key")
        return if (idCols.isNotEmpty()) idCols[0] as IsGeneratedPrimaryKey else null
    }

    /**
     * @return the schema and table, e.g. hr.certificate
     */
    internal fun toSQL(options: SQLOptions = ALIAS): String =
        if (options.includeAlias) "${schema.dottedName}$tableName ${alias()}" else "${schema.dottedName}$tableName"

    override fun toString(): String = toSQL()

    internal fun serialize(): String = "$dbName columns:[${columns.map { it.toString() }.joinToString(",")}]"
}

