package com.dbobjekts.metadata.column

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

/**
 * Implemented by Float, Double and BigDecimal Column types
 */
interface FloatingPointNumericColumn : NumericColumn {
    fun sum(): DoubleColumn
    fun avg(): DoubleColumn
    fun min(): DoubleColumn
    fun max(): DoubleColumn
}
