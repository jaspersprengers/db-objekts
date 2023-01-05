package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class NullableColumn<I>(
    name: String = "dummy",
    table: AnyTable = DefaultTable,
    internal val sqlType: Int,
    valueClass: Class<*>,
    aggregateType: AggregateType?
) : Column<I>(name, table, valueClass, aggregateType) {
    override fun create(value: I?): ColumnAndValue<I> = NullableColumnAndValue(this, value)

    abstract fun distinct(): NullableColumn<I>

    internal fun setNull(): ColumnAndValue<I> = NullableColumnAndValue(this, null)

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
