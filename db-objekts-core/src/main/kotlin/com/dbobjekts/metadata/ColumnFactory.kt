package com.dbobjekts.metadata

import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.api.exception.StatementExecutionException
import com.dbobjekts.metadata.column.*
import java.lang.reflect.Constructor
import java.math.BigDecimal
import java.sql.Blob
import java.sql.Clob
import java.sql.SQLXML
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime


object ColumnFactory {

    private val table = DefaultTable
    private const val DUMMY = "dummy"

    val VARCHAR = VarcharColumn(table, DUMMY)
    val VARCHAR_NIL = NullableVarcharColumn(table, DUMMY)

    val LONG = LongColumn(table, DUMMY)
    val LONG_NIL = NullableLongColumn(table, DUMMY)

    val INTEGER = IntegerColumn(table, DUMMY)
    val INTEGER_NIL = NullableIntegerColumn(table, DUMMY)

    val SHORT = ShortColumn(table, DUMMY)
    val SHORT_NIL = NullableShortColumn(table, DUMMY)

    val BYTE_ARRAY = ByteArrayColumn(table, DUMMY)
    val BYTE_ARRAY_NIL = NullableByteArrayColumn(table, DUMMY)

    val BLOB = BlobColumn(table, DUMMY)
    val BLOB_NIL = NullableBlobColumn(table, DUMMY)

    val CLOB = ClobColumn(table, DUMMY)
    val CLOB_NIL = NullableClobColumn(table, DUMMY)

    val BYTE = ByteColumn(table, DUMMY)
    val BYTE_NIL = NullableByteColumn(table, DUMMY)

    val BOOLEAN = BooleanColumn(table, DUMMY)
    val BOOLEAN_NIL = NullableBooleanColumn(table, DUMMY)

    val NUMBER_AS_BOOLEAN = NumberAsBooleanColumn(table, DUMMY)
    val NUMBER_AS_BOOLEAN_NIL = NullableNumberAsBooleanColumn(table, DUMMY)

    val DOUBLE = DoubleColumn(table, DUMMY)
    val DOUBLE_NIL = NullableDoubleColumn(table, DUMMY)

    val FLOAT = FloatColumn(table, DUMMY)
    val FLOAT_NIL = NullableFloatColumn(table, DUMMY)

    val BIGDECIMAL = BigDecimalColumn(table, DUMMY)
    val BIGDECIMAL_NIL = NullableBigDecimalColumn(table, DUMMY)

    val DATE = DateColumn(table, DUMMY)
    val DATE_NIL = NullableDateColumn(table, DUMMY)

    val DATETIME = DateTimeColumn(table, DUMMY)
    val DATETIME_NIL = NullableDateTimeColumn(table, DUMMY)

    val TIME = TimeColumn(table, DUMMY)
    val TIME_NIL = NullableTimeColumn(table, DUMMY)

    val TIMESTAMP = TimeStampColumn(table, DUMMY)
    val TIMESTAMP_NIL = NullableTimeStampColumn(table, DUMMY)

    val OFFSET_DATETIME = OffsetDateTimeColumn(table, DUMMY)
    val OFFSET_DATETIME_NIL = NullableOffsetDateTimeColumn(table, DUMMY)

    val SQLXML = XMLColumn(table, DUMMY)
    val SQLXML_NIL = NullableXMLColumn(table, DUMMY)

    val AUTOKEY_LONG = AutoKeyLongColumn(table, DUMMY)
    val AUTOKEY_INTEGER = AutoKeyIntegerColumn(table, DUMMY)
    val SEQUENCE_LONG = SequenceKeyLongColumn(table, DUMMY, "")
    val SEQUENCE_INTEGER = SequenceKeyIntegerColumn(table, DUMMY, "")
    val FOREIGN_KEY_LONG = ForeignKeyLongColumn(table, DUMMY, LONG)
    val FOREIGN_KEY_LONG_NIL = OptionalForeignKeyLongColumn(table, DUMMY, LONG)
    val FOREIGN_KEY_INT = ForeignKeyIntColumn(table, DUMMY, INTEGER)
    val FOREIGN_KEY_INT_NIL = OptionalForeignKeyIntColumn(table, DUMMY, INTEGER)
    val FOREIGN_KEY_VARCHAR = ForeignKeyVarcharColumn(table, DUMMY, VARCHAR)
    val FOREIGN_KEY_VARCHAR_NIL = OptionalForeignKeyVarcharColumn(table, DUMMY, VARCHAR)

    fun varcharColumn(nullable: Boolean = false): Column<out String?> = if (nullable) VARCHAR_NIL else VARCHAR
    fun longColumn(nullable: Boolean = false): Column<out Long?> = if (nullable) LONG_NIL else LONG
    fun integerColumn(nullable: Boolean = false): Column<out Int?> = if (nullable) INTEGER_NIL else INTEGER
    fun shortColumn(nullable: Boolean = false): Column<out Short?> = if (nullable) SHORT_NIL else SHORT
    fun byteArrayColumn(nullable: Boolean = false): Column<out ByteArray?> = if (nullable) BYTE_ARRAY_NIL else BYTE_ARRAY
    fun blobColumn(nullable: Boolean = false): Column<out Blob?> = if (nullable) BLOB_NIL else BLOB
    fun clobColumn(nullable: Boolean = false): Column<out Clob?> = if (nullable) CLOB_NIL else CLOB
    fun byteColumn(nullable: Boolean = false): Column<out Byte?> = if (nullable) BYTE_NIL else BYTE
    fun booleanColumn(nullable: Boolean = false): Column<out Boolean?> = if (nullable) BOOLEAN_NIL else BOOLEAN
    fun numberAsBooleanColumn(nullable: Boolean = false): Column<out Boolean?> = if (nullable) NUMBER_AS_BOOLEAN_NIL else NUMBER_AS_BOOLEAN
    fun doubleColumn(nullable: Boolean = false): Column<out Double?> = if (nullable) DOUBLE_NIL else DOUBLE
    fun floatColumn(nullable: Boolean = false): Column<out Float?> = if (nullable) FLOAT_NIL else FLOAT
    fun bigDecimalColumn(nullable: Boolean = false): Column<out BigDecimal?> = if (nullable) BIGDECIMAL_NIL else BIGDECIMAL
    fun dateColumn(nullable: Boolean = false): Column<out LocalDate?> = if (nullable) DATE_NIL else DATE
    fun dateTimeColumn(nullable: Boolean = false): Column<out LocalDateTime?> = if (nullable) DATETIME_NIL else DATETIME
    fun timeColumn(nullable: Boolean = false): Column<out LocalTime?> = if (nullable) TIME_NIL else TIME
    fun timeStampColumn(nullable: Boolean = false): Column<out Instant?> = if (nullable) TIMESTAMP_NIL else TIMESTAMP
    fun offsetDateTimeColumn(nullable: Boolean = false): Column<out OffsetDateTime?> = if (nullable) OFFSET_DATETIME_NIL else OFFSET_DATETIME
    fun xmlColumn(nullable: Boolean = false): Column<out SQLXML?> = if (nullable) SQLXML else SQLXML_NIL

    @Suppress("UNCHECKED_CAST")
    fun <C : NonNullableColumn<*>> forClass(clz: Class<C>): C =
         getConstructor(clz).newInstance(table, DUMMY) as C


    @Suppress("UNCHECKED_CAST")
    fun <C : NullableColumn<*>> forClassAsNullable(clz: Class<C>): C =
        getConstructor(clz).newInstance(table, DUMMY) as C




    private fun getConstructor(clz: Class<*>): Constructor<*> {
        return clz.constructors
            .filter { it.parameterCount == 2 && it.parameters[0].type == Table::class.java && it.parameters[1].type == String::class.java }
            .firstOrNull() ?: throw CodeGenerationException("Class does not define a constructor with (table: Table, name: String")
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getColumnForValue(value: T): Column<T> =
        when (value) {
            is Int -> INTEGER
            is Double -> DOUBLE
            is Long -> LONG
            is String -> VARCHAR
            is LocalDateTime -> DATETIME
            is LocalDate -> DATE
            is ZonedDateTime -> OFFSET_DATETIME
            is Boolean -> BOOLEAN
            else -> throw StatementExecutionException("Type $value not supported for parameter: only Int, Long, Double, Date or String")
        } as Column<T>


}
