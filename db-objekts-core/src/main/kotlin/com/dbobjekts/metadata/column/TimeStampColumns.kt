package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.Instant
import java.time.LocalTime

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class TimeStampColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Instant>(name, table, Instant::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = TimeStampColumn(table, nameInTable, AggregateType.DISTINCT)

    override val nullable: NullableColumn<Instant?> = NullableTimeStampColumn(table, name, aggregateType)
    override fun getValue(position: Int, resultSet: ResultSet): Instant? = resultSet.getTimestamp(position)?.let { DateUtil.toInstant(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value))
}

class NullableTimeStampColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Instant?>(name, table, Types.TIMESTAMP, Instant::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableTimeStampColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): Instant? = resultSet.getTimestamp(position)?.let { DateUtil.toInstant(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: Instant?) =
        statement.setTimestamp(position, DateUtil.toSqlTimeStamp(value!!))

}
