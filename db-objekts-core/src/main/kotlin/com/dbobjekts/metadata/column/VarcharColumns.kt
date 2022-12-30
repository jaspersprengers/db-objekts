package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.nio.charset.Charset
import java.sql.*
import javax.sql.rowset.serial.SerialBlob
import javax.sql.rowset.serial.SerialClob

open class VarcharColumn(table: AnyTable, name: String) : NonNullableColumn<String>(name, table, String::class.java) {
    override val nullable: NullableColumn<String?> = NullableVarcharColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: String) =
        statement.setString(position, value)

}

open class NullableVarcharColumn(table: AnyTable, name: String) : NullableColumn<String?>(name, table, Types.VARCHAR, String::class.java) {
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) =
        statement.setString(position, value)

}
