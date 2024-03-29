package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLXML
import java.sql.Types

open class XMLColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<SQLXML>(table,name, SQLXML::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = XMLColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): SQLXML? = resultSet.getSQLXML(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: SQLXML) =
        statement.setSQLXML(position, value)

}

open class NullableXMLColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<SQLXML?>(table,name, Types.SQLXML, SQLXML::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableXMLColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): SQLXML? = resultSet.getSQLXML(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: SQLXML?) =
        statement.setSQLXML(position, value)

}
