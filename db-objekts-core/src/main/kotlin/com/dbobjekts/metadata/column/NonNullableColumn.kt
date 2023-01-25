package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementExecutionException
import com.dbobjekts.metadata.ColumnFactory
import java.lang.IllegalStateException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class NonNullableColumn<I>(
    table: AnyTable,
    name: String,
    valueClass: Class<*>,
    aggregateType: AggregateType?
) : Column<I>(table,name, valueClass, aggregateType) {

    open val nullable: NullableColumn<I?> by lazy {
        ColumnFactory.nullableColumn(this)
    }

    override fun create(value: I?): ColumnAndValue<I> = NonNullableColumnAndValue(
        this, value ?: throw StatementExecutionException("Value cannot be null in non-null column")
    )

    @Suppress("UNCHECKED_CAST")
    open fun distinct(): NonNullableColumn<I> = ColumnFactory.distinctClone(this) as NonNullableColumn<I>

    override fun retrieveValue(
        position: Int,
        rs: ResultSet
    ): I? {
        val value = getValue(position, rs)
        return if (rs.wasNull()) {
            throw StatementExecutionException(
                "Cannot return null value for non-nullable column $tableDotName. " +
                        "This happens when the column is selected in an outer join. Use the nullable counterpart of the non-nullable column like so: transaction.select(Employee.name, Department.name.nullable)."
            )
        } else value
    }

    abstract fun getValue(position: Int, resultSet: ResultSet): I?
    abstract fun setValue(position: Int, statement: PreparedStatement, value: I)
    override fun putValue(position: Int, statement: PreparedStatement, value: I?) {
        setValue(position, statement, value ?: throw StatementExecutionException("Cannot be null"))
    }

}
