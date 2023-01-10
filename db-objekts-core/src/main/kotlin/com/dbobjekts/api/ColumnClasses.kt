package com.dbobjekts.api

import com.dbobjekts.metadata.column.*
import java.sql.Timestamp

object ColumnClasses {
    /**
     * Class reference to platform type which handles String values
     */
    val VARCHAR = VarcharColumn::class.java

    /**
     * Class reference to platform type which handles Byte values
     */
    val BYTE = ByteColumn::class.java

    /**
     * Class reference to platform type which handles Short values
     */
    val SHORT = ShortColumn::class.java

    /**
     * Class reference to platform type which handles Int values
     */
    val INTEGER = IntegerColumn::class.java

    /**
     * Class reference to platform type which handles Long values
     */
    val LONG = LongColumn::class.java

    /**
     * Class reference to platform type which handles Float values
     */
    val FLOAT = FloatColumn::class.java

    /**
     * Class reference to platform type which handles Double values
     */
    val DOUBLE = DoubleColumn::class.java

    /**
     * Class reference to platform type which handles [java.math.BigDecimal] values
     */
    val BIGDECIMAL = BigDecimalColumn::class.java

    /**
     * Class reference to platform type which handles [ByteArray] values
     */
    val BYTE_ARRAY = ByteArrayColumn::class.java

    /**
     * Class reference to platform type which handles [java.sql.Blob] values
     */
    val BLOB = BlobColumn::class.java

    /**
     * Class reference to platform type which handles [java.sql.Clob] values
     */
    val CLOB = ClobColumn::class.java

    /**
     * Class reference to platform type which handles Boolean values
     */
    val BOOLEAN = BooleanColumn::class.java

    /**
     * Class reference to platform type which handles numeric values to be returned as Boolean
     */
    val NUMBER_AS_BOOLEAN = NumberAsBooleanColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalDate] values
     */
    val DATE = DateColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalDateTime] values
     */
    val DATETIME = DateTimeColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalTime] values
     */
    val TIME = TimeColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.Instant] values
     */
    val TIMESTAMP = Timestamp::class.java

    /**
     * Class reference to platform type which handles [java.time.OffsetDateTime] values
     */
    val OFFSET_DATETIME = OffsetDateTimeColumn::class.java
}

object NilColumnClasses {
    /**
     * Class reference to platform type which handles String? values
     */
    val VARCHAR = NullableVarcharColumn::class.java

    /**
     * Class reference to platform type which handles Byte? values
     */
    val BYTE = ByteColumn::class.java

    /**
     * Class reference to platform type which handles Short? values
     */
    val SHORT = ShortColumn::class.java

    /**
     * Class reference to platform type which handles Int? values
     */
    val INTEGER = IntegerColumn::class.java

    /**
     * Class reference to platform type which handles Long? values
     */
    val LONG = LongColumn::class.java

    /**
     * Class reference to platform type which handles Float? values
     */
    val FLOAT = FloatColumn::class.java

    /**
     * Class reference to platform type which handles Double? values
     */
    val DOUBLE = DoubleColumn::class.java

    /**
     * Class reference to platform type which handles [java.math.BigDecimal]? values
     */
    val BIGDECIMAL = BigDecimalColumn::class.java

    /**
     * Class reference to platform type which handles [ByteArray]? values
     */
    val BYTE_ARRAY = ByteArrayColumn::class.java

    /**
     * Class reference to platform type which handles [java.sql.Blob]? values
     */
    val BLOB = BlobColumn::class.java

    /**
     * Class reference to platform type which handles [java.sql.Clob]? values
     */
    val CLOB = ClobColumn::class.java

    /**
     * Class reference to platform type which handles Boolean? values
     */
    val BOOLEAN = BooleanColumn::class.java

    /**
     * Class reference to platform type which handles numeric values to be returned as Boolean?
     */
    val NUMBER_AS_BOOLEAN = NumberAsBooleanColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalDate]? values
     */
    val DATE = DateColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalDateTime]? values
     */
    val DATETIME = DateTimeColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.LocalTime]? values
     */
    val TIME = TimeColumn::class.java

    /**
     * Class reference to platform type which handles [java.time.Instant]? values
     */
    val TIMESTAMP = Timestamp::class.java

    /**
     * Class reference to platform type which handles [java.time.OffsetDateTime]? values
     */
    val OFFSET_DATETIME = OffsetDateTimeColumn::class.java
}
