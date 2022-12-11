package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table

interface AutoKeyColumn<I> : IsPrimaryKey

class AutoKeyIntegerColumn(table: Table, name: String) : IntegerColumn(table, name), AutoKeyColumn<Int>

class AutoKeyLongColumn(table: Table, name: String):LongColumn(table, name), AutoKeyColumn<Long>

 interface SequenceKeyColumn<I> : IsPrimaryKey {
    fun qualifiedSequence(): String
    fun createValueForUpdate(key: Long): NullableColumnAndValue<I>
}

class SequenceKeyLongColumn(table: Table, name: String,
                            val sequence: String):LongColumn(table, name), SequenceKeyColumn<Long> {

    override fun qualifiedSequence(): String = if (sequence.startsWith(table.schemaName().value)) sequence else "${table.schemaName()}.$sequence"

    override fun createValueForUpdate(key: Long): NullableColumnAndValue<Long> = NullableColumnAndValue(this, key)
}


class SequenceKeyIntegerColumn(table: Table, name: String,
                               val sequence: String):IntegerColumn(table, name), SequenceKeyColumn<Int> {

    override fun qualifiedSequence() = "${table.schemaName()}.$sequence"

    override fun createValueForUpdate(key: Long): NullableColumnAndValue<Int> = NullableColumnAndValue(this, key.toInt())
}


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
