package com.dbobjekts.metadata.column

data class NonNullableColumnAndValue<I>(override val column: Column<I>, override val value: I?) : ColumnAndValue<I> {

    init {
        if (value == null) throw IllegalArgumentException("Non-nullable column $column.name cannot take null value")
    }

}
