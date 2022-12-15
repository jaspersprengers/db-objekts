package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class ObjectColumn<E>(
    table: Table,
    name: String,
    val clz: Class<*>
) : NonNullableColumn<E>(name, table) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getObject(position, clz) as E

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setObject(position, value)

}

abstract class NullableObjectColumn<E>(
    table: Table,
    name: String,
    val clz: Class<*>
) : NullableColumn<E?>(name, table, Types.VARCHAR) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getObject(position, clz) as E

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setObject(position, value)

}
