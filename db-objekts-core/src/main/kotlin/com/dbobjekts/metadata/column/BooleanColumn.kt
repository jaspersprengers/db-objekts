package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Boolean
 *
 * @param name    The column name in the corresponding database table
 */
class BooleanColumn(table: Table, name: String) : NonNullableColumn<Boolean>(name, table, Boolean::class.java) {
    override val nullable: NullableColumn<Boolean?> = NullableBooleanColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getBoolean(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setBoolean(position, value as Boolean)

    override fun defaultValue() = false
}

class NullableBooleanColumn(table: Table, name: String) : NullableColumn<Boolean?>(name, table, Types.BOOLEAN, Boolean::class.java) {
    override fun getValue(position: Int, resultSet: ResultSet): Boolean? = resultSet.getBoolean(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setBoolean(position, value as Boolean)
}

class NumberAsBooleanColumn(table: Table, name: String) : NonNullableColumn<Boolean>(name, table, Boolean::class.java) {
    override val nullable: NullableColumn<Boolean?> = NullableNumberAsBooleanColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getInt(position) == 1

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setInt(position, if (value) 1 else 0)

    override fun defaultValue() = false

}

class NullableNumberAsBooleanColumn(table: Table, name: String) :
    NullableColumn<Boolean?>(name, table, Types.INTEGER, Boolean::class.java) {
    override fun getValue(position: Int, resultSet: ResultSet): Boolean? = resultSet.getInt(position) == 1

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setInt(position, if (value != null && value) 1 else 0)
}
