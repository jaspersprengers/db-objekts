package com.dbobjekts.fixture.columns

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.AggregateType
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class YesNoBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Boolean>(table,name, Boolean::class.java, aggregateType) {

    //override //override fun distinct(): YesNoBooleanColumn = YesNoBooleanColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setString(position, if (value) "Y" else "N")

}

class NullableYesNoBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Boolean?>(table,name, Types.VARCHAR, Boolean::class.java, aggregateType) {

    //override //override fun distinct(): NullableYesNoBooleanColumn = NullableYesNoBooleanColumn(table, nameInTable, AggregateType.DISTINCT)


    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getString(position).equals("Y", true)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setString(position, if (value != null && value) "Y" else "N")
}
