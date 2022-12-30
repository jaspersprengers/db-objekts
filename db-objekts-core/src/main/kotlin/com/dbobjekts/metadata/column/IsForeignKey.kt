package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable

interface IsForeignKey<T, P> {
    val column: Column<T>
    val table: AnyTable
    val nameInTable: String
    val parentColumn: Column<P>
}

interface IsOptionalForeignKey<I> : IsForeignKey<I?, I>

interface IsMandatoryForeignKey<I> : IsForeignKey<I, I>
