package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.Clob
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import javax.sql.rowset.serial.SerialClob

/**
 * Represents a database column whose type is converted from and to a Clob
 *
 * @param name The column name in the corresponding database table
 */
class ClobColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Clob>(table,name, Clob::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob) =
        statement.setClob(position, value)

    companion object {
        fun ofString(value: String) = SerialClob(value.toCharArray())
        fun serialize(blob: Clob) =
            String(blob.asciiStream.readAllBytes())
    }
}

class NullableClobColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Clob?>(table,name, Types.CLOB, Clob::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob?) =
        statement.setClob(position, value)
}
