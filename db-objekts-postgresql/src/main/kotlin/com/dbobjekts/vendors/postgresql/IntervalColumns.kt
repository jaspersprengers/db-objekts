package com.dbobjekts.vendors.postgresql

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.postgresql.util.PGInterval


class IntervalColumn(table: AnyTable, name: String) : ObjectColumn<PGInterval>(table, name, PGInterval::class.java) {

    override val nullable: NullableColumn<PGInterval?> = NullableIntervalColumn(table, name)
}

class NullableIntervalColumn(table: AnyTable, name: String) : NullableObjectColumn<PGInterval?>(table, name, PGInterval::class.java)
