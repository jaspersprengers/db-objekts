package com.dbobjekts.metadata.column

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.*

import com.dbobjekts.metadata.Table
import com.dbobjekts.util.DateUtil
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a java.sql.Time.
 *
 * @param name The column name in the corresponding database table
 */
class TimeColumn(table: Table, name: String) : NonNullableColumn<LocalTime>(name, table, LocalTime::class.java){
    override val nullable: NullableColumn<LocalTime?> = NullableTimeColumn(table, name)
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let {DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime) =
        statement.setTime(position, DateUtil.toTime(value))

}

class NullableTimeColumn(table: Table, name: String) : NullableColumn<LocalTime?>(name, table, Types.TIME, LocalTime::class.java){
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime?> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let {DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime?) =
        statement.setTime(position, DateUtil.toTime(value!!))

}

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class TimeStampColumn(table: Table, name: String) : NonNullableColumn<Instant>(name, table, Instant::class.java){
    override val nullable: NullableColumn<Instant?> = NullableTimeStampColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Instant? = resultSet.getTimestamp(position)?.let {DateUtil.toInstant(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
}

class NullableTimeStampColumn(table: Table, name: String) : NullableColumn<Instant?>(name, table, Types.TIMESTAMP, Instant::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Instant? =resultSet.getTimestamp(position)?.let {DateUtil.toInstant(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))

}

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class OffsetDateTimeColumn(table: Table, name: String) : NonNullableColumn<OffsetDateTime>(name, table, OffsetDateTime::class.java){
    override val nullable: NullableColumn<OffsetDateTime?> = NullableOffsetDateTimeColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime) {
        statement.setObject(position, value)
    }
}

class NullableOffsetDateTimeColumn(table: Table, name: String) : NullableColumn<OffsetDateTime?>(name, table, Types.TIMESTAMP, OffsetDateTime::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime?) {
        statement.setObject(position, value)
    }
}

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

class DateTimeColumn(table: Table, name: String) : NonNullableColumn<LocalDateTime>(name, table, LocalDateTime::class.java){
    override val nullable: NullableColumn<LocalDateTime?> = NullableDateTimeColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
}
class NullableDateTimeColumn(table: Table, name: String) : NullableColumn<LocalDateTime?>(name, table, Types.TIMESTAMP, LocalDateTime::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))
}
