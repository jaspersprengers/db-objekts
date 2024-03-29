package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException

interface AutoKeyColumn<I> : IsGeneratedPrimaryKey

open class AutoKeyIntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    IntegerColumn(table, name, aggregateType), AutoKeyColumn<Int> {
    constructor(table: AnyTable, name: String) : this(table, name, null)
    override val nullable: NullableColumn<Int?> = NullableIntegerColumn(table, name, aggregateType)

    override fun distinct() =
        throw DBObjektsException("distinct() operation is not supported on an auto-generated key, as this is most certainly not what you want. All values will be distinct.")

}

open class AutoKeyLongColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    LongColumn(table, name, aggregateType), AutoKeyColumn<Long> {
    constructor(table: AnyTable, name: String) : this(table, name, null)
    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name, aggregateType)

    override fun distinct() =
        throw DBObjektsException("distinct() operation is not supported on an auto-generated key, as this is most certainly not what you want. All values will be distinct.")

}





