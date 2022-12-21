package com.dbobjekts.fixture

import org.junit.jupiter.api.Test


class ClassGenerator {


    @Test
    fun printMethods(){
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

        lines.forEach {print(it)}

    }

    @Test
    fun printReturnsClasses() {

        IntRange(2, 9).forEach { i ->
            println(output(i))
        }

    }

    private fun output(i: Int): String {
        val next = i + 1
        val ts = join("T$", i)
        val columns = join("column$", i)
        val params = join("internal val column$: Column<T$>", i)


        return """
            class Returns$i<$ts>($params,
            private val semaphore: Semaphore,
            private val conn: ConnectionAdapter,
            private val sql: String,
            private val args: List<Any>) {
        fun string(): Returns$next<$ts, String> = Returns$next($columns, ColumnFactory.VARCHAR, semaphore, conn, sql, args)
        fun stringNil(): Returns$next<$ts, String?> = Returns$next($columns, ColumnFactory.VARCHAR_NIL, semaphore, conn, sql, args)
        fun long(): Returns$next<$ts, Long> = Returns$next($columns, ColumnFactory.LONG, semaphore, conn, sql, args)
        fun longNil(): Returns$next<$ts, Long?> = Returns$next($columns, ColumnFactory.LONG_NIL, semaphore, conn, sql, args)
        fun int(): Returns$next<$ts, Int> = Returns$next($columns, ColumnFactory.INTEGER, semaphore, conn, sql, args)
        fun intNil(): Returns$next<$ts, Int?> = Returns$next($columns, ColumnFactory.INTEGER_NIL, semaphore, conn, sql, args)
        fun byteArray(): Returns$next<$ts, ByteArray> = Returns$next($columns, ColumnFactory.BYTE_ARRAY, semaphore, conn, sql, args)
        fun byteArrayNil(): Returns$next<$ts, ByteArray?> = Returns$next($columns, ColumnFactory.BYTE_ARRAY_NIL, semaphore, conn, sql, args)
        fun blob(): Returns$next<$ts, Blob> = Returns$next($columns, ColumnFactory.BLOB, semaphore, conn, sql, args)
        fun blobNil(): Returns$next<$ts, Blob?> = Returns$next($columns, ColumnFactory.BLOB_NIL, semaphore, conn, sql, args)
        fun clob(): Returns$next<$ts, Clob> = Returns$next($columns, ColumnFactory.CLOB, semaphore, conn, sql, args)
        fun clobNil(): Returns$next<$ts, Clob?> = Returns$next($columns, ColumnFactory.CLOB_NIL, semaphore, conn, sql, args)
        fun byte(): Returns$next<$ts, Byte> = Returns$next($columns, ColumnFactory.BYTE, semaphore, conn, sql, args)
        fun byteNil(): Returns$next<$ts, Byte?> = Returns$next($columns, ColumnFactory.BYTE_NIL, semaphore, conn, sql, args)
        fun boolean(): Returns$next<$ts, Boolean> = Returns$next($columns, ColumnFactory.BOOLEAN, semaphore, conn, sql, args)
        fun booleanNil(): Returns$next<$ts, Boolean?> = Returns$next($columns, ColumnFactory.BOOLEAN_NIL, semaphore, conn, sql, args)
        fun double(): Returns$next<$ts, Double> = Returns$next($columns, ColumnFactory.DOUBLE, semaphore, conn, sql, args)
        fun doubleNil(): Returns$next<$ts, Double?> = Returns$next($columns, ColumnFactory.DOUBLE_NIL, semaphore, conn, sql, args)
        fun float(): Returns$next<$ts, Float> = Returns$next($columns, ColumnFactory.FLOAT, semaphore, conn, sql, args)
        fun floatNil(): Returns$next<$ts, Float?> = Returns$next($columns, ColumnFactory.FLOAT_NIL, semaphore, conn, sql, args)
        fun bigDecimal(): Returns$next<$ts, BigDecimal> = Returns$next($columns, ColumnFactory.BIGDECIMAL, semaphore, conn, sql, args)
        fun bigDecimalNil(): Returns$next<$ts, BigDecimal?> = Returns$next($columns, ColumnFactory.BIGDECIMAL_NIL, semaphore, conn, sql, args)
        fun date(): Returns$next<$ts, LocalDate> = Returns$next($columns, ColumnFactory.DATE, semaphore, conn, sql, args)
        fun dateNil(): Returns$next<$ts, LocalDate?> = Returns$next($columns, ColumnFactory.DATE_NIL, semaphore, conn, sql, args)
        fun dateTime(): Returns$next<$ts, LocalDateTime> = Returns$next($columns, ColumnFactory.DATETIME, semaphore, conn, sql, args)
        fun dateTimeNil(): Returns$next<$ts, LocalDateTime?> = Returns$next($columns, ColumnFactory.DATETIME_NIL, semaphore, conn, sql, args)
        fun time(): Returns$next<$ts, LocalTime> = Returns$next($columns, ColumnFactory.TIME, semaphore, conn, sql, args)
        fun timeNil(): Returns$next<$ts, LocalTime?> = Returns$next($columns, ColumnFactory.TIME_NIL, semaphore, conn, sql, args)
        fun timeStamp(): Returns$next<$ts, Instant> = Returns$next($columns, ColumnFactory.TIMESTAMP, semaphore, conn, sql, args)
        fun timeStampNil(): Returns$next<$ts, Instant?> = Returns$next($columns, ColumnFactory.TIMESTAMP_NIL, semaphore, conn, sql, args)
        fun offsetDateTime(): Returns$next<$ts, OffsetDateTime> = Returns$next($columns, ColumnFactory.OFFSET_DATETIME, semaphore, conn, sql, args)
        fun offsetDateTimeNil(): Returns$next<$ts, OffsetDateTime?> = Returns$next($columns, ColumnFactory.OFFSET_DATETIME_NIL, semaphore, conn, sql, args)
       
        private fun execute() = SQLStatementExecutor(semaphore, conn, sql, args.toList(), listOf($columns), ResultRow$i<$ts>())
        fun asList() = execute().asList().also { statementLog.logResult(it);semaphore.clear() }
        fun first() = execute().first().also { statementLog.logResult(it);semaphore.clear() }
        fun firstOrNull() = execute().firstOrNull().also { statementLog.logResult(it);semaphore.clear() }
        fun forEachRow(mapper: (Tuple$i<$ts>) -> Boolean){ 
             semaphore.clear()
            return execute().forEachRow(mapper)}
            }
            
            
        """.trimIndent()

    }

    fun join(template: String, repeat: Int): String {
        return IntRange(1, repeat).map { i ->
            template.replace("$", i.toString())
        }.joinToString(", ")
    }

}
