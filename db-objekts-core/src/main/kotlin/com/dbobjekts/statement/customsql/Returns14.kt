 package com.dbobjekts.statement.customsql
 
  import com.dbobjekts.api.*
  import com.dbobjekts.statement.Semaphore
  import com.dbobjekts.jdbc.ConnectionAdapter
  import com.dbobjekts.metadata.ColumnFactory
  import com.dbobjekts.metadata.column.Column
  import com.dbobjekts.metadata.column.NonNullableColumn
  import com.dbobjekts.metadata.column.NullableColumn
  import java.math.BigDecimal
  import java.sql.Blob
  import java.sql.Clob
  import java.time.Instant
  import java.time.LocalDate
  import java.time.LocalDateTime
  import java.time.LocalTime
  import java.time.OffsetDateTime 
     
class Returns14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(
    internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>, internal val column5: Column<T5>, internal val column6: Column<T6>, internal val column7: Column<T7>, internal val column8: Column<T8>, internal val column9: Column<T9>, internal val column10: Column<T10>, internal val column11: Column<T11>, internal val column12: Column<T12>, internal val column13: Column<T13>, internal val column14: Column<T14>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {

    /**
     * Adds a custom non-nullable Column type as the next column in the result row.
     * @param clz a subclass of NonNullableColumn<*>             
     */
    fun <R, T : NonNullableColumn<R>> custom(clz: Class<T>): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.forClass(clz), semaphore, conn, sql, args)
    
    /**
     * Adds a custom nullable Column type as the next column in the result row.
     * @param clz a subclass of NullableColumn<*>             
     */
    fun <R, T : NullableColumn<R>> customNil(clz: Class<T>): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.forClassAsNullable(clz), semaphore, conn, sql, args)

    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, String> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, String?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Long> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Long?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Int> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Int?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, ByteArray> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, ByteArray?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Blob> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Blob?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Clob> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Clob?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Byte> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Byte?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Boolean> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Boolean?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Double> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Double?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Float> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Float?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, BigDecimal> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, BigDecimal?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalDate> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalDate?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalDateTime> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalDateTime?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalTime> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, LocalTime?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Instant> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, Instant?> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, OffsetDateTime> = Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, OffsetDateTime?> =
        Returns15(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14), ResultRow14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>())

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
    fun forEachRow(mapper: (Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

