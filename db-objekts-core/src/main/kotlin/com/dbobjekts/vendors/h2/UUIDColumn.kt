package com.dbobjekts.vendors.h2

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import java.util.UUID


class UUIDColumn(table: AnyTable, name: String) : ObjectColumn<UUID>(table, name, UUID::class.java) {

    override val nullable: NullableColumn<UUID?> = NullableUUIDColumn(table, name)
}


class NullableUUIDColumn(table: AnyTable, name: String) : NullableObjectColumn<UUID?>(table, name, UUID::class.java) {

}
