package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Double
 *
 * @param name    The column name in the corresponding database table
 */
class DoubleColumn(table: Table, name: String) : NonNullableColumn<Double>(name, table, Double::class.java){
    override val nullable: NullableColumn<Double?> = NullableDoubleColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Double = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double) =
        statement.setDouble(position, value as Double)

}

class NullableDoubleColumn(table: Table, name: String) : NullableColumn<Double?>(name, table, Types.DOUBLE, Double::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Double? = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double?) =
        statement.setDouble(position, value!!)
}
