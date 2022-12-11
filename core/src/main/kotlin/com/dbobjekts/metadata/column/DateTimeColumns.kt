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
class TimeColumn(table: Table, name: String) : NonNullableColumn<LocalTime>(name, table){
    override val nullable: NullableColumn<LocalTime?> = NullableTimeColumn(table, name)
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let {DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime) =
        statement.setTime(position, DateUtil.toTime(value))

    override val columnClass: Class<*> = TimeColumn::class.java
    override val valueClass: Class<*> = LocalTime::class.java
}

class NullableTimeColumn(table: Table, name: String) : NullableColumn<LocalTime?>(name, table, Types.TIME){
    fun of(hour: Int, minutes: Int, seconds: Int): ColumnAndValue<LocalTime?> = create(LocalTime.of(hour, minutes, seconds))
    override fun getValue(position: Int, resultSet: ResultSet): LocalTime? = resultSet.getTime(position)?.let {DateUtil.toLocalTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalTime?) =
        statement.setTime(position, DateUtil.toTime(value!!))

    override val columnClass: Class<*> = NullableTimeColumn::class.java
    override val valueClass: Class<*> = LocalTime::class.java
}

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class TimeStampColumn(table: Table, name: String) : NonNullableColumn<Instant>(name, table){
    override val nullable: NullableColumn<Instant?> = NullableTimeStampColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Instant? = resultSet.getTimestamp(position)?.let {DateUtil.toInstant(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
    override val columnClass: Class<*> = TimeStampColumn::class.java
    override val valueClass: Class<*> = Instant::class.java
}

class NullableTimeStampColumn(table: Table, name: String) : NullableColumn<Instant?>(name, table, Types.TIMESTAMP){
    override fun getValue(position: Int, resultSet: ResultSet): Instant? =resultSet.getTimestamp(position)?.let {DateUtil.toInstant(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))
    override val columnClass: Class<*> = NullableTimeStampColumn::class.java
    override val valueClass: Class<*> = Instant::class.java

}

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class OffsetDateTimeColumn(table: Table, name: String) : NonNullableColumn<OffsetDateTime>(name, table){
    override val nullable: NullableColumn<OffsetDateTime?> = NullableOffsetDateTimeColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime) {
        statement.setObject(position, value)
    }
    override val columnClass: Class<*> = OffsetDateTimeColumn::class.java
    override val valueClass: Class<*> = OffsetDateTime::class.java
}

class NullableOffsetDateTimeColumn(table: Table, name: String) : NullableColumn<OffsetDateTime?>(name, table, Types.TIMESTAMP){
    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime?) {
        statement.setObject(position, value)
    }
    override val columnClass: Class<*> = NullableOffsetDateTimeColumn::class.java
    override val valueClass: Class<*> = OffsetDateTime::class.java
}

/**
 * Represents a database column whose type is converted from and to a java.util.Date.
 *
 * @param name The column name in the corresponding database table
 */
class DateColumn(table: Table, name: String) : NonNullableColumn<LocalDate>(name, table){
    override val nullable: NullableColumn<LocalDate?> = NullableDateColumn(table, name)
    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? =  resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate) =
        statement.setDate(position, DateUtil.toSqlDate(value))
    override val columnClass: Class<*> = DateColumn::class.java
    override val valueClass: Class<*> = LocalDate::class.java
}
class NullableDateColumn(table: Table, name: String) : NullableColumn<LocalDate?>(name, table, Types.DATE){
    fun of(year: Int, month: Int, day: Int): ColumnAndValue<LocalDate?> = create(LocalDate.of(year, month, day))
    override fun getValue(position: Int, resultSet: ResultSet): LocalDate? = resultSet.getDate(position)?.let {
        DateUtil.toLocalDate(DateUtil.toUtilDate(it))
    }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDate?) =
        statement.setDate(position, DateUtil.toSqlDate(value!!))
    override val columnClass: Class<*> = NullableDateColumn::class.java
    override val valueClass: Class<*> = LocalDate::class.java

}

class DateTimeColumn(table: Table, name: String) : NonNullableColumn<LocalDateTime>(name, table){
    override val nullable: NullableColumn<LocalDateTime?> = NullableDateTimeColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
    override val columnClass: Class<*> = DateTimeColumn::class.java
    override val valueClass: Class<*> = LocalDateTime::class.java
}
class NullableDateTimeColumn(table: Table, name: String) : NullableColumn<LocalDateTime?>(name, table, Types.TIMESTAMP){
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))
    override val columnClass: Class<*> = NullableDateTimeColumn::class.java
    override val valueClass: Class<*> = LocalDateTime::class.java
}
