package com.dbobjekts.testdb

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class JaOderNeinColumn(table: Table, name: String) : NonNullableColumn<Boolean>(name, table, Boolean::class.java) {
    override val nullable: NullableColumn<Boolean?> = NullableJaOderNeinColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position) == "Ja"

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setString(position, if (value) "Ja" else "Nein")

    override fun defaultValue() = false

}

class NullableJaOderNeinColumn(table: Table, name: String) :
    NullableColumn<Boolean?>(name, table, Types.VARCHAR, Boolean::class.java) {
    override fun getValue(position: Int, resultSet: ResultSet): Boolean? = resultSet.getString(position) == "Ja"

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setString(position, if (value != null && value) "Ja" else "Nein")
}
