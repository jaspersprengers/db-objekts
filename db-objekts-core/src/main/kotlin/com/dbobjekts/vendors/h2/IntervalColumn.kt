package com.dbobjekts.vendors.h2

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.h2.api.Interval


class IntervalColumn(table: AnyTable, name: String) : ObjectColumn<Interval>(table, name, Interval::class.java) {

}


class NullableIntervalColumn(table: AnyTable, name: String) : NullableObjectColumn<Interval?>(table, name, Interval::class.java) {

}
