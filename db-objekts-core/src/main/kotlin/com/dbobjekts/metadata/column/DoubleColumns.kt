package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Double
 *
 * @param name    The column name in the corresponding database table
 */
class DoubleColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Double>(table,name, Double::class.java, aggregateType), FloatingPointNumericColumn by FloatingPointNumericColumnCloner(table, name){

    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = DoubleColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): Double = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double) =
        statement.setDouble(position, value)

}

class NullableDoubleColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Double?>(table,name, Types.DOUBLE, Double::class.java, aggregateType), FloatingPointNumericColumn by FloatingPointNumericColumnCloner(table, name) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableDoubleColumn(table, nameInTable, AggregateType.DISTINCT)


    override fun getValue(position: Int, resultSet: ResultSet): Double? = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double?) =
        statement.setDouble(position, value!!)
}
