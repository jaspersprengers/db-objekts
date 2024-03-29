package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.util.DateUtil
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDate
import java.time.LocalTime

/**
 * Represents a database column of java.sql.Date type.
 * You are encouraged to use the [DateColumn] or any of the other date/time implementations that use the java.time api wherever possible.
 *
 * @param name The column name in the corresponding database table
 */
open class LegacyDateColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Date>(table,name, Date::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Date? = resultSet.getDate(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Date) =
        statement.setDate(position, value)
}

open class NullableLegacyDateColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Date?>(table,name, Types.DATE, Date::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableLegacyDateColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): Date? = resultSet.getDate(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Date?) =
        statement.setDate(position, value)

}
