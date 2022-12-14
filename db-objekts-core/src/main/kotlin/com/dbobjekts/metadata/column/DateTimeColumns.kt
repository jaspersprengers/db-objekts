package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDateTime

class DateTimeColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<LocalDateTime>(name, table, LocalDateTime::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = DateTimeColumn(table, nameInTable, AggregateType.DISTINCT)

    override val nullable: NullableColumn<LocalDateTime?> = NullableDateTimeColumn(table, name, aggregateType)
    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? =
        resultSet.getTimestamp(position)?.let { DateUtil.toLocalDateTime(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
}

class NullableDateTimeColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<LocalDateTime?>(name, table, Types.TIMESTAMP, LocalDateTime::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableDateTimeColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): LocalDateTime? =
        resultSet.getTimestamp(position)?.let { DateUtil.toLocalDateTime(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: LocalDateTime?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))
}
