package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable

/**
 * Parent interface for all numeric Column types
 */
interface NumericColumn

/**
 * Implemented by Byte, Short, Int and Long Column types
 */
interface IntegerNumericColumn : NumericColumn {
    fun sum(): LongColumn
    fun avg(): DoubleColumn
    fun min(): LongColumn
    fun max(): LongColumn
}

class IntegerNumericColumnCloner(private val table: AnyTable, private val nameInTable: String): IntegerNumericColumn {
    override fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = LongColumn(table, nameInTable, AggregateType.MAX)
}



/**
 * Implemented by Float, Double and Column types. BigDecimal does not implement this interface, because returning a Double would mean loss of precision.
 */
interface FloatingPointNumericColumn : NumericColumn {
    fun sum(): DoubleColumn
    fun avg(): DoubleColumn
    fun min(): DoubleColumn
    fun max(): DoubleColumn
}

class FloatingPointNumericColumnCloner(private val table: AnyTable, private val nameInTable: String): FloatingPointNumericColumn {
    override fun sum() = DoubleColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = DoubleColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = DoubleColumn(table, nameInTable, AggregateType.MAX)
}
