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
    NonNullableColumn<Double>(name, table, Double::class.java, aggregateType), FloatingPointNumericColumn {

    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = DoubleColumn(table, nameInTable, AggregateType.DISTINCT)
    override fun sum() = DoubleColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = DoubleColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = DoubleColumn(table, nameInTable, AggregateType.MAX)

    override val nullable: NullableColumn<Double?> = NullableDoubleColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Double = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double) =
        statement.setDouble(position, value)

}

class NullableDoubleColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Double?>(name, table, Types.DOUBLE, Double::class.java, aggregateType), FloatingPointNumericColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableDoubleColumn(table, nameInTable, AggregateType.DISTINCT)
    override fun sum() = DoubleColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = DoubleColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = DoubleColumn(table, nameInTable, AggregateType.MAX)

    override fun getValue(position: Int, resultSet: ResultSet): Double? = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double?) =
        statement.setDouble(position, value!!)
}
