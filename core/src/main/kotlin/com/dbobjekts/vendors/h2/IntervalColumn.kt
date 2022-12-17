package com.dbobjekts.vendors.h2

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.h2.api.Interval


class IntervalColumn(table: Table, name: String) : ObjectColumn<Interval>(table, name, Interval::class.java) {

    override val nullable: NullableColumn<Interval?> = NullableIntervalColumn(table, name)
    override val columnClass: Class<*> = IntervalColumn::class.java
    override val valueClass: Class<*> = Interval::class.java
}


class NullableIntervalColumn(table: Table, name: String) : NullableObjectColumn<Interval?>(table, name, Interval::class.java) {

    override val columnClass: Class<*> = NullableIntervalColumn::class.java
    override val valueClass: Class<*> = Interval::class.java
}
