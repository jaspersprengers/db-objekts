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
class BigDecimalColumn(table: AnyTable, name: String) : NonNullableColumn<BigDecimal>(name, table, BigDecimal::class.java){
    override val nullable: NullableColumn<BigDecimal?> = NullableBigDecimalColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal) =
        statement.setBigDecimal(position, value)

}

class NullableBigDecimalColumn(table: AnyTable, name: String) : NullableColumn<BigDecimal?>(name, table, Types.NUMERIC, BigDecimal::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal? = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal?) =
        statement.setBigDecimal(position, value!!)
}
