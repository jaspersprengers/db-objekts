package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
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
class ClobColumn(table: Table, name: String) : NonNullableColumn<Clob>(name, table, Clob::class.java){
    override val nullable: NullableColumn<Clob?> = NullableClobColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob) =
        statement.setClob(position, value)
    companion object {
        fun ofString(value: String) = SerialClob(value.toCharArray())
        fun serialize(blob: Clob) =
            String(blob.asciiStream.readAllBytes())
    }
}

class NullableClobColumn(table: Table, name: String) : NullableColumn<Clob?>(name, table, Types.CLOB, Clob::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob?) =
        statement.setClob(position, value)
}
