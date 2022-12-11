package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.And
import com.dbobjekts.statement.whereclause.ConditionFactory
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.ValidateDBObjectName
import java.lang.IllegalStateException
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Base of objects representing the column of a database table. Columns are associated with an sqlType indicating to the JDBC driver how to map the database type to a Scala type. The correct sqlType is particularly relevant when setting and reading null values.
 *
 * @param dbName    the column name in the corresponding database table
 * @param sqlType one of java.sql.Types
 * @tparam I the Scala type that the db data type maps to
 */
abstract class Column<I>(
    val dbName: String,
    val table: Table
) : ConditionFactory<I, SubClause> {
    init {
        if (!ValidateDBObjectName(dbName))
            throw IllegalArgumentException("Not a valid column name: '$dbName'")
    }

    override fun toString() = "${table.tableName}.$dbName"

    abstract fun create(value: I?): ColumnAndValue<I>

    override val notEqualsOperator = " <>"

    override fun createSubClause(symbol: String, values: List<I>?, secondColumn: Column<I>?): SubClause {
        val clause = SubClause()
        clause.addCondition(this, And, symbol, values, secondColumn)
        return clause
    }

    abstract val columnClass: Class<*>

    abstract val valueClass: Class<*>

    abstract fun retrieveValue(position: Int, rs: ResultSet): I?

    abstract fun putValue(position: Int, statement: PreparedStatement, value: I?)

    val tableDotName: String = table.tableName.value + "." + dbName

    fun aliasDotName(): String = table.alias() + "." + dbName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Column<*>) return false
        if (dbName != other.dbName) return false
        if (table != other.table) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dbName.hashCode()
        result = 31 * result + table.hashCode()
        return result
    }

}

abstract class NonNullableColumn<I>(
    name: String,
    table: Table
) : Column<I>(name, table) {
    abstract val nullable: NullableColumn<I?>
    override fun create(value: I?): ColumnAndValue<I> = NonNullableColumnAndValue(
        this, value ?: throw IllegalArgumentException("Value cannot be null in non-null column")
    )

    fun of(value: I): ColumnAndValue<I> = create(value)
    override fun retrieveValue(
        position: Int,
        rs: ResultSet
    ): I? {
        val value = getValue(position, rs)
        return if (rs.wasNull()) defaultValue() else value
    }

    abstract fun getValue(position: Int, resultSet: ResultSet): I?
    abstract fun setValue(position: Int, statement: PreparedStatement, value: I)
    override fun putValue(position: Int, statement: PreparedStatement, value: I?) {
        setValue(position, statement, value ?: throw IllegalStateException("Cannot be null"))
    }

    open fun defaultValue(): I = throw IllegalStateException("Retrieved null value for non-nullable column, or no default value available.")

}

abstract class NullableColumn<I>(
    name: String = "dummy",
    table: Table = DefaultTable,
    val sqlType: Int
) : Column<I>(name, table) {
    override fun create(value: I?): ColumnAndValue<I> = NullableColumnAndValue(this, value)

    fun setNull(): ColumnAndValue<I> = NullableColumnAndValue(this, null)

    override fun retrieveValue(position: Int, rs: ResultSet): I? {
        val value = getValue(position, rs)
        return if (rs.wasNull())
            null else value
    }

    abstract fun getValue(position: Int, resultSet: ResultSet): I?
    abstract fun setValue(position: Int, statement: PreparedStatement, value: I)
    override fun putValue(position: Int, statement: PreparedStatement, value: I?) {
        if (value == null)
            statement.setNull(position, sqlType)
        else
            setValue(position, statement, value)
    }
}

