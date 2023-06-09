package com.dbobjekts.statement.customsql

import com.dbobjekts.api.*
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.*
import com.dbobjekts.statement.Semaphore
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
    fun <R, T : NonNullableColumn<R>> custom(clz: Class<T>): Returns1<R> = Returns1(ColumnFactory.forClass(clz), semaphore, conn, sql, args)
    fun <R, T : NullableColumn<R>> customNil(clz: Class<T>): Returns1<R> = Returns1(ColumnFactory.forClassAsNullable(clz), semaphore, conn, sql, args)
    fun <R> column(col: NonNullableColumn<R>): Returns1<R> = Returns1(col, semaphore, conn, sql, args)
    fun <R> columnNil(col: NullableColumn<R>): Returns1<R> = Returns1(col, semaphore, conn, sql, args)

    fun <E : Enum<E>> enumAsString(clz: Class<E>): Returns1<E> = Returns1(ColumnFactory.forEnumAsString(clz), semaphore, conn, sql, args)
    fun <E : Enum<E>> enumAsStringNil(clz: Class<E>): Returns1<E?> = Returns1(ColumnFactory.forNullableEnumAsString(clz), semaphore, conn, sql, args)
    fun <E : Enum<E>> enumAsInt(clz: Class<E>): Returns1<E> = Returns1(ColumnFactory.forEnumAsInt(clz), semaphore, conn, sql, args)
    fun <E : Enum<E>> enumAsIntNil(clz: Class<E>): Returns1<E?> = Returns1(ColumnFactory.forNullableEnumAsInt(clz), semaphore, conn, sql, args)

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

    /**
     * Adds a non-nullable `EnumAsStringColumn` type as the next column in the result row.
     *
     * Example:
     *```kotlin
     * withResultTypes().enumAsString(AddressType::class.java)
     * ```
     * @param clz a valid Enum class
     */
    fun <E : Enum<E>> enumAsString(clz: Class<E>): Returns2<T1, E> = Returns2(column1, ColumnFactory.forEnumAsString(clz), semaphore, conn, sql, args)

    /**
     * Adds a `NullableEnumAsStringColumn` type as the next column in the result row.
     *
     * Example:
     *```kotlin
     * withResultTypes().enumAsStringNil(AddressType::class.java)
     * ```
     * @param clz a valid Enum class
     */
    fun <E : Enum<E>> enumAsStringNil(clz: Class<E>): Returns2<T1, E?> = Returns2(column1, ColumnFactory.forNullableEnumAsString(clz), semaphore, conn, sql, args)

    /**
     * Adds a non-nullable `EnumAsIntColumn` as the next column in the result row.
     *
     * Example:
     *```kotlin
     * withResultTypes().enumAsInt(AddressType::class.java)
     * ```
     * @param clz a valid Enum class
     */
    fun <E : Enum<E>> enumAsInt(clz: Class<E>): Returns2<T1, E> = Returns2(column1, ColumnFactory.forEnumAsInt(clz), semaphore, conn, sql, args)

    /**
     * Adds a `NullableEnumAsIntColumn` as the next column in the result row.
     *
     * Example:
     *```kotlin
     * withResultTypes().enumAsIntNil(AddressType::class.java)
     * ```
     * @param clz a valid Enum class
     */
    fun <E : Enum<E>> enumAsIntNil(clz: Class<E>): Returns2<T1, E?> = Returns2(column1, ColumnFactory.forNullableEnumAsInt(clz), semaphore, conn, sql, args)


    /**
     * Adds a custom non-nullable Column type as the next column in the result row.
     * @param clz a subclass of NonNullableColumn<*>
     */
    fun <R, T : NonNullableColumn<R>> custom(clz: Class<T>): Returns2<T1, R> = Returns2(column1, ColumnFactory.forClass(clz), semaphore, conn, sql, args)

    /**
     * Adds a custom nullable Column type as the next column in the result row.
     * @param clz a subclass of NullableColumn<*>
     */
    fun <R, T : NullableColumn<R>> customNil(clz: Class<T>): Returns2<T1, R> = Returns2(column1, ColumnFactory.forClassAsNullable(clz), semaphore, conn, sql, args)

    /**
     * Adds a String type as the next column in the result row
     */
    fun string(): Returns2<T1, String> = Returns2(column1, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
    /**
     * Adds a String? type as the next column in the result row
     */
    fun stringNil(): Returns2<T1, String?> = Returns2(column1, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Long type as the next column in the result row
     */
    fun long(): Returns2<T1, Long> = Returns2(column1, ColumnFactory.LONG, semaphore, conn, sql, args)
    /**
     * Adds a Long? type as the next column in the result row
     */
    fun longNil(): Returns2<T1, Long?> = Returns2(column1, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Int type as the next column in the result row
     */
    fun int(): Returns2<T1, Int> = Returns2(column1, ColumnFactory.INTEGER, semaphore, conn, sql, args)
    /**
     * Adds a Int? type as the next column in the result row
     */
    fun intNil(): Returns2<T1, Int?> = Returns2(column1, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray type as the next column in the result row
     */
    fun byteArray(): Returns2<T1, ByteArray> = Returns2(column1, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
    /**
     * Adds a ByteArray? type as the next column in the result row
     */
    fun byteArrayNil(): Returns2<T1, ByteArray?> = Returns2(column1, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob] type as the next column in the result row
     */
    fun blob(): Returns2<T1, Blob> = Returns2(column1, ColumnFactory.BLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Blob]? type as the next column in the result row
     */
    fun blobNil(): Returns2<T1, Blob?> = Returns2(column1, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob] type as the next column in the result row
     */
    fun clob(): Returns2<T1, Clob> = Returns2(column1, ColumnFactory.CLOB, semaphore, conn, sql, args)
    /**
     * Adds a [java.sql.Clob]? type as the next column in the result row
     */
    fun clobNil(): Returns2<T1, Clob?> = Returns2(column1, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Byte type as the next column in the result row
     */
    fun byte(): Returns2<T1, Byte> = Returns2(column1, ColumnFactory.BYTE, semaphore, conn, sql, args)
    /**
     * Adds a Byte? type as the next column in the result row
     */
    fun byteNil(): Returns2<T1, Byte?> = Returns2(column1, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Boolean type as the next column in the result row
     */
    fun boolean(): Returns2<T1, Boolean> = Returns2(column1, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
    /**
     * Adds a Boolean? type as the next column in the result row
     */
    fun booleanNil(): Returns2<T1, Boolean?> = Returns2(column1, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Double type as the next column in the result row
     */
    fun double(): Returns2<T1, Double> = Returns2(column1, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
    /**
     * Adds a Double? type as the next column in the result row
     */
    fun doubleNil(): Returns2<T1, Double?> = Returns2(column1, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a Float type as the next column in the result row
     */
    fun float(): Returns2<T1, Float> = Returns2(column1, ColumnFactory.FLOAT, semaphore, conn, sql, args)
    /**
     * Adds a Float? type as the next column in the result row
     */
    fun floatNil(): Returns2<T1, Float?> = Returns2(column1, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal type as the next column in the result row
     */
    fun bigDecimal(): Returns2<T1, BigDecimal> = Returns2(column1, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
    /**
     * Adds a BigDecimal? type as the next column in the result row
     */
    fun bigDecimalNil(): Returns2<T1, BigDecimal?> = Returns2(column1, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate] type as the next column in the result row
     */
    fun date(): Returns2<T1, LocalDate> = Returns2(column1, ColumnFactory.DATE, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDate]? type as the next column in the result row
     */
    fun dateNil(): Returns2<T1, LocalDate?> = Returns2(column1, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime] type as the next column in the result row
     */
    fun dateTime(): Returns2<T1, LocalDateTime> = Returns2(column1, ColumnFactory.DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalDateTime]? type as the next column in the result row
     */
    fun dateTimeNil(): Returns2<T1, LocalDateTime?> = Returns2(column1, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime] type as the next column in the result row
     */
    fun time(): Returns2<T1, LocalTime> = Returns2(column1, ColumnFactory.TIME, semaphore, conn, sql, args)
    /**
     * Adds a [LocalTime]? type as the next column in the result row
     */
    fun timeNil(): Returns2<T1, LocalTime?> = Returns2(column1, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [Instant] type as the next column in the result row
     */
    fun timeStamp(): Returns2<T1, Instant> = Returns2(column1, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
    /**
     * Adds a Instant? type as the next column in the result row
     */
    fun timeStampNil(): Returns2<T1, Instant?> = Returns2(column1, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime] type as the next column in the result row
     */
    fun offsetDateTime(): Returns2<T1, OffsetDateTime> = Returns2(column1, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
    /**
     * Adds a [OffsetDateTime]? type as the next column in the result row
     */
    fun offsetDateTimeNil(): Returns2<T1, OffsetDateTime?> =
        Returns2(column1, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

    private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf(column1), ResultRow1<T1>())

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
    fun forEachRow(mapper: (Int, T1) -> Boolean) {
        semaphore.clear()
        return execute().forEachRow(mapper)
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
    fun iterator(): ResultSetIterator<T1, ResultRow1<T1>> = execute().iterator()
}
