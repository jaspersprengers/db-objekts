package com.dbobjekts.statement.customsql
 
import com.dbobjekts.api.*
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.statement.customsql.SQLStatementExecutor
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
     
class Returns3<T1, T2, T3>(
   internal val column1: Column<T1>, internal val column2: Column<T2>, internal val column3: Column<T3>,
    private val semaphore: Semaphore, private val conn: ConnectionAdapter,
    private val sql: String,
    private val args: List<Any>
) {

   /**
      * Adds a non-nullable `EnumAsStringColumn` type as the next column in the result row.
      *
      * Example:
      *```kotlin
      * withResultTypes().enumAsString(AddressType::class.java)
      * ```
      * @param clz a valid Enum class
      */
    fun <E : Enum<E>> enumAsString(clz: Class<E>): Returns4<T1, T2, T3, E> = Returns4(column1, column2, column3, ColumnFactory.forEnumAsString(clz), semaphore, conn, sql, args)
 
     /**
      * Adds a `NullableEnumAsStringColumn` type as the next column in the result row.
      *
      * Example:
      *```kotlin
      * withResultTypes().enumAsStringNil(AddressType::class.java)
      * ```
      * @param clz a valid Enum class
      */
    fun <E : Enum<E>> enumAsStringNil(clz: Class<E>): Returns4<T1, T2, T3, E?> = Returns4(column1, column2, column3, ColumnFactory.forNullableEnumAsString(clz), semaphore, conn, sql, args)
 
     /**
      * Adds a non-nullable `EnumAsIntColumn` as the next column in the result row.
      *
      * Example:
      *```kotlin
      * withResultTypes().enumAsInt(AddressType::class.java)
      * ```
      * @param clz a valid Enum class
      */
    fun <E : Enum<E>> enumAsInt(clz: Class<E>): Returns4<T1, T2, T3, E> = Returns4(column1, column2, column3, ColumnFactory.forEnumAsInt(clz), semaphore, conn, sql, args)
 
    /**
      * Adds a `NullableEnumAsIntColumn` as the next column in the result row.
      *
      * Example:
      *```kotlin
      * withResultTypes().enumAsIntNil(AddressType::class.java)
      * ```
      * @param clz a valid Enum class
      */
    fun <E : Enum<E>> enumAsIntNil(clz: Class<E>): Returns4<T1, T2, T3, E?> = Returns4(column1, column2, column3, ColumnFactory.forNullableEnumAsInt(clz), semaphore, conn, sql, args)


    /**
     * Adds a custom non-nullable Column type as the next column in the result row.
     * @param clz a subclass of NonNullableColumn<*>             
     */               
    fun <R> column(col: NonNullableColumn<R>): Returns4<T1, T2, T3, R> = Returns4(column1, column2, column3, col, semaphore, conn, sql, args)
    
    /**
     * Adds a custom nullable Column type as the next column in the result row.
     * @param clz a subclass of NullableColumn<*>             
     */
    fun <R> columnNil(col: NullableColumn<R>): Returns4<T1, T2, T3, R> = Returns4(column1, column2, column3, col, semaphore, conn, sql, args)

    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns4<T1, T2, T3, String> = Returns4(column1, column2, column3, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns4<T1, T2, T3, String?> = Returns4(column1, column2, column3, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns4<T1, T2, T3, Long> = Returns4(column1, column2, column3, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns4<T1, T2, T3, Long?> = Returns4(column1, column2, column3, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns4<T1, T2, T3, Int> = Returns4(column1, column2, column3, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns4<T1, T2, T3, Int?> = Returns4(column1, column2, column3, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns4<T1, T2, T3, ByteArray> = Returns4(column1, column2, column3, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns4<T1, T2, T3, ByteArray?> = Returns4(column1, column2, column3, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns4<T1, T2, T3, Blob> = Returns4(column1, column2, column3, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns4<T1, T2, T3, Blob?> = Returns4(column1, column2, column3, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns4<T1, T2, T3, Clob> = Returns4(column1, column2, column3, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns4<T1, T2, T3, Clob?> = Returns4(column1, column2, column3, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns4<T1, T2, T3, Byte> = Returns4(column1, column2, column3, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns4<T1, T2, T3, Byte?> = Returns4(column1, column2, column3, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns4<T1, T2, T3, Boolean> = Returns4(column1, column2, column3, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns4<T1, T2, T3, Boolean?> = Returns4(column1, column2, column3, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns4<T1, T2, T3, Double> = Returns4(column1, column2, column3, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns4<T1, T2, T3, Double?> = Returns4(column1, column2, column3, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns4<T1, T2, T3, Float> = Returns4(column1, column2, column3, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns4<T1, T2, T3, Float?> = Returns4(column1, column2, column3, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns4<T1, T2, T3, BigDecimal> = Returns4(column1, column2, column3, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns4<T1, T2, T3, BigDecimal?> = Returns4(column1, column2, column3, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns4<T1, T2, T3, LocalDate> = Returns4(column1, column2, column3, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns4<T1, T2, T3, LocalDate?> = Returns4(column1, column2, column3, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns4<T1, T2, T3, LocalDateTime> = Returns4(column1, column2, column3, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns4<T1, T2, T3, LocalDateTime?> = Returns4(column1, column2, column3, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns4<T1, T2, T3, LocalTime> = Returns4(column1, column2, column3, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns4<T1, T2, T3, LocalTime?> = Returns4(column1, column2, column3, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns4<T1, T2, T3, Instant> = Returns4(column1, column2, column3, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns4<T1, T2, T3, Instant?> = Returns4(column1, column2, column3, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns4<T1, T2, T3, OffsetDateTime> = Returns4(column1, column2, column3, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns4<T1, T2, T3, OffsetDateTime?> =
        Returns4(column1, column2, column3, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1, column2, column3), ResultRow3<T1, T2, T3>())

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
    fun forEachRow(predicate: (Int, Tuple3<T1, T2, T3>) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(predicate)
    }
    
    /**
     * Returns a [ResultSetIterator], which implements [Iterator].
     *
     * This delegates to the underlying [ResultSet] for each call to `next()`, which makes it more memory-efficient for very large data sets by not loading all rows into a single list.
     *
     * WARNING: You cannot return a [ResultSetIterator] from a transaction block, as the underlying [Connection] has been already closed.
     *
     * ```kotlin
     * // this will fail at runtime
     * val iterator = tm { it.select(Employee).iterator() }
     * ```
     */
    fun iterator(): ResultSetIterator<Tuple3<T1, T2, T3>, ResultRow3<T1, T2, T3>> = execute().iterator()
}

 