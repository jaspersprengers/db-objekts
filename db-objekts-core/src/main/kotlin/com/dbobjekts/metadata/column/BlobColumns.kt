package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.nio.charset.Charset
import java.sql.Blob
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types
import javax.sql.rowset.serial.SerialBlob

/**
 * Represents a database column whose type is converted from and to a Blob
 *
 * @param name The column name in the corresponding database table
 */
open class BlobColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Blob>(table,name, Blob::class.java, aggregateType) {

    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Blob? = resultSet.getBlob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Blob) =
        statement.setBlob(position, value)

    companion object {
        fun ofString(value: String, charset: Charset = Charset.defaultCharset()) =
            SerialBlob(value.toByteArray(charset))

        fun serialize(blob: Blob) =
            String(blob.binaryStream.readAllBytes())
    }
}

open class NullableBlobColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Blob?>(table,name, Types.BLOB, Blob::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Blob? = resultSet.getBlob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Blob?) =
        statement.setBlob(position, value)
}
