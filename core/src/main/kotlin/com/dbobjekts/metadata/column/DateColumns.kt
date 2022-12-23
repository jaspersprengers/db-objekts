package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
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
class DateColumn(table: Table, name: String) : NonNullableColumn<LocalDate>(name, table, LocalDate::class.java){
    override val nullable: NullableColumn<LocalDate?> = NullableDateColumn(table, name)
    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? =  resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate) =
        statement.setDate(position, DateUtil.toSqlDate(value))
}
class NullableDateColumn(table: Table, name: String) : NullableColumn<LocalDate?>(name, table, Types.DATE, LocalDate::class.java){
    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate?> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? = resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate?) =
        statement.setDate(position, DateUtil.toSqlDate(value!!))

}