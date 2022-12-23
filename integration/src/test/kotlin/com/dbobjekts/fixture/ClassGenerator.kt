package com.dbobjekts.fixture

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.file.Paths


class ClassGenerator {


    @Test
    fun printMethods() {
        val lines = IntRange(2, 9).map { i ->
            val ts = join("T$", i)
            val columns = join("ret.column$", i)
            """
        fun <$ts> Returns(ret: Returns$i<$ts>): SQLStatementExecutor<Tuple$i<$ts>, ResultRow$i<$ts>> {
            val columns = listOf($columns)
            return SQLStatementExecutor(semaphore, conn, sql, args.toList(), columns, ResultRow$i<$ts>())
        }
        
            """.trimIndent()
        }

        lines.forEach { print(it) }

    }

    @Test
    fun createResultRows() {

        fun print(i: Int): String {
            val ts = join("T$", i)
            val extractValues = join("extractValue(cols[#], resultSet) as T$", i)
            return """
   class ResultRow$i<$ts> : ResultRow<Tuple$i<$ts>>() {
       override fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): Tuple$i<$ts> =
           Tuple$i($extractValues)
   }
        """.trimIndent()
        }
        IntRange(2, 22).forEach { i ->
            println(print(i))
        }

    }

    @Test
    fun createSelectStatement() {

        fun print(i: Int): String {
            val ts = join("T$", i)
            val columns = join("c$: Column<T$>", i)
            val cs = join("c$", i)
            return """
     /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <$ts> select($columns): SelectStatementExecutor<Tuple$i<$ts>, ResultRow$i<$ts>> =
        SelectStatementExecutor(semaphore, connection, listOf($cs), ResultRow$i<$ts>())

        """.trimIndent()
        }
        IntRange(2, 22).forEach { i ->
            println(print(i))
        }

    }


    @Test
    fun printReturnsClasses() {

        fun outputCustomSqlBuilder(i: Int): String {
            val next = i + 1
            val ts = join("T$", i)
            val columns = join("column$", i)
            val params = join("internal val column$: Column<T$>", i)


            return """
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
                
           class Returns$i<$ts>(
              $params,
               private val semaphore: Semaphore, private val conn: ConnectionAdapter,
               private val sql: String,
               private val args: List<Any>
           ) {
               /**
                * Adds a String type as the next column in the result row
                */
               fun string(): Returns$next<$ts, String> = Returns$next($columns, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
               /**
                * Adds a String? type as the next column in the result row
                */
               fun stringNil(): Returns$next<$ts, String?> = Returns$next($columns, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Long type as the next column in the result row
                */
               fun long(): Returns$next<$ts, Long> = Returns$next($columns, ColumnFactory.LONG, semaphore, conn, sql, args)
               /**
                * Adds a Long? type as the next column in the result row
                */
               fun longNil(): Returns$next<$ts, Long?> = Returns$next($columns, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Int type as the next column in the result row
                */
               fun int(): Returns$next<$ts, Int> = Returns$next($columns, ColumnFactory.INTEGER, semaphore, conn, sql, args)
               /**
                * Adds a Int? type as the next column in the result row
                */
               fun intNil(): Returns$next<$ts, Int?> = Returns$next($columns, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
               /**
                * Adds a ByteArray type as the next column in the result row
                */
               fun byteArray(): Returns$next<$ts, ByteArray> = Returns$next($columns, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
               /**
                * Adds a ByteArray? type as the next column in the result row
                */
               fun byteArrayNil(): Returns$next<$ts, ByteArray?> = Returns$next($columns, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [java.sql.Blob] type as the next column in the result row
                */
               fun blob(): Returns$next<$ts, Blob> = Returns$next($columns, ColumnFactory.BLOB, semaphore, conn, sql, args)
               /**
                * Adds a [java.sql.Blob]? type as the next column in the result row
                */
               fun blobNil(): Returns$next<$ts, Blob?> = Returns$next($columns, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [java.sql.Clob] type as the next column in the result row
                */
               fun clob(): Returns$next<$ts, Clob> = Returns$next($columns, ColumnFactory.CLOB, semaphore, conn, sql, args)
               /**
                * Adds a [java.sql.Clob]? type as the next column in the result row
                */
               fun clobNil(): Returns$next<$ts, Clob?> = Returns$next($columns, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Byte type as the next column in the result row
                */
               fun byte(): Returns$next<$ts, Byte> = Returns$next($columns, ColumnFactory.BYTE, semaphore, conn, sql, args)
               /**
                * Adds a Byte? type as the next column in the result row
                */
               fun byteNil(): Returns$next<$ts, Byte?> = Returns$next($columns, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Boolean type as the next column in the result row
                */
               fun boolean(): Returns$next<$ts, Boolean> = Returns$next($columns, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
               /**
                * Adds a Boolean? type as the next column in the result row
                */
               fun booleanNil(): Returns$next<$ts, Boolean?> = Returns$next($columns, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Double type as the next column in the result row
                */
               fun double(): Returns$next<$ts, Double> = Returns$next($columns, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
               /**
                * Adds a Double? type as the next column in the result row
                */
               fun doubleNil(): Returns$next<$ts, Double?> = Returns$next($columns, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
               /**
                * Adds a Float type as the next column in the result row
                */
               fun float(): Returns$next<$ts, Float> = Returns$next($columns, ColumnFactory.FLOAT, semaphore, conn, sql, args)
               /**
                * Adds a Float? type as the next column in the result row
                */
               fun floatNil(): Returns$next<$ts, Float?> = Returns$next($columns, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
               /**
                * Adds a BigDecimal type as the next column in the result row
                */
               fun bigDecimal(): Returns$next<$ts, BigDecimal> = Returns$next($columns, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
               /**
                * Adds a BigDecimal? type as the next column in the result row
                */
               fun bigDecimalNil(): Returns$next<$ts, BigDecimal?> = Returns$next($columns, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [LocalDate] type as the next column in the result row
                */
               fun date(): Returns$next<$ts, LocalDate> = Returns$next($columns, ColumnFactory.DATE, semaphore, conn, sql, args)
               /**
                * Adds a [LocalDate]? type as the next column in the result row
                */
               fun dateNil(): Returns$next<$ts, LocalDate?> = Returns$next($columns, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [LocalDateTime] type as the next column in the result row
                */
               fun dateTime(): Returns$next<$ts, LocalDateTime> = Returns$next($columns, ColumnFactory.DATETIME, semaphore, conn, sql, args)
               /**
                * Adds a [LocalDateTime]? type as the next column in the result row
                */
               fun dateTimeNil(): Returns$next<$ts, LocalDateTime?> = Returns$next($columns, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [LocalTime] type as the next column in the result row
                */
               fun time(): Returns$next<$ts, LocalTime> = Returns$next($columns, ColumnFactory.TIME, semaphore, conn, sql, args)
               /**
                * Adds a [LocalTime]? type as the next column in the result row
                */
               fun timeNil(): Returns$next<$ts, LocalTime?> = Returns$next($columns, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [Instant] type as the next column in the result row
                */
               fun timeStamp(): Returns$next<$ts, Instant> = Returns$next($columns, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
               /**
                * Adds a Instant? type as the next column in the result row
                */
               fun timeStampNil(): Returns$next<$ts, Instant?> = Returns$next($columns, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
               /**
                * Adds a [OffsetDateTime] type as the next column in the result row
                */
               fun offsetDateTime(): Returns$next<$ts, OffsetDateTime> = Returns$next($columns, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
               /**
                * Adds a [OffsetDateTime]? type as the next column in the result row
                */
               fun offsetDateTimeNil(): Returns$next<$ts, OffsetDateTime?> =
                   Returns$next($columns, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)

               private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf($columns), ResultRow$i<$ts>())

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
               fun forEachRow(mapper: (Tuple$i<$ts>) -> Boolean) {
                   semaphore.clear()
                   return execute().forEachRow(mapper)
               }
           }

            
        """.trimIndent()

        }

        IntRange(2,22).forEach { i ->
            val file =
                Paths.get("/Users/jasper/dev/db-objekts/core/src/main/kotlin/com/dbobjekts/statement/customsql", "Returns${i}.kt").toFile()
            //FileUtils.writeStringToFile(file, outputCustomSqlBuilder(i), Charset.defaultCharset())

        }
    }

    fun join(template: String, repeat: Int): String {
        return IntRange(1, repeat).map { i ->
            template.replace("$", i.toString()).replace("#", (i - 1).toString())
        }.joinToString(", ")
    }

}
