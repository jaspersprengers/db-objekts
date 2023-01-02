 package com.dbobjekts.statement.customsql
 
  import com.dbobjekts.api.*
  import com.dbobjekts.api.Semaphore
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
     
class Returns6<T1, T2, T3, T4, T5, T6>(
   internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>, internal val column5: Column<T5>, internal val column6: Column<T6>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {

    /**
     * Adds a custom non-nullable Column type as the next column in the result row.
     * @param clz a subclass of NonNullableColumn<*>             
     */
    fun <R, T : NonNullableColumn<R>> custom(clz: Class<T>): Returns7<T1, T2, T3, T4, T5, T6, R> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.forClass(clz), semaphore, conn, sql, args)
    
    /**
     * Adds a custom nullable Column type as the next column in the result row.
     * @param clz a subclass of NullableColumn<*>             
     */
    fun <R, T : NullableColumn<R>> customNil(clz: Class<T>): Returns7<T1, T2, T3, T4, T5, T6, R> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.forClassAsNullable(clz), semaphore, conn, sql, args)

    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns7<T1, T2, T3, T4, T5, T6, String> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns7<T1, T2, T3, T4, T5, T6, String?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns7<T1, T2, T3, T4, T5, T6, Long> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns7<T1, T2, T3, T4, T5, T6, Long?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns7<T1, T2, T3, T4, T5, T6, Int> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns7<T1, T2, T3, T4, T5, T6, Int?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns7<T1, T2, T3, T4, T5, T6, ByteArray> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns7<T1, T2, T3, T4, T5, T6, ByteArray?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns7<T1, T2, T3, T4, T5, T6, Blob> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns7<T1, T2, T3, T4, T5, T6, Blob?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns7<T1, T2, T3, T4, T5, T6, Clob> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns7<T1, T2, T3, T4, T5, T6, Clob?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns7<T1, T2, T3, T4, T5, T6, Byte> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns7<T1, T2, T3, T4, T5, T6, Byte?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns7<T1, T2, T3, T4, T5, T6, Boolean> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns7<T1, T2, T3, T4, T5, T6, Boolean?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns7<T1, T2, T3, T4, T5, T6, Double> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns7<T1, T2, T3, T4, T5, T6, Double?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns7<T1, T2, T3, T4, T5, T6, Float> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns7<T1, T2, T3, T4, T5, T6, Float?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns7<T1, T2, T3, T4, T5, T6, BigDecimal> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns7<T1, T2, T3, T4, T5, T6, BigDecimal?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns7<T1, T2, T3, T4, T5, T6, LocalDate> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalDate?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns7<T1, T2, T3, T4, T5, T6, LocalDateTime> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalDateTime?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns7<T1, T2, T3, T4, T5, T6, LocalTime> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns7<T1, T2, T3, T4, T5, T6, LocalTime?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns7<T1, T2, T3, T4, T5, T6, Instant> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns7<T1, T2, T3, T4, T5, T6, Instant?> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns7<T1, T2, T3, T4, T5, T6, OffsetDateTime> = Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns7<T1, T2, T3, T4, T5, T6, OffsetDateTime?> =
        Returns7(column1, column2, column3, column4, column5, column6, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4, column5, column6), ResultRow6<T1, T2, T3, T4, T5, T6>())

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
    fun forEachRow(mapper: (Tuple6<T1, T2, T3, T4, T5, T6>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

 