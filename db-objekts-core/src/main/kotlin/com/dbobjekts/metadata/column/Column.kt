package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.And
import com.dbobjekts.statement.whereclause.ConditionFactory
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.ValidateDBObjectName
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Base of objects representing the column of a database table. ColumnClasses are associated with an sqlType indicating to the JDBC driver how to map the database type to a Kotlin type.
 * The correct sqlType is particularly relevant when setting null values.
 *
 * @param nameInTable    the column name in the corresponding database table
 * @tparam I the Kotlin type that the db data type maps to
 */
abstract class Column<I>(
    val nameInTable: String,
    val table: AnyTable,
    internal val valueClass: Class<*>,
    val aggregateType: AggregateType?
) : ConditionFactory<I, SubClause>, Selectable<I> {
    init {
        if (!ValidateDBObjectName(nameInTable))
            throw DBObjektsException("Not a valid column name: '$nameInTable'")
    }

    override fun toString() = "${table.tableName}.$nameInTable"

    internal abstract fun create(value: I?): ColumnAndValue<I>

    override fun createSubClause(symbol: String, values: List<I>?, secondColumn: Column<I>?): SubClause {
        val clause = SubClause()
        clause.addCondition(this, And, symbol, values, secondColumn)
        return clause
    }

    override val columns: List<AnyColumn> = listOf(this)

    @Suppress("UNCHECKED_CAST")
    override fun toValue(values: List<Any?>) = values.get(0) as I

    internal abstract fun retrieveValue(position: Int, rs: ResultSet): I?

    internal abstract fun putValue(position: Int, statement: PreparedStatement, value: I?)

    internal val tableDotName: String = table.tableName.value + "." + nameInTable

    fun count(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT)

    fun countDistinct(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT_DISTINCT)

    internal fun aliasDotName(): String = table.alias() + "." + nameInTable

    internal fun forSelect(): String =  aggregateType?.forColumn(aliasDotName()) ?: aliasDotName()

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

