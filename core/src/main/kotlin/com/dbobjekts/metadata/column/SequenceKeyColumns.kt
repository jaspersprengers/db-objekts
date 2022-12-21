package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table

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
