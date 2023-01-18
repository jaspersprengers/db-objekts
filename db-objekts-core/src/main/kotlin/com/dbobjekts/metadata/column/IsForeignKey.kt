package com.dbobjekts.metadata.column

internal interface IsForeignKey<T, P> {
    val column: Column<T>
    val parentColumn: Column<P>
}

internal interface IsOptionalForeignKey<I> : IsForeignKey<I?, I>

internal interface IsMandatoryForeignKey<I> : IsForeignKey<I, I>
