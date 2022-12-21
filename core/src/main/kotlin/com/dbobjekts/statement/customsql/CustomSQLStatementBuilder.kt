package com.dbobjekts.statement.customsql

import com.dbobjekts.api.*
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.util.StatementLogger
import java.math.BigDecimal
import java.sql.Blob
import java.sql.Clob
import java.time.*

class CustomSQLStatementBuilder(
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>,
    private val statementLog: StatementLogger
) {

    fun execute(): Long = SQLStatementExecutor<Long?, ResultRow1<Long>>(semaphore, conn, sql, args.toList()).execute()
    fun withResultTypes() = CustomSQLSelectStatementBuilder(semaphore, conn, sql, args)
}

class CustomSQLSelectStatementBuilder(
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns1<String> = Returns1(ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    fun stringNil(): Returns1<String?> = Returns1(ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    fun long(): Returns1<Long> = Returns1(ColumnFactory.LONG, semaphore, conn, sql, args)
    fun longNil(): Returns1<Long?> = Returns1(ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    fun int(): Returns1<Int> = Returns1(ColumnFactory.INTEGER, semaphore, conn, sql, args)
    fun intNil(): Returns1<Int?> = Returns1(ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    fun byteArray(): Returns1<ByteArray> = Returns1(ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    fun byteArrayNil(): Returns1<ByteArray?> = Returns1(ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    fun blob(): Returns1<Blob> = Returns1(ColumnFactory.BLOB, semaphore, conn, sql, args)
    fun blobNil(): Returns1<Blob?> = Returns1(ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    fun clob(): Returns1<Clob> = Returns1(ColumnFactory.CLOB, semaphore, conn, sql, args)
    fun clobNil(): Returns1<Clob?> = Returns1(ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    fun byte(): Returns1<Byte> = Returns1(ColumnFactory.BYTE, semaphore, conn, sql, args)
    fun byteNil(): Returns1<Byte?> = Returns1(ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    fun boolean(): Returns1<Boolean> = Returns1(ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    fun booleanNil(): Returns1<Boolean?> = Returns1(ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    fun double(): Returns1<Double> = Returns1(ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    fun doubleNil(): Returns1<Double?> = Returns1(ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    fun float(): Returns1<Float> = Returns1(ColumnFactory.FLOAT, semaphore, conn, sql, args)
    fun floatNil(): Returns1<Float?> = Returns1(ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    fun bigDecimal(): Returns1<BigDecimal> = Returns1(ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    fun bigDecimalNil(): Returns1<BigDecimal?> = Returns1(ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    fun date(): Returns1<LocalDate> = Returns1(ColumnFactory.DATE, semaphore, conn, sql, args)
    fun dateNil(): Returns1<LocalDate?> = Returns1(ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    fun dateTime(): Returns1<LocalDateTime> = Returns1(ColumnFactory.DATETIME, semaphore, conn, sql, args)
    fun dateTimeNil(): Returns1<LocalDateTime?> = Returns1(ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    fun time(): Returns1<LocalTime> = Returns1(ColumnFactory.TIME, semaphore, conn, sql, args)
    fun timeNil(): Returns1<LocalTime?> = Returns1(ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    fun timeStamp(): Returns1<Instant> = Returns1(ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    fun timeStampNil(): Returns1<Instant?> = Returns1(ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    fun offsetDateTime(): Returns1<OffsetDateTime> = Returns1(ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    fun offsetDateTimeNil(): Returns1<OffsetDateTime?> = Returns1(ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

}

class Returns1<T1>(
    internal val column1: Column<T1>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns2<T1, String> = Returns2(column1, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    fun stringNil(): Returns2<T1, String?> = Returns2(column1, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    fun long(): Returns2<T1, Long> = Returns2(column1, ColumnFactory.LONG, semaphore, conn, sql, args)
    fun longNil(): Returns2<T1, Long?> = Returns2(column1, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    fun int(): Returns2<T1, Int> = Returns2(column1, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    fun intNil(): Returns2<T1, Int?> = Returns2(column1, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    fun byteArray(): Returns2<T1, ByteArray> = Returns2(column1, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    fun byteArrayNil(): Returns2<T1, ByteArray?> = Returns2(column1, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    fun blob(): Returns2<T1, Blob> = Returns2(column1, ColumnFactory.BLOB, semaphore, conn, sql, args)
    fun blobNil(): Returns2<T1, Blob?> = Returns2(column1, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    fun clob(): Returns2<T1, Clob> = Returns2(column1, ColumnFactory.CLOB, semaphore, conn, sql, args)
    fun clobNil(): Returns2<T1, Clob?> = Returns2(column1, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    fun byte(): Returns2<T1, Byte> = Returns2(column1, ColumnFactory.BYTE, semaphore, conn, sql, args)
    fun byteNil(): Returns2<T1, Byte?> = Returns2(column1, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    fun boolean(): Returns2<T1, Boolean> = Returns2(column1, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    fun booleanNil(): Returns2<T1, Boolean?> = Returns2(column1, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    fun double(): Returns2<T1, Double> = Returns2(column1, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    fun doubleNil(): Returns2<T1, Double?> = Returns2(column1, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    fun float(): Returns2<T1, Float> = Returns2(column1, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    fun floatNil(): Returns2<T1, Float?> = Returns2(column1, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    fun bigDecimal(): Returns2<T1, BigDecimal> = Returns2(column1, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    fun bigDecimalNil(): Returns2<T1, BigDecimal?> = Returns2(column1, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    fun date(): Returns2<T1, LocalDate> = Returns2(column1, ColumnFactory.DATE, semaphore, conn, sql, args)
    fun dateNil(): Returns2<T1, LocalDate?> = Returns2(column1, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    fun dateTime(): Returns2<T1, LocalDateTime> = Returns2(column1, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    fun dateTimeNil(): Returns2<T1, LocalDateTime?> = Returns2(column1, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    fun time(): Returns2<T1, LocalTime> = Returns2(column1, ColumnFactory.TIME, semaphore, conn, sql, args)
    fun timeNil(): Returns2<T1, LocalTime?> = Returns2(column1, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    fun timeStamp(): Returns2<T1, Instant> = Returns2(column1, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    fun timeStampNil(): Returns2<T1, Instant?> = Returns2(column1, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    fun offsetDateTime(): Returns2<T1, OffsetDateTime> = Returns2(column1, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    fun offsetDateTimeNil(): Returns2<T1, OffsetDateTime?> =
        Returns2(column1, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1), ResultRow1<T1>())
    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (T1) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

class Returns2<T1, T2>(
    internal val column1: Column<T1>, internal val column2: Column<T2>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns3<T1, T2, String> = Returns3(column1, column2, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    fun stringNil(): Returns3<T1, T2, String?> = Returns3(column1, column2, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    fun long(): Returns3<T1, T2, Long> = Returns3(column1, column2, ColumnFactory.LONG, semaphore, conn, sql, args)
    fun longNil(): Returns3<T1, T2, Long?> = Returns3(column1, column2, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    fun int(): Returns3<T1, T2, Int> = Returns3(column1, column2, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    fun intNil(): Returns3<T1, T2, Int?> = Returns3(column1, column2, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    fun byteArray(): Returns3<T1, T2, ByteArray> = Returns3(column1, column2, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    fun byteArrayNil(): Returns3<T1, T2, ByteArray?> = Returns3(column1, column2, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    fun blob(): Returns3<T1, T2, Blob> = Returns3(column1, column2, ColumnFactory.BLOB, semaphore, conn, sql, args)
    fun blobNil(): Returns3<T1, T2, Blob?> = Returns3(column1, column2, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    fun clob(): Returns3<T1, T2, Clob> = Returns3(column1, column2, ColumnFactory.CLOB, semaphore, conn, sql, args)
    fun clobNil(): Returns3<T1, T2, Clob?> = Returns3(column1, column2, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    fun byte(): Returns3<T1, T2, Byte> = Returns3(column1, column2, ColumnFactory.BYTE, semaphore, conn, sql, args)
    fun byteNil(): Returns3<T1, T2, Byte?> = Returns3(column1, column2, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    fun boolean(): Returns3<T1, T2, Boolean> = Returns3(column1, column2, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    fun booleanNil(): Returns3<T1, T2, Boolean?> = Returns3(column1, column2, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    fun double(): Returns3<T1, T2, Double> = Returns3(column1, column2, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    fun doubleNil(): Returns3<T1, T2, Double?> = Returns3(column1, column2, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    fun float(): Returns3<T1, T2, Float> = Returns3(column1, column2, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    fun floatNil(): Returns3<T1, T2, Float?> = Returns3(column1, column2, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    fun bigDecimal(): Returns3<T1, T2, BigDecimal> = Returns3(column1, column2, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    fun bigDecimalNil(): Returns3<T1, T2, BigDecimal?> =
        Returns3(column1, column2, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns3<T1, T2, LocalDate> = Returns3(column1, column2, ColumnFactory.DATE, semaphore, conn, sql, args)
    fun dateNil(): Returns3<T1, T2, LocalDate?> = Returns3(column1, column2, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    fun dateTime(): Returns3<T1, T2, LocalDateTime> = Returns3(column1, column2, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    fun dateTimeNil(): Returns3<T1, T2, LocalDateTime?> = Returns3(column1, column2, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    fun time(): Returns3<T1, T2, LocalTime> = Returns3(column1, column2, ColumnFactory.TIME, semaphore, conn, sql, args)
    fun timeNil(): Returns3<T1, T2, LocalTime?> = Returns3(column1, column2, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    fun timeStamp(): Returns3<T1, T2, Instant> = Returns3(column1, column2, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    fun timeStampNil(): Returns3<T1, T2, Instant?> = Returns3(column1, column2, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    fun offsetDateTime(): Returns3<T1, T2, OffsetDateTime> =
        Returns3(column1, column2, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns3<T1, T2, OffsetDateTime?> =
        Returns3(column1, column2, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2), ResultRow2<T1, T2>())
    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple2<T1, T2>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}


class Returns3<T1, T2, T3>(
    internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns4<T1, T2, T3, String> = Returns4(column1, column2, column3, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    fun stringNil(): Returns4<T1, T2, T3, String?> =
        Returns4(column1, column2, column3, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)

    fun long(): Returns4<T1, T2, T3, Long> = Returns4(column1, column2, column3, ColumnFactory.LONG, semaphore, conn, sql, args)
    fun longNil(): Returns4<T1, T2, T3, Long?> = Returns4(column1, column2, column3, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    fun int(): Returns4<T1, T2, T3, Int> = Returns4(column1, column2, column3, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    fun intNil(): Returns4<T1, T2, T3, Int?> = Returns4(column1, column2, column3, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    fun byteArray(): Returns4<T1, T2, T3, ByteArray> =
        Returns4(column1, column2, column3, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)

    fun byteArrayNil(): Returns4<T1, T2, T3, ByteArray?> =
        Returns4(column1, column2, column3, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)

    fun blob(): Returns4<T1, T2, T3, Blob> = Returns4(column1, column2, column3, ColumnFactory.BLOB, semaphore, conn, sql, args)
    fun blobNil(): Returns4<T1, T2, T3, Blob?> = Returns4(column1, column2, column3, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    fun clob(): Returns4<T1, T2, T3, Clob> = Returns4(column1, column2, column3, ColumnFactory.CLOB, semaphore, conn, sql, args)
    fun clobNil(): Returns4<T1, T2, T3, Clob?> = Returns4(column1, column2, column3, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    fun byte(): Returns4<T1, T2, T3, Byte> = Returns4(column1, column2, column3, ColumnFactory.BYTE, semaphore, conn, sql, args)
    fun byteNil(): Returns4<T1, T2, T3, Byte?> = Returns4(column1, column2, column3, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    fun boolean(): Returns4<T1, T2, T3, Boolean> = Returns4(column1, column2, column3, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    fun booleanNil(): Returns4<T1, T2, T3, Boolean?> =
        Returns4(column1, column2, column3, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)

    fun double(): Returns4<T1, T2, T3, Double> = Returns4(column1, column2, column3, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    fun doubleNil(): Returns4<T1, T2, T3, Double?> =
        Returns4(column1, column2, column3, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)

    fun float(): Returns4<T1, T2, T3, Float> = Returns4(column1, column2, column3, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    fun floatNil(): Returns4<T1, T2, T3, Float?> = Returns4(column1, column2, column3, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    fun bigDecimal(): Returns4<T1, T2, T3, BigDecimal> =
        Returns4(column1, column2, column3, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)

    fun bigDecimalNil(): Returns4<T1, T2, T3, BigDecimal?> =
        Returns4(column1, column2, column3, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns4<T1, T2, T3, LocalDate> = Returns4(column1, column2, column3, ColumnFactory.DATE, semaphore, conn, sql, args)
    fun dateNil(): Returns4<T1, T2, T3, LocalDate?> =
        Returns4(column1, column2, column3, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns4<T1, T2, T3, LocalDateTime> =
        Returns4(column1, column2, column3, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns4<T1, T2, T3, LocalDateTime?> =
        Returns4(column1, column2, column3, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)

    fun time(): Returns4<T1, T2, T3, LocalTime> = Returns4(column1, column2, column3, ColumnFactory.TIME, semaphore, conn, sql, args)
    fun timeNil(): Returns4<T1, T2, T3, LocalTime?> =
        Returns4(column1, column2, column3, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns4<T1, T2, T3, Instant> =
        Returns4(column1, column2, column3, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)

    fun timeStampNil(): Returns4<T1, T2, T3, Instant?> =
        Returns4(column1, column2, column3, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)

    fun offsetDateTime(): Returns4<T1, T2, T3, OffsetDateTime> =
        Returns4(column1, column2, column3, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns4<T1, T2, T3, OffsetDateTime?> =
        Returns4(column1, column2, column3, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() =
        SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3), ResultRow3<T1, T2, T3>())

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple3<T1, T2, T3>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}


class Returns4<T1, T2, T3, T4>(
    internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns5<T1, T2, T3, T4, String> =
        Returns5(column1, column2, column3, column4, ColumnFactory.VARCHAR, semaphore, conn, sql, args)

    fun stringNil(): Returns5<T1, T2, T3, T4, String?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)

    fun long(): Returns5<T1, T2, T3, T4, Long> =
        Returns5(column1, column2, column3, column4, ColumnFactory.LONG, semaphore, conn, sql, args)

    fun longNil(): Returns5<T1, T2, T3, T4, Long?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)

    fun int(): Returns5<T1, T2, T3, T4, Int> =
        Returns5(column1, column2, column3, column4, ColumnFactory.INTEGER, semaphore, conn, sql, args)

    fun intNil(): Returns5<T1, T2, T3, T4, Int?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)

    fun byteArray(): Returns5<T1, T2, T3, T4, ByteArray> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)

    fun byteArrayNil(): Returns5<T1, T2, T3, T4, ByteArray?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)

    fun blob(): Returns5<T1, T2, T3, T4, Blob> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BLOB, semaphore, conn, sql, args)

    fun blobNil(): Returns5<T1, T2, T3, T4, Blob?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)

    fun clob(): Returns5<T1, T2, T3, T4, Clob> =
        Returns5(column1, column2, column3, column4, ColumnFactory.CLOB, semaphore, conn, sql, args)

    fun clobNil(): Returns5<T1, T2, T3, T4, Clob?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)

    fun byte(): Returns5<T1, T2, T3, T4, Byte> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BYTE, semaphore, conn, sql, args)

    fun byteNil(): Returns5<T1, T2, T3, T4, Byte?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)

    fun boolean(): Returns5<T1, T2, T3, T4, Boolean> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)

    fun booleanNil(): Returns5<T1, T2, T3, T4, Boolean?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)

    fun double(): Returns5<T1, T2, T3, T4, Double> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DOUBLE, semaphore, conn, sql, args)

    fun doubleNil(): Returns5<T1, T2, T3, T4, Double?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)

    fun float(): Returns5<T1, T2, T3, T4, Float> =
        Returns5(column1, column2, column3, column4, ColumnFactory.FLOAT, semaphore, conn, sql, args)

    fun floatNil(): Returns5<T1, T2, T3, T4, Float?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)

    fun bigDecimal(): Returns5<T1, T2, T3, T4, BigDecimal> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)

    fun bigDecimalNil(): Returns5<T1, T2, T3, T4, BigDecimal?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns5<T1, T2, T3, T4, LocalDate> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DATE, semaphore, conn, sql, args)

    fun dateNil(): Returns5<T1, T2, T3, T4, LocalDate?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns5<T1, T2, T3, T4, LocalDateTime> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns5<T1, T2, T3, T4, LocalDateTime?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)

    fun time(): Returns5<T1, T2, T3, T4, LocalTime> =
        Returns5(column1, column2, column3, column4, ColumnFactory.TIME, semaphore, conn, sql, args)

    fun timeNil(): Returns5<T1, T2, T3, T4, LocalTime?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns5<T1, T2, T3, T4, Instant> =
        Returns5(column1, column2, column3, column4, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)

    fun timeStampNil(): Returns5<T1, T2, T3, T4, Instant?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)

    fun offsetDateTime(): Returns5<T1, T2, T3, T4, OffsetDateTime> =
        Returns5(column1, column2, column3, column4, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns5<T1, T2, T3, T4, OffsetDateTime?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() =
        SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4), ResultRow4<T1, T2, T3, T4>())

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple4<T1, T2, T3, T4>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}


class Returns5<T1, T2, T3, T4, T5>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns6<T1, T2, T3, T4, T5, String> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.VARCHAR, semaphore, conn, sql, args)

    fun stringNil(): Returns6<T1, T2, T3, T4, T5, String?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)

    fun long(): Returns6<T1, T2, T3, T4, T5, Long> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.LONG, semaphore, conn, sql, args)

    fun longNil(): Returns6<T1, T2, T3, T4, T5, Long?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)

    fun int(): Returns6<T1, T2, T3, T4, T5, Int> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.INTEGER, semaphore, conn, sql, args)

    fun intNil(): Returns6<T1, T2, T3, T4, T5, Int?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)

    fun byteArray(): Returns6<T1, T2, T3, T4, T5, ByteArray> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)

    fun byteArrayNil(): Returns6<T1, T2, T3, T4, T5, ByteArray?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)

    fun blob(): Returns6<T1, T2, T3, T4, T5, Blob> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BLOB, semaphore, conn, sql, args)

    fun blobNil(): Returns6<T1, T2, T3, T4, T5, Blob?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)

    fun clob(): Returns6<T1, T2, T3, T4, T5, Clob> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.CLOB, semaphore, conn, sql, args)

    fun clobNil(): Returns6<T1, T2, T3, T4, T5, Clob?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)

    fun byte(): Returns6<T1, T2, T3, T4, T5, Byte> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BYTE, semaphore, conn, sql, args)

    fun byteNil(): Returns6<T1, T2, T3, T4, T5, Byte?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)

    fun boolean(): Returns6<T1, T2, T3, T4, T5, Boolean> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)

    fun booleanNil(): Returns6<T1, T2, T3, T4, T5, Boolean?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)

    fun double(): Returns6<T1, T2, T3, T4, T5, Double> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DOUBLE, semaphore, conn, sql, args)

    fun doubleNil(): Returns6<T1, T2, T3, T4, T5, Double?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)

    fun float(): Returns6<T1, T2, T3, T4, T5, Float> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.FLOAT, semaphore, conn, sql, args)

    fun floatNil(): Returns6<T1, T2, T3, T4, T5, Float?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)

    fun bigDecimal(): Returns6<T1, T2, T3, T4, T5, BigDecimal> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)

    fun bigDecimalNil(): Returns6<T1, T2, T3, T4, T5, BigDecimal?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns6<T1, T2, T3, T4, T5, LocalDate> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DATE, semaphore, conn, sql, args)

    fun dateNil(): Returns6<T1, T2, T3, T4, T5, LocalDate?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns6<T1, T2, T3, T4, T5, LocalDateTime> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns6<T1, T2, T3, T4, T5, LocalDateTime?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)

    fun time(): Returns6<T1, T2, T3, T4, T5, LocalTime> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.TIME, semaphore, conn, sql, args)

    fun timeNil(): Returns6<T1, T2, T3, T4, T5, LocalTime?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns6<T1, T2, T3, T4, T5, Instant> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)

    fun timeStampNil(): Returns6<T1, T2, T3, T4, T5, Instant?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)

    fun offsetDateTime(): Returns6<T1, T2, T3, T4, T5, OffsetDateTime> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns6<T1, T2, T3, T4, T5, OffsetDateTime?> =
        Returns6(column1, column2, column3, column4, column5, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(
        semaphore,
        conn,
        sql,
        args.toList(),
        listOf(column1, column2, column3, column4, column5),
        ResultRow5<T1, T2, T3, T4, T5>()
    )

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple5<T1, T2, T3, T4, T5>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}


class Returns6<T1, T2, T3, T4, T5, T6>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns7<T1, T2, T3, T4, T5, T6, String> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.VARCHAR, semaphore, conn, sql, args)

    fun stringNil(): Returns7<T1, T2, T3, T4, T5, T6, String?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)

    fun long(): Returns7<T1, T2, T3, T4, T5, T6, Long> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.LONG, semaphore, conn, sql, args)

    fun longNil(): Returns7<T1, T2, T3, T4, T5, T6, Long?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)

    fun int(): Returns7<T1, T2, T3, T4, T5, T6, Int> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.INTEGER, semaphore, conn, sql, args)

    fun intNil(): Returns7<T1, T2, T3, T4, T5, T6, Int?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)

    fun byteArray(): Returns7<T1, T2, T3, T4, T5, T6, ByteArray> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)

    fun byteArrayNil(): Returns7<T1, T2, T3, T4, T5, T6, ByteArray?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)

    fun blob(): Returns7<T1, T2, T3, T4, T5, T6, Blob> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BLOB, semaphore, conn, sql, args)

    fun blobNil(): Returns7<T1, T2, T3, T4, T5, T6, Blob?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)

    fun clob(): Returns7<T1, T2, T3, T4, T5, T6, Clob> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.CLOB, semaphore, conn, sql, args)

    fun clobNil(): Returns7<T1, T2, T3, T4, T5, T6, Clob?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)

    fun byte(): Returns7<T1, T2, T3, T4, T5, T6, Byte> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE, semaphore, conn, sql, args)

    fun byteNil(): Returns7<T1, T2, T3, T4, T5, T6, Byte?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)

    fun boolean(): Returns7<T1, T2, T3, T4, T5, T6, Boolean> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)

    fun booleanNil(): Returns7<T1, T2, T3, T4, T5, T6, Boolean?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)

    fun double(): Returns7<T1, T2, T3, T4, T5, T6, Double> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DOUBLE, semaphore, conn, sql, args)

    fun doubleNil(): Returns7<T1, T2, T3, T4, T5, T6, Double?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)

    fun float(): Returns7<T1, T2, T3, T4, T5, T6, Float> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.FLOAT, semaphore, conn, sql, args)

    fun floatNil(): Returns7<T1, T2, T3, T4, T5, T6, Float?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)

    fun bigDecimal(): Returns7<T1, T2, T3, T4, T5, T6, BigDecimal> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)

    fun bigDecimalNil(): Returns7<T1, T2, T3, T4, T5, T6, BigDecimal?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns7<T1, T2, T3, T4, T5, T6, LocalDate> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATE, semaphore, conn, sql, args)

    fun dateNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalDate?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns7<T1, T2, T3, T4, T5, T6, LocalDateTime> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalDateTime?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)

    fun time(): Returns7<T1, T2, T3, T4, T5, T6, LocalTime> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIME, semaphore, conn, sql, args)

    fun timeNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalTime?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns7<T1, T2, T3, T4, T5, T6, Instant> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)

    fun timeStampNil(): Returns7<T1, T2, T3, T4, T5, T6, Instant?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)

    fun offsetDateTime(): Returns7<T1, T2, T3, T4, T5, T6, OffsetDateTime> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns7<T1, T2, T3, T4, T5, T6, OffsetDateTime?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(
        semaphore,
        conn,
        sql,
        args.toList(),
        listOf(column1, column2, column3, column4, column5, column6),
        ResultRow6<T1, T2, T3, T4, T5, T6>()
    )

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple6<T1, T2, T3, T4, T5, T6>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}


class Returns7<T1, T2, T3, T4, T5, T6, T7>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>,
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns8<T1, T2, T3, T4, T5, T6, T7, String> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.VARCHAR, semaphore, conn, sql, args)

    fun stringNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, String?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)

    fun long(): Returns8<T1, T2, T3, T4, T5, T6, T7, Long> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.LONG, semaphore, conn, sql, args)

    fun longNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Long?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)

    fun int(): Returns8<T1, T2, T3, T4, T5, T6, T7, Int> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.INTEGER, semaphore, conn, sql, args)

    fun intNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Int?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)

    fun byteArray(): Returns8<T1, T2, T3, T4, T5, T6, T7, ByteArray> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)

    fun byteArrayNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, ByteArray?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)

    fun blob(): Returns8<T1, T2, T3, T4, T5, T6, T7, Blob> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BLOB, semaphore, conn, sql, args)

    fun blobNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Blob?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)

    fun clob(): Returns8<T1, T2, T3, T4, T5, T6, T7, Clob> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.CLOB, semaphore, conn, sql, args)

    fun clobNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Clob?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)

    fun byte(): Returns8<T1, T2, T3, T4, T5, T6, T7, Byte> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BYTE, semaphore, conn, sql, args)

    fun byteNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Byte?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)

    fun boolean(): Returns8<T1, T2, T3, T4, T5, T6, T7, Boolean> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)

    fun booleanNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Boolean?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)

    fun double(): Returns8<T1, T2, T3, T4, T5, T6, T7, Double> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DOUBLE, semaphore, conn, sql, args)

    fun doubleNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Double?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)

    fun float(): Returns8<T1, T2, T3, T4, T5, T6, T7, Float> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.FLOAT, semaphore, conn, sql, args)

    fun floatNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Float?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)

    fun bigDecimal(): Returns8<T1, T2, T3, T4, T5, T6, T7, BigDecimal> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)

    fun bigDecimalNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, BigDecimal?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)

    fun date(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalDate> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DATE, semaphore, conn, sql, args)

    fun dateNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalDate?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalDateTime> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalDateTime?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)

    fun time(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalTime> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.TIME, semaphore, conn, sql, args)

    fun timeNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, LocalTime?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns8<T1, T2, T3, T4, T5, T6, T7, Instant> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)

    fun timeStampNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, Instant?> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)

    fun offsetDateTime(): Returns8<T1, T2, T3, T4, T5, T6, T7, OffsetDateTime> =
        Returns8(column1, column2, column3, column4, column5, column6, column7, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)

    fun offsetDateTimeNil(): Returns8<T1, T2, T3, T4, T5, T6, T7, OffsetDateTime?> = Returns8(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        ColumnFactory.OFFSET_DATETIME_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    private fun execute() = SQLStatementExecutor(
        semaphore,
        conn,
        sql,
        args.toList(),
        listOf(column1, column2, column3, column4, column5, column6, column7),
        ResultRow7<T1, T2, T3, T4, T5, T6, T7>()
    )

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple7<T1, T2, T3, T4, T5, T6, T7>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}


class Returns8<T1, T2, T3, T4, T5, T6, T7, T8>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>,
    internal val column8: Column<T8>,
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    fun string(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, String> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.VARCHAR, semaphore, conn, sql, args)

    fun stringNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, String?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.VARCHAR_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun long(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Long> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.LONG, semaphore, conn, sql, args)

    fun longNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Long?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)

    fun int(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Int> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.INTEGER, semaphore, conn, sql, args)

    fun intNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Int?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.INTEGER_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun byteArray(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, ByteArray> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.BYTE_ARRAY,
        semaphore,
        conn,
        sql,
        args
    )

    fun byteArrayNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, ByteArray?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.BYTE_ARRAY_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun blob(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Blob> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.BLOB, semaphore, conn, sql, args)

    fun blobNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Blob?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)

    fun clob(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Clob> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.CLOB, semaphore, conn, sql, args)

    fun clobNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Clob?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)

    fun byte(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Byte> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.BYTE, semaphore, conn, sql, args)

    fun byteNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Byte?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)

    fun boolean(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Boolean> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)

    fun booleanNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Boolean?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.BOOLEAN_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun double(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Double> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.DOUBLE, semaphore, conn, sql, args)

    fun doubleNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Double?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.DOUBLE_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun float(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Float> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.FLOAT, semaphore, conn, sql, args)

    fun floatNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Float?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.FLOAT_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun bigDecimal(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, BigDecimal> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.BIGDECIMAL,
        semaphore,
        conn,
        sql,
        args
    )

    fun bigDecimalNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, BigDecimal?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.BIGDECIMAL_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun date(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDate> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.DATE, semaphore, conn, sql, args)

    fun dateNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDate?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)

    fun dateTime(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDateTime> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.DATETIME, semaphore, conn, sql, args)

    fun dateTimeNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalDateTime?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.DATETIME_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun time(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalTime> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.TIME, semaphore, conn, sql, args)

    fun timeNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, LocalTime?> =
        Returns9(column1, column2, column3, column4, column5, column6, column7, column8, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)

    fun timeStamp(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Instant> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.TIMESTAMP,
        semaphore,
        conn,
        sql,
        args
    )

    fun timeStampNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, Instant?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.TIMESTAMP_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    fun offsetDateTime(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, OffsetDateTime> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.OFFSET_DATETIME,
        semaphore,
        conn,
        sql,
        args
    )

    fun offsetDateTimeNil(): Returns9<T1, T2, T3, T4, T5, T6, T7, T8, OffsetDateTime?> = Returns9(
        column1,
        column2,
        column3,
        column4,
        column5,
        column6,
        column7,
        column8,
        ColumnFactory.OFFSET_DATETIME_NIL,
        semaphore,
        conn,
        sql,
        args
    )

    private fun execute() = SQLStatementExecutor(
        semaphore,
        conn,
        sql,
        args.toList(),
        listOf(column1, column2, column3, column4, column5, column6, column7, column8),
        ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>()
    )

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}


class Returns9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    internal val column1: Column<T1>,
    internal val column2: Column<T2>,
    internal val column3: Column<T3>,
    internal val column4: Column<T4>,
    internal val column5: Column<T5>,
    internal val column6: Column<T6>,
    internal val column7: Column<T7>,
    internal val column8: Column<T8>,
    internal val column9: Column<T9>,
    private val semaphore: Semaphore,
    private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    private fun execute() = SQLStatementExecutor(
        semaphore,
        conn,
        sql,
        args.toList(),
        listOf(column1, column2, column3, column4, column5, column6, column7, column8, column9),
        ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>()
    )

    fun asList() = execute().asList()
    fun first() = execute().first()
    fun firstOrNull() = execute().firstOrNull()
    fun forEachRow(mapper: (Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>) -> Boolean) {
        return execute().forEachRow(mapper)
    }
}
