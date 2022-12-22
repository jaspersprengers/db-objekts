package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.lang.IllegalStateException
import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class NonNullableColumn<I>(
    name: String,
    table: Table,
    valueClass: Class<*>
) : Column<I>(name, table, valueClass) {
    abstract val nullable: NullableColumn<I?>
    override fun create(value: I?): ColumnAndValue<I> = NonNullableColumnAndValue(
        this, value ?: throw IllegalArgumentException("Value cannot be null in non-null column")
    )

    fun of(value: I): ColumnAndValue<I> = create(value)
    override fun retrieveValue(
        position: Int,
        rs: ResultSet,
        useDefaultValuesInOuterJoins: Boolean
    ): I? {
        val value = getValue(position, rs)
        return if (rs.wasNull()) {
            if (useDefaultValuesInOuterJoins) defaultValue() else throw IllegalStateException("Cannot return null value for non-nullable column $tableDotName. " +
                    "This happens when the column is selected in an outer join. Either use the nullable counterpart of the non-nullable column like so: transaction.select(Employee.name, Department.name.nullable)." +
                    "Another strategy is to invoke useOuterJoins(true) on the select statement")
        } else value
    }

    abstract fun getValue(position: Int, resultSet: ResultSet): I?
    abstract fun setValue(position: Int, statement: PreparedStatement, value: I)
    override fun putValue(position: Int, statement: PreparedStatement, value: I?) {
        setValue(position, statement, value ?: throw IllegalStateException("Cannot be null"))
    }

    open fun defaultValue(): I =
        throw IllegalStateException("Retrieved null value for non-nullable column '$tableDotName' and no default value is available.")

}
