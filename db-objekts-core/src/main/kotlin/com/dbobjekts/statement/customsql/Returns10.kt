 package com.dbobjekts.statement.customsql
 
  import com.dbobjekts.api.*
  import com.dbobjekts.api.Semaphore
  import com.dbobjekts.jdbc.ConnectionAdapter
  import com.dbobjekts.metadata.ColumnFactory
  import com.dbobjekts.metadata.column.Column
  import java.math.BigDecimal
  import java.sql.Blob
  import java.sql.Clob
  import java.time.Instant
  import java.time.LocalDate
  import java.time.LocalDateTime
  import java.time.LocalTime
  import java.time.OffsetDateTime 
     
class Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(
   internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>, internal val column5: Column<T5>, internal val column6: Column<T6>, internal val column7: Column<T7>, internal val column8: Column<T8>, internal val column9: Column<T9>, internal val column10: Column<T10>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, String> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, String?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Long> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Long?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Int> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Int?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, ByteArray> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, ByteArray?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Blob> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Blob?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Clob> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Clob?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Byte> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Byte?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Boolean> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Boolean?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Double> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Double?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Float> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Float?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, BigDecimal> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, BigDecimal?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalDate> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalDate?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalDateTime> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalDateTime?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalTime> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, LocalTime?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Instant> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, Instant?> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, OffsetDateTime> = Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, OffsetDateTime?> =
        Returns11(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10), ResultRow10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>())

    /**
     * Executes the select statement, fetches all rows and returns them as a list of tuples
     */
    fun asList() = execute().asList()

    /**
     * Executes the statement, fetches all rows and returns the first result.
     * @throws IllegalStateException if there are no results. To prevent this, use [firstOrNull]
     */
    fun first() = execute().first()

    /**
     * Executes the select statement, fetches all rows and returns the first result, or null if there is no match.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     */
    fun firstOrNull() = execute().firstOrNull()

    /**
     * Executes the query and lets you step through the results with a custom function that receives the current row data
     * and returns a Boolean to indicate whether to proceed or not. Example:
     * ```kotlin
     *     transaction.select("query").forEachRow({ row ->
     *        buffer.add(row)
     *        !buffer.memoryFull()
     *     })
     *  ```
     *
     * This can be useful for huge result sets that would run into memory problems when fetched at once into a list.
     */
    fun forEachRow(mapper: (Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

 