package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.And
import com.dbobjekts.statement.whereclause.ConditionFactory
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.ValidateDBObjectName
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Base of objects representing the column of a database table. Columns are associated with an sqlType indicating to the JDBC driver how to map the database type to a Kotlin type.
 * The correct sqlType is particularly relevant when setting null values.
 *
 * @param nameInTable    the column name in the corresponding database table
 * @tparam I the Kotlin type that the db data type maps to
 */
abstract class Column<I>(
    val nameInTable: String,
    val table: Table,
    internal val valueClass: Class<*>
) : ConditionFactory<I, SubClause> {
    init {
        if (!ValidateDBObjectName(nameInTable))
            throw IllegalArgumentException("Not a valid column name: '$nameInTable'")
    }

    override fun toString() = "${table.tableName}.$nameInTable"

    internal abstract fun create(value: I?): ColumnAndValue<I>

    override fun createSubClause(symbol: String, values: List<I>?, secondColumn: Column<I>?): SubClause {
        val clause = SubClause()
        clause.addCondition(this, And, symbol, values, secondColumn)
        return clause
    }

    internal abstract fun retrieveValue(position: Int, rs: ResultSet, useDefaultValuesInOuterJoins: Boolean): I?

    internal abstract fun putValue(position: Int, statement: PreparedStatement, value: I?)

    internal val tableDotName: String = table.tableName.value + "." + nameInTable

    internal fun aliasDotName(): String = table.alias() + "." + nameInTable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Column<*>) return false
        if (nameInTable != other.nameInTable) return false
        if (table != other.table) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameInTable.hashCode()
        result = 31 * result + table.hashCode()
        return result
    }

    internal fun serialize(): String = "$nameInTable ${javaClass.simpleName}"

}

