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
     
class Returns9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(
    internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>, internal val column5: Column<T5>, internal val column6: Column<T6>, internal val column7: Column<T7>, internal val column8: Column<T8>, internal val column9: Column<T9>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {

    /**
     * Adds a custom non-nullable Column type as the next column in the result row.
     * @param clz a subclass of NonNullableColumn<*>             
     */
    fun <R, T : NonNullableColumn<R>> custom(clz: Class<T>): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.forClass(clz), semaphore, conn, sql, args)
    
    /**
     * Adds a custom nullable Column type as the next column in the result row.
     * @param clz a subclass of NullableColumn<*>             
     */
    fun <R, T : NullableColumn<R>> customNil(clz: Class<T>): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.forClassAsNullable(clz), semaphore, conn, sql, args)

    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, String> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, String?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Long> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Long?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Int> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Int?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, ByteArray> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, ByteArray?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Blob> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Blob?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Clob> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Clob?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Byte> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Byte?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Boolean> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Boolean?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Double> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Double?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Float> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Float?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, BigDecimal> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, BigDecimal?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalDate> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalDate?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalDateTime> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalDateTime?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalTime> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, LocalTime?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Instant> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Instant?> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, OffsetDateTime> = Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns10<T1, T2, T3, T4, T5, T6, T7, T8, T9, OffsetDateTime?> =
        Returns10(column1, column2, column3, column4, column5, column6, column7, column8, column9, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4, column5, column6, column7, column8, column9), ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>())

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
    fun forEachRow(mapper: (Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

