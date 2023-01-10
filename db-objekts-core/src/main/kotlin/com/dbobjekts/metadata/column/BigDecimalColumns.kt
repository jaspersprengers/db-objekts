package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a BigDecimal
 *
 * @param name    The column name in the corresponding database table
 */
class BigDecimalColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<BigDecimal>(name, table, BigDecimal::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = BigDecimalColumn(table, nameInTable, AggregateType.DISTINCT)

    fun sum() = BigDecimalColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = BigDecimalColumn(table, nameInTable, AggregateType.AVG)
    fun min() = BigDecimalColumn(table, nameInTable, AggregateType.MIN)
    fun max() = BigDecimalColumn(table, nameInTable, AggregateType.MAX)

    override val nullable: NullableColumn<BigDecimal?> = NullableBigDecimalColumn(table, name, aggregateType)
    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal) =
        statement.setBigDecimal(position, value)

}

class NullableBigDecimalColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<BigDecimal?>(name, table, Types.NUMERIC, BigDecimal::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableBigDecimalColumn(table, nameInTable, AggregateType.DISTINCT)

    fun sum() = BigDecimalColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = BigDecimalColumn(table, nameInTable, AggregateType.AVG)
    fun min() = BigDecimalColumn(table, nameInTable, AggregateType.MIN)
    fun max() = BigDecimalColumn(table, nameInTable, AggregateType.MAX)

    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal? = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal?) =
        statement.setBigDecimal(position, value!!)
}
