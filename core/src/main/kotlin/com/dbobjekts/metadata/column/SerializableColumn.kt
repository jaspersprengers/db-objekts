package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class SerializableColumn<E : Serializable>(table: Table,
                                                    name: String) : NonNullableColumn<E>(name, table){
    override  fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { parse(it) }

    override  fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

    abstract fun parse(str: String): E

}

abstract class NullableSerializableColumn<E : Serializable>(table: Table,
                                                            name: String) : NullableColumn<E?>(name, table, Types.VARCHAR){
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { parse(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

    abstract fun parse(str: String): E?

}
