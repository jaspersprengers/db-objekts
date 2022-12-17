package com.dbobjekts.vendors.h2

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.NullableObjectColumn
import com.dbobjekts.metadata.column.ObjectColumn
import org.h2.api.Interval
import java.sql.PreparedStatement
import java.sql.Types


class H2ObjectColumn(table: Table, name: String) : ObjectColumn<Any>(table, name, Interval::class.java) {

    override fun setValue(position: Int, statement: PreparedStatement, value: Any) = statement.setObject(position, value, Types.JAVA_OBJECT)
    override val nullable: NullableColumn<Any?> = NullableH2ObjectColumn(table, name)
    override val columnClass: Class<*> = H2ObjectColumn::class.java
    override val valueClass: Class<*> = Any::class.java

}


class NullableH2ObjectColumn(table: Table, name: String) : NullableObjectColumn<Any?>(table, name, Interval::class.java) {

    override fun setValue(position: Int, statement: PreparedStatement, value: Any?) = statement.setObject(position, value, Types.JAVA_OBJECT)
    override val columnClass: Class<*> = NullableH2ObjectColumn::class.java
    override val valueClass: Class<*> = Any::class.java
}
