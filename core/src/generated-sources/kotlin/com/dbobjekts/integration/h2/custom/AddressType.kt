package com.dbobjekts.integration.h2.custom

import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import java.io.Serializable

enum class AddressType : Serializable {
    HOME, WORK
}

class AddressTypeColumn(table: Table, name: String) : NullableSerializableColumn<AddressType>(table, name) {
    override fun parse(str: String): AddressType = AddressType.valueOf(str)
    override val columnClass: Class<*> = AddressTypeColumn::class.java
    override val valueClass: Class<*> = AddressType::class.java

    companion object {
        val INSTANCE: AddressTypeColumn = AddressTypeColumn(DefaultTable, "dummy")
    }
}
