package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.util.DateUtil
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * Represents a database timestamp column whose type is converted from a java.sql.Timestamp
 *
 * @param name The column name in the corresponding database table
 */
class OffsetDateTimeColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<OffsetDateTime>(table,name, OffsetDateTime::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = OffsetDateTimeColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime) {
        statement.setObject(position, value)
    }
}

class NullableOffsetDateTimeColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<OffsetDateTime?>(table,name, Types.TIMESTAMP, OffsetDateTime::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableOffsetDateTimeColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): OffsetDateTime? = resultSet.getObject(position, OffsetDateTime::class.java)

    override fun setValue(position: Int, statement: PreparedStatement, value: OffsetDateTime?) {
        statement.setObject(position, value)
    }
}
