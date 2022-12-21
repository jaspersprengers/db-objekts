package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
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
class BlobColumn(table: Table, name: String) : NonNullableColumn<Blob>(name, table, Blob::class.java){
    override val nullable: NullableColumn<Blob?> = NullableBlobColumn(table, name)
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

class NullableBlobColumn(table: Table, name: String) : NullableColumn<Blob?>(name, table, Types.BLOB, Blob::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Blob? = resultSet.getBlob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Blob?) =
        statement.setBlob(position, value)
}
