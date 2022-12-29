package com.dbobjekts.testdb

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableSerializableColumn
import com.dbobjekts.metadata.column.SerializableColumn


class StreetAndTownColumn(
    table: Table,
    name: String
) : SerializableColumn<Address>(table, name) {
    override fun parse(str: String): Address = Address.parse(str)

    override val nullable: NullableColumn<Address?> = NullableStreetAndTownColumn(table, name)
}

class NullableStreetAndTownColumn(
    table: Table,
    name: String
) : NullableSerializableColumn<Address>(table, name) {
    override fun parse(str: String): Address = Address.parse(str)
}
