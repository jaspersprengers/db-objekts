package com.dbobjekts.fixture

import org.junit.jupiter.api.Test


class ClassGenerator {


    @Test
    fun printMethods(){
        val lines = IntRange(2, 9).map { i ->
            val ts = join("T$", i)
            val columns = join("ret.column$", i)
            """
        fun <$ts> returning(ret: Returning$i<$ts>): SQLStatementExecutor<Tuple$i<$ts>, ResultRow$i<$ts>> {
            val columns = listOf($columns)
            return SQLStatementExecutor(conn, sql, args.toList(), columns, ResultRow$i<$ts>())
        }
        
            """.trimIndent()
        }

        lines.forEach {print(it)}

    }

    @Test
    fun printReturningClasses() {

        IntRange(1, 9).forEach { i ->
            println(output(i))
        }

    }

    private fun output(i: Int): String {
        val next = i + 1
        val ts = join("T$", i)
        val columns = join("column$", i)
        val params = join("internal val column$: Column<T$>", i)


        return """
            class Returning$i<$ts>($params) {
        fun string(): Returning$next<$ts, String> = Returning$next($columns, Columns.VARCHAR)
        fun stringNil(): Returning$next<$ts, String?> = Returning$next($columns, Columns.VARCHAR_NIL)
        fun long(): Returning$next<$ts, Long> = Returning$next($columns, Columns.LONG)
        fun longNil(): Returning$next<$ts, Long?> = Returning$next($columns, Columns.LONG_NIL)
        fun int(): Returning$next<$ts, Int> = Returning$next($columns, Columns.INTEGER)
        fun intNil(): Returning$next<$ts, Int?> = Returning$next($columns, Columns.INTEGER_NIL)
        fun byteArray(): Returning$next<$ts, ByteArray> = Returning$next($columns, Columns.BYTE_ARRAY)
        fun byteArrayNil(): Returning$next<$ts, ByteArray?> = Returning$next($columns, Columns.BYTE_ARRAY_NIL)
        fun blob(): Returning$next<$ts, Blob> = Returning$next($columns, Columns.BLOB)
        fun blobNil(): Returning$next<$ts, Blob?> = Returning$next($columns, Columns.BLOB_NIL)
        fun clob(): Returning$next<$ts, Clob> = Returning$next($columns, Columns.CLOB)
        fun clobNil(): Returning$next<$ts, Clob?> = Returning$next($columns, Columns.CLOB_NIL)
        fun byte(): Returning$next<$ts, Byte> = Returning$next($columns, Columns.BYTE)
        fun byteNil(): Returning$next<$ts, Byte?> = Returning$next($columns, Columns.BYTE_NIL)
        fun boolean(): Returning$next<$ts, Boolean> = Returning$next($columns, Columns.BOOLEAN)
        fun booleanNil(): Returning$next<$ts, Boolean?> = Returning$next($columns, Columns.BOOLEAN_NIL)
        fun double(): Returning$next<$ts, Double> = Returning$next($columns, Columns.DOUBLE)
        fun doubleNil(): Returning$next<$ts, Double?> = Returning$next($columns, Columns.DOUBLE_NIL)
        fun float(): Returning$next<$ts, Float> = Returning$next($columns, Columns.FLOAT)
        fun floatNil(): Returning$next<$ts, Float?> = Returning$next($columns, Columns.FLOAT_NIL)
        fun bigDecimal(): Returning$next<$ts, BigDecimal> = Returning$next($columns, Columns.BIGDECIMAL)
        fun bigDecimalNil(): Returning$next<$ts, BigDecimal?> = Returning$next($columns, Columns.BIGDECIMAL_NIL)
        fun date(): Returning$next<$ts, LocalDate> = Returning$next($columns, Columns.DATE)
        fun dateNil(): Returning$next<$ts, LocalDate?> = Returning$next($columns, Columns.DATE_NIL)
        fun dateTime(): Returning$next<$ts, LocalDateTime> = Returning$next($columns, Columns.DATETIME)
        fun dateTimeNil(): Returning$next<$ts, LocalDateTime?> = Returning$next($columns, Columns.DATETIME_NIL)
        fun time(): Returning$next<$ts, LocalTime> = Returning$next($columns, Columns.TIME)
        fun timeNil(): Returning$next<$ts, LocalTime?> = Returning$next($columns, Columns.TIME_NIL)
        fun timeStamp(): Returning$next<$ts, Instant> = Returning$next($columns, Columns.TIMESTAMP)
        fun timeStampNil(): Returning$next<$ts, Instant?> = Returning$next($columns, Columns.TIMESTAMP_NIL)
        fun offsetDateTime(): Returning$next<$ts, OffsetDateTime> = Returning$next($columns, Columns.OFFSET_DATETIME)
        fun offsetDateTimeNil(): Returning$next<$ts, OffsetDateTime?> = Returning$next($columns, Columns.OFFSET_DATETIME_NIL)
       

            }
        """.trimIndent()

    }

    fun join(template: String, repeat: Int): String {
        return IntRange(1, repeat).map { i ->
            template.replace("$", i.toString())
        }.joinToString(", ")
    }

}
