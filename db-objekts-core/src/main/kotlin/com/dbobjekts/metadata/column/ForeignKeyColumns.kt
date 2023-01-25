package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable

class ForeignKeyLongColumn(
    table: AnyTable,
    name: String,
    override val parentColumn: Column<Long>,
    aggregateType: AggregateType?
) : LongColumn(table, name, aggregateType), IsMandatoryForeignKey<Long> {
    constructor(table: AnyTable, name: String, parentColumn: Column<Long>) : this(table, name, parentColumn, null)

    override val nullable = OptionalForeignKeyLongColumn(table, name, parentColumn, aggregateType)

    override val column: Column<Long> = this
}

class ForeignKeyIntColumn(table: AnyTable, name: String, override val parentColumn: Column<Int>, aggregateType: AggregateType?) :
    IntegerColumn(table, name, aggregateType), IsMandatoryForeignKey<Int> {
    constructor(table: AnyTable, name: String, parentColumn: Column<Int>) : this(table, name, parentColumn, null)

    override val nullable = NullableIntegerColumn(table, name, aggregateType)

    override val column: Column<Int> = this
}

class OptionalForeignKeyLongColumn(table: AnyTable, name: String, override val parentColumn: Column<Long>, aggregateType: AggregateType?) :
    NullableLongColumn(table, name, aggregateType), IsOptionalForeignKey<Long> {
    constructor(table: AnyTable, name: String, parentColumn: Column<Long>) : this(table, name, parentColumn, null)

    override val column: Column<Long?> = this
}

class OptionalForeignKeyIntColumn(
    table: AnyTable,
    name: String,
    override val parentColumn: NonNullableColumn<Int>,
    aggregateType: AggregateType?
) : NullableIntegerColumn(table, name, aggregateType), IsOptionalForeignKey<Int> {
    constructor(table: AnyTable, name: String, parentColumn: NonNullableColumn<Int>) : this(table, name, parentColumn, null)

    override val column: Column<Int?> = this
}

class ForeignKeyVarcharColumn(table: AnyTable, name: String, override val parentColumn: Column<String>, aggregateType: AggregateType?) :
    VarcharColumn(table, name, aggregateType), IsMandatoryForeignKey<String> {
    constructor(table: AnyTable, name: String, parentColumn: Column<String>) : this(table, name, parentColumn, null)

    override val nullable = OptionalForeignKeyVarcharColumn(table, name, parentColumn, aggregateType)

    override val column: Column<String> = this
}

class OptionalForeignKeyVarcharColumn(
    table: AnyTable,
    name: String,
    override val parentColumn: Column<String>,
    aggregateType: AggregateType?
) : NullableVarcharColumn(table, name, aggregateType), IsOptionalForeignKey<String> {
    constructor(table: AnyTable, name: String, parentColumn: Column<String>) : this(table, name, parentColumn, null)

    override val column: Column<String?> = this
}
