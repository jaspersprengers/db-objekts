package com.dbobjekts.statement.customsql

import com.dbobjekts.SQL
import com.dbobjekts.*
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Columns
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.result.*
import java.math.BigDecimal
import java.sql.Blob
import java.sql.Clob
import java.sql.Time
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class CustomSQLStatementBuilder(
    private val conn: ConnectionAdapter,
    private val sql: SQL,
    private val args: List<Any>
) {
    fun <T1> returning(ret: Returning1<T1>): SQLStatementExecutor<T1, ResultRow1<T1>> {
        val columns = listOf(ret.column1)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow1<T1>())
    }

    fun <T1, T2> returning(ret: Returning2<T1, T2>): SQLStatementExecutor<Tuple2<T1, T2>, ResultRow2<T1, T2>> {
        val columns = listOf(ret.column1, ret.column2)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow2<T1, T2>())
    }
    fun <T1, T2, T3> returning(ret: Returning3<T1, T2, T3>): SQLStatementExecutor<Tuple3<T1, T2, T3>, ResultRow3<T1, T2, T3>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow3<T1, T2, T3>())
    }
    fun <T1, T2, T3, T4> returning(ret: Returning4<T1, T2, T3, T4>): SQLStatementExecutor<Tuple4<T1, T2, T3, T4>, ResultRow4<T1, T2, T3, T4>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow4<T1, T2, T3, T4>())
    }
    fun <T1, T2, T3, T4, T5> returning(ret: Returning5<T1, T2, T3, T4, T5>): SQLStatementExecutor<Tuple5<T1, T2, T3, T4, T5>, ResultRow5<T1, T2, T3, T4, T5>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4, ret.column5)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow5<T1, T2, T3, T4, T5>())
    }
    fun <T1, T2, T3, T4, T5, T6> returning(ret: Returning6<T1, T2, T3, T4, T5, T6>): SQLStatementExecutor<Tuple6<T1, T2, T3, T4, T5, T6>, ResultRow6<T1, T2, T3, T4, T5, T6>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4, ret.column5, ret.column6)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow6<T1, T2, T3, T4, T5, T6>())
    }
    fun <T1, T2, T3, T4, T5, T6, T7> returning(ret: Returning7<T1, T2, T3, T4, T5, T6, T7>): SQLStatementExecutor<Tuple7<T1, T2, T3, T4, T5, T6, T7>, ResultRow7<T1, T2, T3, T4, T5, T6, T7>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4, ret.column5, ret.column6, ret.column7)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow7<T1, T2, T3, T4, T5, T6, T7>())
    }
    fun <T1, T2, T3, T4, T5, T6, T7, T8> returning(ret: Returning8<T1, T2, T3, T4, T5, T6, T7, T8>): SQLStatementExecutor<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4, ret.column5, ret.column6, ret.column7, ret.column8)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>())
    }
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> returning(ret: Returning9<T1, T2, T3, T4, T5, T6, T7, T8, T9>): SQLStatementExecutor<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        val columns = listOf(ret.column1, ret.column2, ret.column3, ret.column4, ret.column5, ret.column6, ret.column7, ret.column8, ret.column9)
        return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>())
    }
}

object ResultTypes {

    fun string(): Returning1<String> = Returning1(Columns.VARCHAR)
    fun stringNil(): Returning1<String?> = Returning1(Columns.VARCHAR_NIL)
    fun long(): Returning1<Long> = Returning1(Columns.LONG)
    fun longNil(): Returning1<Long?> = Returning1(Columns.LONG_NIL)
    fun int(): Returning1<Int> = Returning1(Columns.INTEGER)
    fun intNil(): Returning1<Int?> = Returning1(Columns.INTEGER_NIL)
    fun byteArray(): Returning1<ByteArray> = Returning1(Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning1<ByteArray?> = Returning1(Columns.BYTE_ARRAY_NIL)
    fun blob(): Returning1<Blob> = Returning1(Columns.BLOB)
    fun blobNil(): Returning1<Blob?> = Returning1(Columns.BLOB_NIL)
    fun clob(): Returning1<Clob> = Returning1(Columns.CLOB)
    fun clobNil(): Returning1<Clob?> = Returning1(Columns.CLOB_NIL)
    fun byte(): Returning1<Byte> = Returning1(Columns.BYTE)
    fun byteNil(): Returning1<Byte?> = Returning1(Columns.BYTE_NIL)
    fun boolean(): Returning1<Boolean> = Returning1(Columns.BOOLEAN)
    fun booleanNil(): Returning1<Boolean?> = Returning1(Columns.BOOLEAN_NIL)
    fun double(): Returning1<Double> = Returning1(Columns.DOUBLE)
    fun doubleNil(): Returning1<Double?> = Returning1(Columns.DOUBLE_NIL)
    fun float(): Returning1<Float> = Returning1(Columns.FLOAT)
    fun floatNil(): Returning1<Float?> = Returning1(Columns.FLOAT_NIL)
    fun bigDecimal(): Returning1<BigDecimal> = Returning1(Columns.BIGDECIMAL)
    fun bigDecimalNil(): Returning1<BigDecimal?> = Returning1(Columns.BIGDECIMAL_NIL)
    fun date(): Returning1<LocalDate> = Returning1(Columns.DATE)
    fun dateNil(): Returning1<LocalDate?> = Returning1(Columns.DATE_NIL)
    fun dateTime(): Returning1<LocalDateTime> = Returning1(Columns.DATETIME)
    fun dateTimeNil(): Returning1<LocalDateTime?> = Returning1(Columns.DATETIME_NIL)
    fun time(): Returning1<LocalTime> = Returning1(Columns.TIME)
    fun timeNil(): Returning1<LocalTime?> = Returning1(Columns.TIME_NIL)
    fun timeStamp(): Returning1<Instant> = Returning1(Columns.TIMESTAMP)
    fun timeStampNil(): Returning1<Instant?> = Returning1(Columns.TIMESTAMP_NIL)
    fun offsetDateTime(): Returning1<OffsetDateTime> = Returning1(Columns.OFFSET_DATETIME)
    fun offsetDateTimeNil(): Returning1<OffsetDateTime?> = Returning1(Columns.OFFSET_DATETIME_NIL)
}

class Returning1<T1>(internal val column1: Column<T1>) {
    fun string(): Returning2<T1, String> = Returning2(column1, Columns.VARCHAR)
    fun stringNil(): Returning2<T1, String?> = Returning2(column1, Columns.VARCHAR_NIL)
    fun long(): Returning2<T1, Long> = Returning2(column1, Columns.LONG)
    fun longNil(): Returning2<T1, Long?> = Returning2(column1, Columns.LONG_NIL)
    fun int(): Returning2<T1, Int> = Returning2(column1, Columns.INTEGER)
    fun intNil(): Returning2<T1, Int?> = Returning2(column1, Columns.INTEGER_NIL)
    fun byteArray(): Returning2<T1, ByteArray> = Returning2(column1, Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning2<T1, ByteArray?> = Returning2(column1, Columns.BYTE_ARRAY_NIL)
    fun blob(): Returning2<T1, Blob> = Returning2(column1, Columns.BLOB)
    fun blobNil(): Returning2<T1, Blob?> = Returning2(column1, Columns.BLOB_NIL)
    fun clob(): Returning2<T1, Clob> = Returning2(column1, Columns.CLOB)
    fun clobNil(): Returning2<T1, Clob?> = Returning2(column1, Columns.CLOB_NIL)
    fun byte(): Returning2<T1, Byte> = Returning2(column1, Columns.BYTE)
    fun byteNil(): Returning2<T1, Byte?> = Returning2(column1, Columns.BYTE_NIL)
    fun boolean(): Returning2<T1, Boolean> = Returning2(column1, Columns.BOOLEAN)
    fun booleanNil(): Returning2<T1, Boolean?> = Returning2(column1, Columns.BOOLEAN_NIL)
    fun double(): Returning2<T1, Double> = Returning2(column1, Columns.DOUBLE)
    fun doubleNil(): Returning2<T1, Double?> = Returning2(column1, Columns.DOUBLE_NIL)
    fun float(): Returning2<T1, Float> = Returning2(column1, Columns.FLOAT)
    fun floatNil(): Returning2<T1, Float?> = Returning2(column1, Columns.FLOAT_NIL)
    fun bigDecimal(): Returning2<T1, BigDecimal> = Returning2(column1, Columns.BIGDECIMAL)
    fun bigDecimalNil(): Returning2<T1, BigDecimal?> = Returning2(column1, Columns.BIGDECIMAL_NIL)
    fun date(): Returning2<T1, LocalDate> = Returning2(column1, Columns.DATE)
    fun dateNil(): Returning2<T1, LocalDate?> = Returning2(column1, Columns.DATE_NIL)
    fun dateTime(): Returning2<T1, LocalDateTime> = Returning2(column1, Columns.DATETIME)
    fun dateTimeNil(): Returning2<T1, LocalDateTime?> = Returning2(column1, Columns.DATETIME_NIL)
    fun time(): Returning2<T1, LocalTime> = Returning2(column1, Columns.TIME)
    fun timeNil(): Returning2<T1, LocalTime?> = Returning2(column1, Columns.TIME_NIL)
    fun timeStamp(): Returning2<T1, Instant> = Returning2(column1, Columns.TIMESTAMP)
    fun timeStampNil(): Returning2<T1, Instant?> = Returning2(column1, Columns.TIMESTAMP_NIL)
    fun offsetDateTime(): Returning2<T1, OffsetDateTime> = Returning2(column1, Columns.OFFSET_DATETIME)
    fun offsetDateTimeNil(): Returning2<T1, OffsetDateTime?> = Returning2(column1, Columns.OFFSET_DATETIME_NIL)

}

class Returning2<T1, T2>(internal val column1: Column<T1>, internal val column2: Column<T2>) {
    fun string(): Returning3<T1, T2, String> = Returning3(column1, column2, Columns.VARCHAR)
    fun stringNil(): Returning3<T1, T2, String?> = Returning3(column1, column2, Columns.VARCHAR_NIL)
    fun long(): Returning3<T1, T2, Long> = Returning3(column1, column2, Columns.LONG)
    fun longNil(): Returning3<T1, T2, Long?> = Returning3(column1, column2, Columns.LONG_NIL)
    fun int(): Returning3<T1, T2, Int> = Returning3(column1, column2, Columns.INTEGER)
    fun intNil(): Returning3<T1, T2, Int?> = Returning3(column1, column2, Columns.INTEGER_NIL)
    fun byteArray(): Returning3<T1, T2, ByteArray> = Returning3(column1, column2, Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning3<T1, T2, ByteArray?> = Returning3(column1, column2, Columns.BYTE_ARRAY_NIL)
    fun blob(): Returning3<T1, T2, Blob> = Returning3(column1, column2, Columns.BLOB)
    fun blobNil(): Returning3<T1, T2, Blob?> = Returning3(column1, column2, Columns.BLOB_NIL)
    fun clob(): Returning3<T1, T2, Clob> = Returning3(column1, column2, Columns.CLOB)
    fun clobNil(): Returning3<T1, T2, Clob?> = Returning3(column1, column2, Columns.CLOB_NIL)
    fun byte(): Returning3<T1, T2, Byte> = Returning3(column1, column2, Columns.BYTE)
    fun byteNil(): Returning3<T1, T2, Byte?> = Returning3(column1, column2, Columns.BYTE_NIL)
    fun boolean(): Returning3<T1, T2, Boolean> = Returning3(column1, column2, Columns.BOOLEAN)
    fun booleanNil(): Returning3<T1, T2, Boolean?> = Returning3(column1, column2, Columns.BOOLEAN_NIL)
    fun double(): Returning3<T1, T2, Double> = Returning3(column1, column2, Columns.DOUBLE)
    fun doubleNil(): Returning3<T1, T2, Double?> = Returning3(column1, column2, Columns.DOUBLE_NIL)
    fun float(): Returning3<T1, T2, Float> = Returning3(column1, column2, Columns.FLOAT)
    fun floatNil(): Returning3<T1, T2, Float?> = Returning3(column1, column2, Columns.FLOAT_NIL)
    fun bigDecimal(): Returning3<T1, T2, BigDecimal> = Returning3(column1, column2, Columns.BIGDECIMAL)
    fun bigDecimalNil(): Returning3<T1, T2, BigDecimal?> = Returning3(column1, column2, Columns.BIGDECIMAL_NIL)
    fun date(): Returning3<T1, T2, LocalDate> = Returning3(column1, column2, Columns.DATE)
    fun dateNil(): Returning3<T1, T2, LocalDate?> = Returning3(column1, column2, Columns.DATE_NIL)
    fun dateTime(): Returning3<T1, T2, LocalDateTime> = Returning3(column1, column2, Columns.DATETIME)
    fun dateTimeNil(): Returning3<T1, T2, LocalDateTime?> = Returning3(column1, column2, Columns.DATETIME_NIL)
    fun time(): Returning3<T1, T2, LocalTime> = Returning3(column1, column2, Columns.TIME)
    fun timeNil(): Returning3<T1, T2, LocalTime?> = Returning3(column1, column2, Columns.TIME_NIL)
    fun timeStamp(): Returning3<T1, T2, Instant> = Returning3(column1, column2, Columns.TIMESTAMP)
    fun timeStampNil(): Returning3<T1, T2, Instant?> = Returning3(column1, column2, Columns.TIMESTAMP_NIL)
    fun offsetDateTime(): Returning3<T1, T2, OffsetDateTime> = Returning3(column1, column2, Columns.OFFSET_DATETIME)
    fun offsetDateTimeNil(): Returning3<T1, T2, OffsetDateTime?> = Returning3(column1, column2, Columns.OFFSET_DATETIME_NIL)


}

class Returning3<T1, T2, T3>(internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>) {
    fun string(): Returning4<T1, T2, T3, String> = Returning4(column1, column2, column3, Columns.VARCHAR)
    fun stringNil(): Returning4<T1, T2, T3, String?> = Returning4(column1, column2, column3, Columns.VARCHAR_NIL)
    fun long(): Returning4<T1, T2, T3, Long> = Returning4(column1, column2, column3, Columns.LONG)
    fun longNil(): Returning4<T1, T2, T3, Long?> = Returning4(column1, column2, column3, Columns.LONG_NIL)
    fun int(): Returning4<T1, T2, T3, Int> = Returning4(column1, column2, column3, Columns.INTEGER)
    fun intNil(): Returning4<T1, T2, T3, Int?> = Returning4(column1, column2, column3, Columns.INTEGER_NIL)
    fun byteArray(): Returning4<T1, T2, T3, ByteArray> = Returning4(column1, column2, column3, Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning4<T1, T2, T3, ByteArray?> = Returning4(column1, column2, column3, Columns.BYTE_ARRAY_NIL)
    fun blob(): Returning4<T1, T2, T3, Blob> = Returning4(column1, column2, column3, Columns.BLOB)
    fun blobNil(): Returning4<T1, T2, T3, Blob?> = Returning4(column1, column2, column3, Columns.BLOB_NIL)
    fun clob(): Returning4<T1, T2, T3, Clob> = Returning4(column1, column2, column3, Columns.CLOB)
    fun clobNil(): Returning4<T1, T2, T3, Clob?> = Returning4(column1, column2, column3, Columns.CLOB_NIL)
    fun byte(): Returning4<T1, T2, T3, Byte> = Returning4(column1, column2, column3, Columns.BYTE)
    fun byteNil(): Returning4<T1, T2, T3, Byte?> = Returning4(column1, column2, column3, Columns.BYTE_NIL)
    fun boolean(): Returning4<T1, T2, T3, Boolean> = Returning4(column1, column2, column3, Columns.BOOLEAN)
    fun booleanNil(): Returning4<T1, T2, T3, Boolean?> = Returning4(column1, column2, column3, Columns.BOOLEAN_NIL)
    fun double(): Returning4<T1, T2, T3, Double> = Returning4(column1, column2, column3, Columns.DOUBLE)
    fun doubleNil(): Returning4<T1, T2, T3, Double?> = Returning4(column1, column2, column3, Columns.DOUBLE_NIL)
    fun float(): Returning4<T1, T2, T3, Float> = Returning4(column1, column2, column3, Columns.FLOAT)
    fun floatNil(): Returning4<T1, T2, T3, Float?> = Returning4(column1, column2, column3, Columns.FLOAT_NIL)
    fun bigDecimal(): Returning4<T1, T2, T3, BigDecimal> = Returning4(column1, column2, column3, Columns.BIGDECIMAL)
    fun bigDecimalNil(): Returning4<T1, T2, T3, BigDecimal?> = Returning4(column1, column2, column3, Columns.BIGDECIMAL_NIL)
    fun date(): Returning4<T1, T2, T3, LocalDate> = Returning4(column1, column2, column3, Columns.DATE)
    fun dateNil(): Returning4<T1, T2, T3, LocalDate?> = Returning4(column1, column2, column3, Columns.DATE_NIL)
    fun dateTime(): Returning4<T1, T2, T3, LocalDateTime> = Returning4(column1, column2, column3, Columns.DATETIME)
    fun dateTimeNil(): Returning4<T1, T2, T3, LocalDateTime?> = Returning4(column1, column2, column3, Columns.DATETIME_NIL)
    fun time(): Returning4<T1, T2, T3, LocalTime> = Returning4(column1, column2, column3, Columns.TIME)
    fun timeNil(): Returning4<T1, T2, T3, LocalTime?> = Returning4(column1, column2, column3, Columns.TIME_NIL)
    fun timeStamp(): Returning4<T1, T2, T3, Instant> = Returning4(column1, column2, column3, Columns.TIMESTAMP)
    fun timeStampNil(): Returning4<T1, T2, T3, Instant?> = Returning4(column1, column2, column3, Columns.TIMESTAMP_NIL)
    fun offsetDateTime(): Returning4<T1, T2, T3, OffsetDateTime> = Returning4(column1, column2, column3, Columns.OFFSET_DATETIME)
    fun offsetDateTimeNil(): Returning4<T1, T2, T3, OffsetDateTime?> = Returning4(column1, column2, column3, Columns.OFFSET_DATETIME_NIL)


}

class Returning4<T1, T2, T3, T4>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>
) {
    fun string(): Returning5<T1, T2, T3, T4, String> = Returning5(column1, column2, column3, column4, Columns.VARCHAR)
    fun stringNil(): Returning5<T1, T2, T3, T4, String?> = Returning5(column1, column2, column3, column4, Columns.VARCHAR_NIL)
    fun long(): Returning5<T1, T2, T3, T4, Long> = Returning5(column1, column2, column3, column4, Columns.LONG)
    fun longNil(): Returning5<T1, T2, T3, T4, Long?> = Returning5(column1, column2, column3, column4, Columns.LONG_NIL)
    fun int(): Returning5<T1, T2, T3, T4, Int> = Returning5(column1, column2, column3, column4, Columns.INTEGER)
    fun intNil(): Returning5<T1, T2, T3, T4, Int?> = Returning5(column1, column2, column3, column4, Columns.INTEGER_NIL)
    fun byteArray(): Returning5<T1, T2, T3, T4, ByteArray> = Returning5(column1, column2, column3, column4, Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning5<T1, T2, T3, T4, ByteArray?> = Returning5(column1, column2, column3, column4, Columns.BYTE_ARRAY_NIL)
    fun blob(): Returning5<T1, T2, T3, T4, Blob> = Returning5(column1, column2, column3, column4, Columns.BLOB)
    fun blobNil(): Returning5<T1, T2, T3, T4, Blob?> = Returning5(column1, column2, column3, column4, Columns.BLOB_NIL)
    fun clob(): Returning5<T1, T2, T3, T4, Clob> = Returning5(column1, column2, column3, column4, Columns.CLOB)
    fun clobNil(): Returning5<T1, T2, T3, T4, Clob?> = Returning5(column1, column2, column3, column4, Columns.CLOB_NIL)
    fun byte(): Returning5<T1, T2, T3, T4, Byte> = Returning5(column1, column2, column3, column4, Columns.BYTE)
    fun byteNil(): Returning5<T1, T2, T3, T4, Byte?> = Returning5(column1, column2, column3, column4, Columns.BYTE_NIL)
    fun boolean(): Returning5<T1, T2, T3, T4, Boolean> = Returning5(column1, column2, column3, column4, Columns.BOOLEAN)
    fun booleanNil(): Returning5<T1, T2, T3, T4, Boolean?> = Returning5(column1, column2, column3, column4, Columns.BOOLEAN_NIL)
    fun double(): Returning5<T1, T2, T3, T4, Double> = Returning5(column1, column2, column3, column4, Columns.DOUBLE)
    fun doubleNil(): Returning5<T1, T2, T3, T4, Double?> = Returning5(column1, column2, column3, column4, Columns.DOUBLE_NIL)
    fun float(): Returning5<T1, T2, T3, T4, Float> = Returning5(column1, column2, column3, column4, Columns.FLOAT)
    fun floatNil(): Returning5<T1, T2, T3, T4, Float?> = Returning5(column1, column2, column3, column4, Columns.FLOAT_NIL)
    fun bigDecimal(): Returning5<T1, T2, T3, T4, BigDecimal> = Returning5(column1, column2, column3, column4, Columns.BIGDECIMAL)
    fun bigDecimalNil(): Returning5<T1, T2, T3, T4, BigDecimal?> = Returning5(column1, column2, column3, column4, Columns.BIGDECIMAL_NIL)
    fun date(): Returning5<T1, T2, T3, T4, LocalDate> = Returning5(column1, column2, column3, column4, Columns.DATE)
    fun dateNil(): Returning5<T1, T2, T3, T4, LocalDate?> = Returning5(column1, column2, column3, column4, Columns.DATE_NIL)
    fun dateTime(): Returning5<T1, T2, T3, T4, LocalDateTime> = Returning5(column1, column2, column3, column4, Columns.DATETIME)
    fun dateTimeNil(): Returning5<T1, T2, T3, T4, LocalDateTime?> = Returning5(column1, column2, column3, column4, Columns.DATETIME_NIL)
    fun time(): Returning5<T1, T2, T3, T4, LocalTime> = Returning5(column1, column2, column3, column4, Columns.TIME)
    fun timeNil(): Returning5<T1, T2, T3, T4, LocalTime?> = Returning5(column1, column2, column3, column4, Columns.TIME_NIL)
    fun timeStamp(): Returning5<T1, T2, T3, T4, Instant> = Returning5(column1, column2, column3, column4, Columns.TIMESTAMP)
    fun timeStampNil(): Returning5<T1, T2, T3, T4, Instant?> = Returning5(column1, column2, column3, column4, Columns.TIMESTAMP_NIL)
    fun offsetDateTime(): Returning5<T1, T2, T3, T4, OffsetDateTime> =
        Returning5(column1, column2, column3, column4, Columns.OFFSET_DATETIME)

    fun offsetDateTimeNil(): Returning5<T1, T2, T3, T4, OffsetDateTime?> =
        Returning5(column1, column2, column3, column4, Columns.OFFSET_DATETIME_NIL)


}

class Returning5<T1, T2, T3, T4, T5>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>
) {
    fun string(): Returning6<T1, T2, T3, T4, T5, String> = Returning6(column1, column2, column3, column4, column5, Columns.VARCHAR)
    fun stringNil(): Returning6<T1, T2, T3, T4, T5, String?> = Returning6(column1, column2, column3, column4, column5, Columns.VARCHAR_NIL)
    fun long(): Returning6<T1, T2, T3, T4, T5, Long> = Returning6(column1, column2, column3, column4, column5, Columns.LONG)
    fun longNil(): Returning6<T1, T2, T3, T4, T5, Long?> = Returning6(column1, column2, column3, column4, column5, Columns.LONG_NIL)
    fun int(): Returning6<T1, T2, T3, T4, T5, Int> = Returning6(column1, column2, column3, column4, column5, Columns.INTEGER)
    fun intNil(): Returning6<T1, T2, T3, T4, T5, Int?> = Returning6(column1, column2, column3, column4, column5, Columns.INTEGER_NIL)
    fun byteArray(): Returning6<T1, T2, T3, T4, T5, ByteArray> = Returning6(column1, column2, column3, column4, column5, Columns.BYTE_ARRAY)
    fun byteArrayNil(): Returning6<T1, T2, T3, T4, T5, ByteArray?> =
        Returning6(column1, column2, column3, column4, column5, Columns.BYTE_ARRAY_NIL)

    fun blob(): Returning6<T1, T2, T3, T4, T5, Blob> = Returning6(column1, column2, column3, column4, column5, Columns.BLOB)
    fun blobNil(): Returning6<T1, T2, T3, T4, T5, Blob?> = Returning6(column1, column2, column3, column4, column5, Columns.BLOB_NIL)
    fun clob(): Returning6<T1, T2, T3, T4, T5, Clob> = Returning6(column1, column2, column3, column4, column5, Columns.CLOB)
    fun clobNil(): Returning6<T1, T2, T3, T4, T5, Clob?> = Returning6(column1, column2, column3, column4, column5, Columns.CLOB_NIL)
    fun byte(): Returning6<T1, T2, T3, T4, T5, Byte> = Returning6(column1, column2, column3, column4, column5, Columns.BYTE)
    fun byteNil(): Returning6<T1, T2, T3, T4, T5, Byte?> = Returning6(column1, column2, column3, column4, column5, Columns.BYTE_NIL)
    fun boolean(): Returning6<T1, T2, T3, T4, T5, Boolean> = Returning6(column1, column2, column3, column4, column5, Columns.BOOLEAN)
    fun booleanNil(): Returning6<T1, T2, T3, T4, T5, Boolean?> =
        Returning6(column1, column2, column3, column4, column5, Columns.BOOLEAN_NIL)

    fun double(): Returning6<T1, T2, T3, T4, T5, Double> = Returning6(column1, column2, column3, column4, column5, Columns.DOUBLE)
    fun doubleNil(): Returning6<T1, T2, T3, T4, T5, Double?> = Returning6(column1, column2, column3, column4, column5, Columns.DOUBLE_NIL)
    fun float(): Returning6<T1, T2, T3, T4, T5, Float> = Returning6(column1, column2, column3, column4, column5, Columns.FLOAT)
    fun floatNil(): Returning6<T1, T2, T3, T4, T5, Float?> = Returning6(column1, column2, column3, column4, column5, Columns.FLOAT_NIL)
    fun bigDecimal(): Returning6<T1, T2, T3, T4, T5, BigDecimal> =
        Returning6(column1, column2, column3, column4, column5, Columns.BIGDECIMAL)

    fun bigDecimalNil(): Returning6<T1, T2, T3, T4, T5, BigDecimal?> =
        Returning6(column1, column2, column3, column4, column5, Columns.BIGDECIMAL_NIL)

    fun date(): Returning6<T1, T2, T3, T4, T5, LocalDate> = Returning6(column1, column2, column3, column4, column5, Columns.DATE)
    fun dateNil(): Returning6<T1, T2, T3, T4, T5, LocalDate?> = Returning6(column1, column2, column3, column4, column5, Columns.DATE_NIL)
    fun dateTime(): Returning6<T1, T2, T3, T4, T5, LocalDateTime> =
        Returning6(column1, column2, column3, column4, column5, Columns.DATETIME)

    fun dateTimeNil(): Returning6<T1, T2, T3, T4, T5, LocalDateTime?> =
        Returning6(column1, column2, column3, column4, column5, Columns.DATETIME_NIL)

    fun time(): Returning6<T1, T2, T3, T4, T5, LocalTime> = Returning6(column1, column2, column3, column4, column5, Columns.TIME)
    fun timeNil(): Returning6<T1, T2, T3, T4, T5, LocalTime?> = Returning6(column1, column2, column3, column4, column5, Columns.TIME_NIL)
    fun timeStamp(): Returning6<T1, T2, T3, T4, T5, Instant> = Returning6(column1, column2, column3, column4, column5, Columns.TIMESTAMP)
    fun timeStampNil(): Returning6<T1, T2, T3, T4, T5, Instant?> =
        Returning6(column1, column2, column3, column4, column5, Columns.TIMESTAMP_NIL)

    fun offsetDateTime(): Returning6<T1, T2, T3, T4, T5, OffsetDateTime> =
        Returning6(column1, column2, column3, column4, column5, Columns.OFFSET_DATETIME)

    fun offsetDateTimeNil(): Returning6<T1, T2, T3, T4, T5, OffsetDateTime?> =
        Returning6(column1, column2, column3, column4, column5, Columns.OFFSET_DATETIME_NIL)


}

class Returning6<T1, T2, T3, T4, T5, T6>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>
) {
    fun string(): Returning7<T1, T2, T3, T4, T5, T6, String> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.VARCHAR)

    fun stringNil(): Returning7<T1, T2, T3, T4, T5, T6, String?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.VARCHAR_NIL)

    fun long(): Returning7<T1, T2, T3, T4, T5, T6, Long> = Returning7(column1, column2, column3, column4, column5, column6, Columns.LONG)
    fun longNil(): Returning7<T1, T2, T3, T4, T5, T6, Long?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.LONG_NIL)

    fun int(): Returning7<T1, T2, T3, T4, T5, T6, Int> = Returning7(column1, column2, column3, column4, column5, column6, Columns.INTEGER)
    fun intNil(): Returning7<T1, T2, T3, T4, T5, T6, Int?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.INTEGER_NIL)

    fun byteArray(): Returning7<T1, T2, T3, T4, T5, T6, ByteArray> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BYTE_ARRAY)

    fun byteArrayNil(): Returning7<T1, T2, T3, T4, T5, T6, ByteArray?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BYTE_ARRAY_NIL)

    fun blob(): Returning7<T1, T2, T3, T4, T5, T6, Blob> = Returning7(column1, column2, column3, column4, column5, column6, Columns.BLOB)
    fun blobNil(): Returning7<T1, T2, T3, T4, T5, T6, Blob?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BLOB_NIL)

    fun clob(): Returning7<T1, T2, T3, T4, T5, T6, Clob> = Returning7(column1, column2, column3, column4, column5, column6, Columns.CLOB)
    fun clobNil(): Returning7<T1, T2, T3, T4, T5, T6, Clob?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.CLOB_NIL)

    fun byte(): Returning7<T1, T2, T3, T4, T5, T6, Byte> = Returning7(column1, column2, column3, column4, column5, column6, Columns.BYTE)
    fun byteNil(): Returning7<T1, T2, T3, T4, T5, T6, Byte?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BYTE_NIL)

    fun boolean(): Returning7<T1, T2, T3, T4, T5, T6, Boolean> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BOOLEAN)

    fun booleanNil(): Returning7<T1, T2, T3, T4, T5, T6, Boolean?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BOOLEAN_NIL)

    fun double(): Returning7<T1, T2, T3, T4, T5, T6, Double> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DOUBLE)

    fun doubleNil(): Returning7<T1, T2, T3, T4, T5, T6, Double?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DOUBLE_NIL)

    fun float(): Returning7<T1, T2, T3, T4, T5, T6, Float> = Returning7(column1, column2, column3, column4, column5, column6, Columns.FLOAT)
    fun floatNil(): Returning7<T1, T2, T3, T4, T5, T6, Float?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.FLOAT_NIL)

    fun bigDecimal(): Returning7<T1, T2, T3, T4, T5, T6, BigDecimal> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BIGDECIMAL)

    fun bigDecimalNil(): Returning7<T1, T2, T3, T4, T5, T6, BigDecimal?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.BIGDECIMAL_NIL)

    fun date(): Returning7<T1, T2, T3, T4, T5, T6, LocalDate> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DATE)

    fun dateNil(): Returning7<T1, T2, T3, T4, T5, T6, LocalDate?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DATE_NIL)

    fun dateTime(): Returning7<T1, T2, T3, T4, T5, T6, LocalDateTime> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DATETIME)

    fun dateTimeNil(): Returning7<T1, T2, T3, T4, T5, T6, LocalDateTime?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.DATETIME_NIL)

    fun time(): Returning7<T1, T2, T3, T4, T5, T6, LocalTime> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.TIME)

    fun timeNil(): Returning7<T1, T2, T3, T4, T5, T6, LocalTime?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.TIME_NIL)

    fun timeStamp(): Returning7<T1, T2, T3, T4, T5, T6, Instant> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.TIMESTAMP)

    fun timeStampNil(): Returning7<T1, T2, T3, T4, T5, T6, Instant?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.TIMESTAMP_NIL)

    fun offsetDateTime(): Returning7<T1, T2, T3, T4, T5, T6, OffsetDateTime> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.OFFSET_DATETIME)

    fun offsetDateTimeNil(): Returning7<T1, T2, T3, T4, T5, T6, OffsetDateTime?> =
        Returning7(column1, column2, column3, column4, column5, column6, Columns.OFFSET_DATETIME_NIL)


}

class Returning7<T1, T2, T3, T4, T5, T6, T7>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>
) {
    fun string(): Returning8<T1, T2, T3, T4, T5, T6, T7, String> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.VARCHAR)

    fun stringNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, String?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.VARCHAR_NIL)

    fun long(): Returning8<T1, T2, T3, T4, T5, T6, T7, Long> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.LONG)

    fun longNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Long?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.LONG_NIL)

    fun int(): Returning8<T1, T2, T3, T4, T5, T6, T7, Int> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.INTEGER)

    fun intNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Int?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.INTEGER_NIL)

    fun byteArray(): Returning8<T1, T2, T3, T4, T5, T6, T7, ByteArray> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BYTE_ARRAY)

    fun byteArrayNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, ByteArray?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BYTE_ARRAY_NIL)

    fun blob(): Returning8<T1, T2, T3, T4, T5, T6, T7, Blob> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BLOB)

    fun blobNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Blob?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BLOB_NIL)

    fun clob(): Returning8<T1, T2, T3, T4, T5, T6, T7, Clob> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.CLOB)

    fun clobNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Clob?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.CLOB_NIL)

    fun byte(): Returning8<T1, T2, T3, T4, T5, T6, T7, Byte> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BYTE)

    fun byteNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Byte?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BYTE_NIL)

    fun boolean(): Returning8<T1, T2, T3, T4, T5, T6, T7, Boolean> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BOOLEAN)

    fun booleanNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Boolean?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BOOLEAN_NIL)

    fun double(): Returning8<T1, T2, T3, T4, T5, T6, T7, Double> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DOUBLE)

    fun doubleNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Double?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DOUBLE_NIL)

    fun float(): Returning8<T1, T2, T3, T4, T5, T6, T7, Float> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.FLOAT)

    fun floatNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Float?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.FLOAT_NIL)

    fun bigDecimal(): Returning8<T1, T2, T3, T4, T5, T6, T7, BigDecimal> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BIGDECIMAL)

    fun bigDecimalNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, BigDecimal?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.BIGDECIMAL_NIL)

    fun date(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalDate> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DATE)

    fun dateNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalDate?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DATE_NIL)

    fun dateTime(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalDateTime> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DATETIME)

    fun dateTimeNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalDateTime?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.DATETIME_NIL)

    fun time(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalTime> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.TIME)

    fun timeNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, LocalTime?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.TIME_NIL)

    fun timeStamp(): Returning8<T1, T2, T3, T4, T5, T6, T7, Instant> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.TIMESTAMP)

    fun timeStampNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, Instant?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.TIMESTAMP_NIL)

    fun offsetDateTime(): Returning8<T1, T2, T3, T4, T5, T6, T7, OffsetDateTime> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.OFFSET_DATETIME)

    fun offsetDateTimeNil(): Returning8<T1, T2, T3, T4, T5, T6, T7, OffsetDateTime?> =
        Returning8(column1, column2, column3, column4, column5, column6, column7, Columns.OFFSET_DATETIME_NIL)


}

class Returning8<T1, T2, T3, T4, T5, T6, T7, T8>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>,
    internal val column8: Column<T8>
) {
    fun string(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, String> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.VARCHAR)

    fun stringNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, String?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.VARCHAR_NIL)

    fun long(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Long> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.LONG)

    fun longNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Long?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.LONG_NIL)

    fun int(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Int> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.INTEGER)

    fun intNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Int?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.INTEGER_NIL)

    fun byteArray(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, ByteArray> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BYTE_ARRAY)

    fun byteArrayNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, ByteArray?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BYTE_ARRAY_NIL)

    fun blob(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Blob> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BLOB)

    fun blobNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Blob?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BLOB_NIL)

    fun clob(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Clob> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.CLOB)

    fun clobNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Clob?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.CLOB_NIL)

    fun byte(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Byte> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BYTE)

    fun byteNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Byte?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BYTE_NIL)

    fun boolean(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Boolean> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BOOLEAN)

    fun booleanNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Boolean?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BOOLEAN_NIL)

    fun double(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Double> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DOUBLE)

    fun doubleNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Double?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DOUBLE_NIL)

    fun float(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Float> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.FLOAT)

    fun floatNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Float?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.FLOAT_NIL)

    fun bigDecimal(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, BigDecimal> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BIGDECIMAL)

    fun bigDecimalNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, BigDecimal?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.BIGDECIMAL_NIL)

    fun date(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDate> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DATE)

    fun dateNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDate?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DATE_NIL)

    fun dateTime(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDateTime> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DATETIME)

    fun dateTimeNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDateTime?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.DATETIME_NIL)

    fun time(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalTime> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.TIME)

    fun timeNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, LocalTime?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.TIME_NIL)

    fun timeStamp(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Instant> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.TIMESTAMP)

    fun timeStampNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, Instant?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.TIMESTAMP_NIL)

    fun offsetDateTime(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, OffsetDateTime> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.OFFSET_DATETIME)

    fun offsetDateTimeNil(): Returning9<T1, T2, T3, T4, T5, T6, T7, T8, OffsetDateTime?> =
        Returning9(column1, column2, column3, column4, column5, column6, column7, column8, Columns.OFFSET_DATETIME_NIL)


}

class Returning9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>,
    internal val column8: Column<T8>,
    internal val column9: Column<T9>
)
