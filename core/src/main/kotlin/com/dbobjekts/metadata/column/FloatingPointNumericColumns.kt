package com.dbobjekts.metadata.column

import java.sql.PreparedStatement
import java.sql.ResultSet
import com.dbobjekts.metadata.Table
import java.math.BigDecimal
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Double
 *
 * @param name    The column name in the corresponding database table
 */
class DoubleColumn(table: Table, name: String) : NonNullableColumn<Double>(name, table){
    override val nullable: NullableColumn<Double?> = NullableDoubleColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Double = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double) =
        statement.setDouble(position, value as Double)
    override val valueClass: Class<*> = Double::class.java

}

class NullableDoubleColumn(table: Table, name: String) : NullableColumn<Double?>(name, table, Types.DOUBLE){
    override fun getValue(position: Int, resultSet: ResultSet): Double? = resultSet.getDouble(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Double?) =
        statement.setDouble(position, value!!)
    override val valueClass: Class<*> = Double::class.java
}

/**
 * Represents a database column whose type is converted from and to a Float
 *
 * @param name    The column name in the corresponding database table
 */
class FloatColumn(table: Table, name: String) : NonNullableColumn<Float>(name, table){
    override val nullable: NullableColumn<Float?> = NullableFloatColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Float = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float) =
        statement.setFloat(position, value as Float)
    override val valueClass: Class<*> = Float::class.java
}

class NullableFloatColumn(table: Table, name: String) : NullableColumn<Float?>(name, table, Types.FLOAT){
    override fun getValue(position: Int, resultSet: ResultSet): Float? = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float?) =
        statement.setFloat(position, value!!)
    override val valueClass: Class<*> = Float::class.java
}

/**
 * Represents a database column whose type is converted from and to a BigDecimal
 *
 * @param name    The column name in the corresponding database table
 */
class BigDecimalColumn(table: Table, name: String) : NonNullableColumn<BigDecimal>(name, table){
    override val nullable: NullableColumn<BigDecimal?> = NullableBigDecimalColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal) =
        statement.setBigDecimal(position, value as BigDecimal)
    override val valueClass: Class<*> = BigDecimal::class.java
}

class NullableBigDecimalColumn(table: Table, name: String) : NullableColumn<BigDecimal?>(name, table, Types.NUMERIC){
    override fun getValue(position: Int, resultSet: ResultSet): BigDecimal? = resultSet.getBigDecimal(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: BigDecimal?) =
        statement.setBigDecimal(position, value!!)
    override val valueClass: Class<*> = BigDecimal::class.java
}
