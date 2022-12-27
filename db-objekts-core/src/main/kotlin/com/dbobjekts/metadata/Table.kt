package com.dbobjekts.metadata

import com.dbobjekts.api.*
import com.dbobjekts.metadata.column.IsPrimaryKey
import com.dbobjekts.metadata.joins.JoinFactory
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.util.Errors
import com.dbobjekts.util.ValidateDBObjectName
import java.lang.IllegalStateException

/**
 * Parent of all the generated [Table] objects that represent the tables in the database and act as metadata for the query engine.
 *
 */
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

    internal fun columnByName(column: String): AnyColumn? = columns.find { it.nameInTable.equals(column, true) }

    internal fun alias(): String = ensureSchema().aliasForTable(this)

    internal fun schemaAndName(): String = "${ensureSchema().dottedName}$tableName"

    internal fun schemaName(): SchemaName = ensureSchema().schemaName

    internal fun withSchema(schema: Schema) {
        this.schema = schema
    }

    internal fun ensureSchema(): Schema {
        if (!this::schema.isInitialized)
            throw IllegalStateException(
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
    internal fun primaryKey(): IsPrimaryKey? {
        val idCols = columns.filter { it is IsPrimaryKey }
        Errors.require(idCols.size < 2, "table cannot have more than one primary key")
        return if (idCols.isNotEmpty()) idCols[0] as IsPrimaryKey else null
    }

    /**
     * Creates a left outer join with the table provided. Example:
     * ```kotlin
     *  Employee.leftJoin(Hobby)
     * ```
     * Will produce sql like the following:
     * ```sql
     *  employee e left join hobby h on e.hobby_id = h.id
     * ```
     */
    fun leftJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createLeftJoin(this, table))

    /**
     * Creates a right outer join with the table provided. Example:
     * ```kotlin
     *  Employee.rightJoin(Hobby)
     * ```
     * Will produce sql like the following:
     * ```sql
     *  employee e right join hobby h on e.hobby_id = h.id
     * ```
     */
    fun rightJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createRightJoin(this, table))

    /**
     * Creates an inner join with the table provided. Example:
     * ```kotlin
     *  Employee.rightJoin(Hobby)
     * ```
     * Will produce sql like the following:
     * ```sql
     *  employee e join hobby h on e.hobby_id = h.id
     * ```
     */
    fun innerJoin(table: Table): TableJoinChain =
        TableJoinChain(this).addJoin(JoinFactory.createInnerJoin(this, table))

    /**
     * @return the schema and table, e.g. hr.certificate
     */
    override fun toSQL(): String = "${schema.dottedName}$tableName ${alias()}"

    override fun toString(): String = toSQL()

    internal fun serialize(): String = "$dbName columns:[${columns.map { it.toString() }.joinToString(",")}]"
}

