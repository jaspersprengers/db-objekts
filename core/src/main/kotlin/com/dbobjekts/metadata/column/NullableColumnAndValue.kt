package com.dbobjekts.metadata.column

/**
 * Encapsulates a [[com.dbobjekts.metadata.column.Column]] metadata object and a specific value to be inserted.
 *
 * @param column the Column you wish to insert into
 * @param value  the non-null value to be used.
 * @tparam I the datatype of this column
 */
data class NullableColumnAndValue<I>(override val column: Column<I>, override val value: I?) : ColumnAndValue<I>

data class NonNullableColumnAndValue<I>(override val column: Column<I>, override val value: I?) : ColumnAndValue<I> {

    init {
        if (value == null) throw IllegalArgumentException("Non-nullable column $column.name cannot take null value")
    }

}

interface ColumnAndValue<I> {
    val column: Column<I>
    val value: I?
}
