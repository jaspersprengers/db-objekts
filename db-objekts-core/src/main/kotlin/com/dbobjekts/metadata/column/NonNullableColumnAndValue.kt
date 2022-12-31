package com.dbobjekts.metadata.column

import com.dbobjekts.api.exception.StatementExecutionException

data class NonNullableColumnAndValue<I>(override val column: Column<I>, override val value: I?) : ColumnAndValue<I> {

    init {
        if (value == null) throw StatementExecutionException("Non-nullable column $column.name cannot take null value")
    }

}
