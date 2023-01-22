package com.dbobjekts.vendors.mariadb

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.*
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import java.util.*


class UUIDColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) : NonNullableColumn<UUID>(name,table, UUID::class.java, aggregateType) {

    override val nullable: NullableColumn<UUID?> = NullableUUIDColumn(table, name, aggregateType)

    override fun distinct() = UUIDColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): UUID? = UUID.fromString(resultSet.getString(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: UUID) = statement.setString(position, value.toString())

}


class NullableUUIDColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) : NullableColumn<UUID?>(name, table, Types.VARCHAR, UUID::class.java, aggregateType) {

    override fun distinct() = NullableUUIDColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): UUID? = resultSet.getString(position)?.let { UUID.fromString(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: UUID?) = statement.setString(position, value?.toString())
}
