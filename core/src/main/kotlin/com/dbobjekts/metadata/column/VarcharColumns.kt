package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.nio.charset.Charset
import java.sql.*
import javax.sql.rowset.serial.SerialBlob
import javax.sql.rowset.serial.SerialClob

open class VarcharColumn(table: Table, name: String) : NonNullableColumn<String>(name, table){
    override val nullable: NullableColumn<String?> = NullableVarcharColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: String) =
        statement.setString(position, value)

    override val valueClass: Class<*> = String::class.java
}

open class NullableVarcharColumn(table: Table, name: String) : NullableColumn<String?>(name, table, Types.VARCHAR){
    override fun getValue(position: Int, resultSet: ResultSet): String? = resultSet.getString(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: String?) =
        statement.setString(position, value)

    override val valueClass: Class<*> = String::class.java
}

/**
 * Represents a database column whose type is converted from and to a Blob
 *
 * @param name The column name in the corresponding database table
 */
class BlobColumn(table: Table, name: String) : NonNullableColumn<Blob>(name, table){
    override val nullable: NullableColumn<Blob?> = NullableBlobColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Blob? = resultSet.getBlob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Blob) =
        statement.setBlob(position, value)
    override val valueClass: Class<*> = Blob::class.java
    companion object {
        fun ofString(value: String, charset: Charset = Charset.defaultCharset()) =
            SerialBlob(value.toByteArray(charset))
        fun serialize(blob: Blob) =
            String(blob.binaryStream.readAllBytes())
    }
}

class NullableBlobColumn(table: Table, name: String) : NullableColumn<Blob?>(name, table, Types.BLOB){
    override fun getValue(position: Int, resultSet: ResultSet): Blob? = resultSet.getBlob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Blob?) =
        statement.setBlob(position, value)
    override val valueClass: Class<*> = Blob::class.java
}

/**
 * Represents a database column whose type is converted from and to a Clob
 *
 * @param name The column name in the corresponding database table
 */
class ClobColumn(table: Table, name: String) : NonNullableColumn<Clob>(name, table){
    override val nullable: NullableColumn<Clob?> = NullableClobColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob) =
        statement.setClob(position, value)
    companion object {
        fun ofString(value: String) = SerialClob(value.toCharArray())
    }
    override val valueClass: Class<*> = Clob::class.java
}

class NullableClobColumn(table: Table, name: String) : NullableColumn<Clob?>(name, table, Types.CLOB){
    override fun getValue(position: Int, resultSet: ResultSet): Clob? = resultSet.getClob(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: Clob?) =
        statement.setClob(position, value)
    override val valueClass: Class<*> = Clob::class.java
}

class ByteArrayColumn(table: Table, name: String) : NonNullableColumn<ByteArray>(name, table){
    override val nullable: NullableColumn<ByteArray?> = NullableByteArrayColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray) =
        statement.setBytes(position, value)
    override val valueClass: Class<*> = ByteArray::class.java
}

class NullableByteArrayColumn(table: Table, name: String) : NullableColumn<ByteArray?>(name, table, Types.BINARY){
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray?) =
        statement.setBytes(position, value)
    override val valueClass: Class<*> = ByteArray::class.java
}

