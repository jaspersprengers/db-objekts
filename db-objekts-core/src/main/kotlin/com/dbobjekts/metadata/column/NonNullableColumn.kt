package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.lang.IllegalStateException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class NonNullableColumn<I>(
    name: String,
    table: AnyTable,
    valueClass: Class<*>
) : Column<I>(name, table, valueClass) {
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
        return if (rs.wasNull()) {
            throw IllegalStateException(
                "Cannot return null value for non-nullable column $tableDotName. " +
                        "This happens when the column is selected in an outer join. Use the nullable counterpart of the non-nullable column like so: transaction.select(Employee.name, Department.name.nullable)."
            )
        } else value
    }

    abstract fun getValue(position: Int, resultSet: ResultSet): I?
    abstract fun setValue(position: Int, statement: PreparedStatement, value: I)
    override fun putValue(position: Int, statement: PreparedStatement, value: I?) {
        setValue(position, statement, value ?: throw IllegalStateException("Cannot be null"))
    }

}
