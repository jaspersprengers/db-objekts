package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents a database column whose type is converted from and to a java.util.Date.
 *
 * @param name The column name in the corresponding database table
 */
open class DateColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<LocalDate>(table,name, LocalDate::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? = resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate) =
        statement.setDate(position, DateUtil.toSqlDate(value))
}

open class NullableDateColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<LocalDate?>(table,name, Types.DATE, LocalDate::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate?> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? = resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate?) =
        statement.setDate(position, DateUtil.toSqlDate(value!!))

}
