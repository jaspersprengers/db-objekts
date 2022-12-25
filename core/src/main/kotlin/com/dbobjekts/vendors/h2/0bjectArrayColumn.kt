package com.dbobjekts.vendors.h2

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.h2.api.Interval
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


class ObjectArrayColumn(table: Table, name: String) : ObjectColumn<Array<Any>>(table, name, Array::class.java) {

    override val nullable: NullableColumn<Array<Any>?> = NullableObjectArrayColumn(table, name)
}


class NullableObjectArrayColumn(table: Table, name: String) : NullableObjectColumn<Array<Any>?>(table, name, Array::class.java) {

}
