package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
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
