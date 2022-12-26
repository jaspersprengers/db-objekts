package com.dbobjekts.metadata.column

/**
 * Encapsulates a [[com.dbobjekts.metadata.column.Column]] metadata object and a specific value to be inserted.
 *
 * @param column the Column you wish to insert into
 * @param value  the non-null value to be used.
 * @tparam I the datatype of this column
 */
data class NullableColumnAndValue<I>(override val column: Column<I>, override val value: I?) : ColumnAndValue<I>

