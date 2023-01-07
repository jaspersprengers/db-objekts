package com.dbobjekts.vendors.h2

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn


class ObjectArrayColumn(table: AnyTable, name: String) : ObjectColumn<Array<Any>>(table, name, Array::class.java) {

    override val nullable: NullableColumn<Array<Any>?> = NullableObjectArrayColumn(table, name)
}


class NullableObjectArrayColumn(table: AnyTable, name: String) : NullableObjectColumn<Array<Any>?>(table, name, Array::class.java) {

}
