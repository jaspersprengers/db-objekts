package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalTime


/**
 * Represents a database column whose type is converted from and to a java.sql.Time.
 *
 * @param name The column name in the corresponding database table
 */
class TimeColumn(table: Table, name: String) : NonNullableColumn<LocalTime>(name, table, LocalTime::class.java){
    override val nullable: NullableColumn<LocalTime?> = NullableTimeColumn(table, name)
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let { DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime) =
        statement.setTime(position, DateUtil.toTime(value))
}

class NullableTimeColumn(table: Table, name: String) : NullableColumn<LocalTime?>(name, table, Types.TIME, LocalTime::class.java){
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime?> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let { DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime?) =
        statement.setTime(position, DateUtil.toTime(value!!))

}
