package com.dbobjekts.testdb

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class YesNoBooleanColumn(table: AnyTable, name: String) : NonNullableColumn<Boolean>(name, table, Boolean::class.java) {
    override val nullable: NullableColumn<Boolean?> = NullableYesNoBooleanColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setString(position, if (value) "Y" else "N")

}

class NullableYesNoBooleanColumn(table: AnyTable, name: String) :
    NullableColumn<Boolean?>(name, table, Types.VARCHAR, Boolean::class.java) {
    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setString(position, if (value != null && value) "Y" else "N")
}
