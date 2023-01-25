package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.PackageName
import com.dbobjekts.metadata.column.*
import java.sql.PreparedStatement
import java.sql.ResultSet


internal class GeneratedEnumPlaceholderColumn<T : Enum<T>>(
    name: String,
    table: AnyTable,
    valueClass: Class<T>,
    aggregateType: AggregateType?
) : NonNullableColumn<T>(table,name, valueClass, aggregateType) {

    lateinit var packageName: PackageName

    override fun create(value: T?): ColumnAndValue<T>  = throw IllegalAccessException("Not implemented")

    //override //override fun distinct(): NonNullableColumn<T>  = throw IllegalAccessException("Not implemented")

    override fun retrieveValue(position: Int, rs: ResultSet): T?  = throw IllegalAccessException("Not implemented")

    override fun getValue(position: Int, resultSet: ResultSet): T?  = throw IllegalAccessException("Not implemented")

    override fun setValue(position: Int, statement: PreparedStatement, value: T)  = throw IllegalAccessException("Not implemented")

    override fun putValue(position: Int, statement: PreparedStatement, value: T?)  = throw IllegalAccessException("Not implemented")

    override fun simpleClassName(): String = valueClass.simpleName+"Column"

    override fun qualifiedClassName(): String = packageName.toString()+"."+simpleClassName()
}

internal class GeneratedNullableEnumPlaceholderColumn<T : Enum<T>>(
    name: String,
    table: AnyTable,
    sqlType: Int,
    valueClass: Class<T>,
    aggregateType: AggregateType?
) : NullableColumn<T?>(table,name, sqlType, valueClass, aggregateType) {

    lateinit var packageName: PackageName

    override fun create(value: T?): ColumnAndValue<T?> {
        TODO("Not yet implemented")
    }

    //override //override fun distinct(): NullableColumn<T?> = throw IllegalAccessException("Not implemented")

    override fun retrieveValue(position: Int, rs: ResultSet): T?  = throw IllegalAccessException("Not implemented")

    override fun getValue(position: Int, resultSet: ResultSet): T?  = throw IllegalAccessException("Not implemented")

    override fun setValue(position: Int, statement: PreparedStatement, value: T?)  = throw IllegalAccessException("Not implemented")

    override fun putValue(position: Int, statement: PreparedStatement, value: T?)  = throw IllegalAccessException("Not implemented")

    override fun simpleClassName(): String = "Nullable${valueClass.simpleName}+Column"

    override fun qualifiedClassName(): String = packageName.toString()+"."+simpleClassName()
}
