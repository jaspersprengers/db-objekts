package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.nio.charset.Charset
import java.sql.*
import javax.sql.rowset.serial.SerialBlob
import javax.sql.rowset.serial.SerialClob

open class VarcharColumn(table: Table, name: String) : NonNullableColumn<String>(name, table, String::class.java){
    override val nullable: NullableColumn<String?> = NullableVarcharColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: String) =
        statement.setString(position, value)

}

open class NullableVarcharColumn(table: Table, name: String) : NullableColumn<String?>(name, table, Types.VARCHAR, String::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) =
        statement.setString(position, value)

}

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

/**
 * Represents a database column whose type is converted from and to a Clob
 *
 * @param name The column name in the corresponding database table
 */
class ClobColumn(table: Table, name: String) : NonNullableColumn<Clob>(name, table, Clob::class.java){
    override val nullable: NullableColumn<Clob?> = NullableClobColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob) =
        statement.setClob(position, value)
    companion object {
        fun ofString(value: String) = SerialClob(value.toCharArray())
        fun serialize(blob: Clob) =
            String(blob.asciiStream.readAllBytes())
    }
}

class NullableClobColumn(table: Table, name: String) : NullableColumn<Clob?>(name, table, Types.CLOB, Clob::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob?) =
        statement.setClob(position, value)
}

class ByteArrayColumn(table: Table, name: String) : NonNullableColumn<ByteArray>(name, table, ByteArray::class.java){
    override val nullable: NullableColumn<ByteArray?> = NullableByteArrayColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray) =
        statement.setBytes(position, value)
}

class NullableByteArrayColumn(table: Table, name: String) : NullableColumn<ByteArray?>(name, table, Types.BINARY, ByteArray::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray?) =
        statement.setBytes(position, value)
}

