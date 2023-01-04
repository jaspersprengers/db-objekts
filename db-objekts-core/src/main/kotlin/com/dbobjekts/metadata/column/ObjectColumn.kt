package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class ObjectColumn<E>(
    table: AnyTable,
    name: String,
    val clz: Class<*>,
    aggregateType: AggregateType?
) : NonNullableColumn<E>(name, table, clz, aggregateType) {
    constructor(table: AnyTable, name: String, clz: Class<*>) : this(table, name, clz, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    @Suppress("UNCHECKED_CAST")
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getObject(position, clz) as E

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setObject(position, value)

}

abstract class NullableObjectColumn<E>(
    table: AnyTable,
    name: String,
    val clz: Class<*>,
    aggregateType: AggregateType?
) : NullableColumn<E?>(name, table, Types.VARCHAR, clz, aggregateType) {
    constructor(table: AnyTable, name: String, clz: Class<*>) : this(table, name, clz, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    @Suppress("UNCHECKED_CAST")
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getObject(position, clz) as E

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setObject(position, value)

}
