package com.dbobjekts.testdb

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AggregateType
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class YesNoBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Boolean>(name, table, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override val nullable: NullableColumn<Boolean?> = NullableYesNoBooleanColumn(table, name)
    override fun distinct(): YesNoBooleanColumn = YesNoBooleanColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setString(position, if (value) "Y" else "N")

}

class NullableYesNoBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Boolean?>(name, table, Types.VARCHAR, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct(): NullableYesNoBooleanColumn = NullableYesNoBooleanColumn(table, nameInTable, AggregateType.DISTINCT)


    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setString(position, if (value != null && value) "Y" else "N")
}
