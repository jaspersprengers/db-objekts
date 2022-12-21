package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table

class ForeignKeyLongColumn(table: Table,
                           name: String,
                           override val parentColumn: Column<Long>) :LongColumn(table, name), IsMandatoryForeignKey<Long> {
    override val column: Column<Long> = this
}

class ForeignKeyIntColumn(table: Table, name: String, override val parentColumn: Column<Int>)
    :IntegerColumn(table, name), IsMandatoryForeignKey<Int> {
    override val column: Column<Int> = this
}

class OptionalForeignKeyLongColumn(table: Table, name: String, override val parentColumn: Column<Long>) :NullableLongColumn(table, name), IsOptionalForeignKey<Long> {
    override val column: Column<Long?> = this
}

class OptionalForeignKeyIntColumn(table: Table, name: String, override val parentColumn: NonNullableColumn<Int>)
    :NullableIntegerColumn(table, name), IsOptionalForeignKey<Int> {
    override val column: Column<Int?> = this
}

class ForeignKeyVarcharColumn(table: Table, name: String, override val parentColumn: Column<String>) : VarcharColumn(table, name), IsMandatoryForeignKey<String> {
    override val column: Column<String> = this
}

class OptionalForeignKeyVarcharColumn(table: Table, name: String, override val parentColumn: Column<String>) : NullableVarcharColumn(table, name), IsOptionalForeignKey<String> {
    override val column: Column<String?> = this
}
