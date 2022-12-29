package com.dbobjekts.metadata

import com.dbobjekts.metadata.column.*
import java.time.LocalDate
import java.time.LocalDateTime
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

    fun varcharColumn(nullable: Boolean = false) = if (nullable) VARCHAR_NIL else VARCHAR
    fun longColumn(nullable: Boolean = false) = if (nullable) LONG_NIL else LONG
    fun integerColumn(nullable: Boolean = false) = if (nullable) INTEGER_NIL else INTEGER
    fun shortColumn(nullable: Boolean = false) = if (nullable) SHORT_NIL else SHORT
    fun byteArrayColumn(nullable: Boolean = false) = if (nullable) BYTE_ARRAY_NIL else BYTE_ARRAY
    fun blobColumn(nullable: Boolean = false) = if (nullable) BLOB_NIL else BLOB
    fun clobColumn(nullable: Boolean = false) = if (nullable) CLOB_NIL else CLOB
    fun byteColumn(nullable: Boolean = false) = if (nullable) BYTE_NIL else BYTE
    fun booleanColumn(nullable: Boolean = false) = if (nullable) BOOLEAN_NIL else BOOLEAN
    fun numberAsBooleanColumn(nullable: Boolean = false) = if (nullable) NUMBER_AS_BOOLEAN_NIL else NUMBER_AS_BOOLEAN
    fun doubleColumn(nullable: Boolean = false) = if (nullable) DOUBLE_NIL else DOUBLE
    fun floatColumn(nullable: Boolean = false) = if (nullable) FLOAT_NIL else FLOAT
    fun bigDecimalColumn(nullable: Boolean = false) = if (nullable) BIGDECIMAL_NIL else BIGDECIMAL
    fun dateColumn(nullable: Boolean = false) = if (nullable) DATE_NIL else DATE
    fun dateTimeColumn(nullable: Boolean = false) = if (nullable) DATETIME_NIL else DATETIME
    fun timeColumn(nullable: Boolean = false) = if (nullable) TIME_NIL else TIME
    fun timeStampColumn(nullable: Boolean = false) = if (nullable) TIMESTAMP_NIL else TIMESTAMP
    fun offsetDateTimeColumn(nullable: Boolean = false) = if (nullable) OFFSET_DATETIME_NIL else OFFSET_DATETIME

    @Suppress("UNCHECKED_CAST")
    fun <C : NonNullableColumn<*>> forClass(clz: Class<C>): C {
        val constructor = clz.constructors
            .filter { it.parameterCount == 2 && it.parameters[0].type == Table::class.java && it.parameters[1].type == String::class.java }
            .firstOrNull() ?: throw IllegalStateException("Class does not define a constructor with (table: Table, name: String")
        return constructor.newInstance(table, DUMMY) as C
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
            else -> throw IllegalArgumentException("Type $value not supported for parameter: only Int, Long, Double, Date or String")
        } as Column<T>


}
