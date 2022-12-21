package com.dbobjekts.metadata.column

interface ColumnAndValue<I> {
    val column: Column<I>
    val value: I?
}
