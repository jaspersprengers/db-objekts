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
     
class Returns4<T1, T2, T3, T4>(
   internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>, internal val column4: Column<T4>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {
    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns5<T1, T2, T3, T4, String> = Returns5(column1, column2, column3, column4, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns5<T1, T2, T3, T4, String?> = Returns5(column1, column2, column3, column4, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns5<T1, T2, T3, T4, Long> = Returns5(column1, column2, column3, column4, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns5<T1, T2, T3, T4, Long?> = Returns5(column1, column2, column3, column4, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns5<T1, T2, T3, T4, Int> = Returns5(column1, column2, column3, column4, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns5<T1, T2, T3, T4, Int?> = Returns5(column1, column2, column3, column4, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns5<T1, T2, T3, T4, ByteArray> = Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns5<T1, T2, T3, T4, ByteArray?> = Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns5<T1, T2, T3, T4, Blob> = Returns5(column1, column2, column3, column4, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns5<T1, T2, T3, T4, Blob?> = Returns5(column1, column2, column3, column4, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns5<T1, T2, T3, T4, Clob> = Returns5(column1, column2, column3, column4, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns5<T1, T2, T3, T4, Clob?> = Returns5(column1, column2, column3, column4, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns5<T1, T2, T3, T4, Byte> = Returns5(column1, column2, column3, column4, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns5<T1, T2, T3, T4, Byte?> = Returns5(column1, column2, column3, column4, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns5<T1, T2, T3, T4, Boolean> = Returns5(column1, column2, column3, column4, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns5<T1, T2, T3, T4, Boolean?> = Returns5(column1, column2, column3, column4, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns5<T1, T2, T3, T4, Double> = Returns5(column1, column2, column3, column4, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns5<T1, T2, T3, T4, Double?> = Returns5(column1, column2, column3, column4, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns5<T1, T2, T3, T4, Float> = Returns5(column1, column2, column3, column4, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns5<T1, T2, T3, T4, Float?> = Returns5(column1, column2, column3, column4, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns5<T1, T2, T3, T4, BigDecimal> = Returns5(column1, column2, column3, column4, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns5<T1, T2, T3, T4, BigDecimal?> = Returns5(column1, column2, column3, column4, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns5<T1, T2, T3, T4, LocalDate> = Returns5(column1, column2, column3, column4, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns5<T1, T2, T3, T4, LocalDate?> = Returns5(column1, column2, column3, column4, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns5<T1, T2, T3, T4, LocalDateTime> = Returns5(column1, column2, column3, column4, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns5<T1, T2, T3, T4, LocalDateTime?> = Returns5(column1, column2, column3, column4, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns5<T1, T2, T3, T4, LocalTime> = Returns5(column1, column2, column3, column4, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns5<T1, T2, T3, T4, LocalTime?> = Returns5(column1, column2, column3, column4, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns5<T1, T2, T3, T4, Instant> = Returns5(column1, column2, column3, column4, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns5<T1, T2, T3, T4, Instant?> = Returns5(column1, column2, column3, column4, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns5<T1, T2, T3, T4, OffsetDateTime> = Returns5(column1, column2, column3, column4, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns5<T1, T2, T3, T4, OffsetDateTime?> =
        Returns5(column1, column2, column3, column4, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3, column4), ResultRow4<T1, T2, T3, T4>())

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
    fun forEachRow(mapper: (Tuple4<T1, T2, T3, T4>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
    }
}

 