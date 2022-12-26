package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDateTime

class DateTimeColumn(table: Table, name: String) : NonNullableColumn<LocalDateTime>(name, table, LocalDateTime::class.java){
    override val nullable: NullableColumn<LocalDateTime?> = NullableDateTimeColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
    override fun defaultValue() = LocalDateTime.MIN
}
class NullableDateTimeColumn(table: Table, name: String) : NullableColumn<LocalDateTime?>(name, table, Types.TIMESTAMP, LocalDateTime::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? = resultSet.getTimestamp(position)?.let {DateUtil.toLocalDateTime(it)}

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))
}