package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException

interface SequenceKeyColumn<I> : IsGeneratedPrimaryKey {
    fun qualifiedSequence(): String
    fun createValueForUpdate(key: Long): NullableColumnAndValue<I>
}

open class SequenceKeyLongColumn(
    table: AnyTable,
    name: String,
    val sequence: String,
    aggregateType: AggregateType?
) : LongColumn(table, name, aggregateType), SequenceKeyColumn<Long> {
    constructor(table: AnyTable, name: String, sequence: String) : this(table, name, sequence, null)

    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name, aggregateType)

    override fun distinct() =
        throw DBObjektsException("distinct() operation is not supported on an auto-generated key, as this is most certainly not what you want. All values will be distinct.")

    override fun qualifiedSequence(): String =
        if (sequence.startsWith(table.schemaName().value)) sequence else "${table.schemaName()}.$sequence"

    override fun createValueForUpdate(key: Long): NullableColumnAndValue<Long> = NullableColumnAndValue(this, key)
}


open class SequenceKeyIntegerColumn(
    table: AnyTable,
    name: String,
    val sequence: String,
    aggregateType: AggregateType?
) :
    IntegerColumn(table, name, aggregateType), SequenceKeyColumn<Int> {
    constructor(table: AnyTable, name: String, sequence: String) : this(table, name, sequence, null)

    override val nullable: NullableColumn<Int?> = NullableIntegerColumn(table, name, aggregateType)

    override fun distinct() =
        throw DBObjektsException("distinct() operation is not supported on an auto-generated key, as this is most certainly not what you want. All values will be distinct.")

    override fun qualifiedSequence() = "${table.schemaName()}.$sequence"

    override fun createValueForUpdate(key: Long): NullableColumnAndValue<Int> = NullableColumnAndValue(this, key.toInt())
}
