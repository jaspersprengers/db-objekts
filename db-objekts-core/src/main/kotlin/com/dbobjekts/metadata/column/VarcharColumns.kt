package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.nio.charset.Charset
import java.sql.*
import javax.sql.rowset.serial.SerialBlob
import javax.sql.rowset.serial.SerialClob

open class VarcharColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<String>(table,name, String::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = VarcharColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: String) =
        statement.setString(position, value)

}

open class NullableVarcharColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<String?>(table,name, Types.VARCHAR, String::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableVarcharColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) =
        statement.setString(position, value)

}
