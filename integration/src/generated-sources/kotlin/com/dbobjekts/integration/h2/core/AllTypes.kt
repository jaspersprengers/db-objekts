package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object AllTypes:Table("ALL_TYPES"), HasUpdateBuilder<AllTypesUpdateBuilder, AllTypesInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val characterCol = com.dbobjekts.metadata.column.VarcharColumn(this, "CHARACTER_COL")
    val characterColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CHARACTER_COL_NIL")
    val charactervaryingCol = com.dbobjekts.metadata.column.VarcharColumn(this, "CHARACTERVARYING_COL")
    val charactervaryingColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CHARACTERVARYING_COL_NIL")
    val characterlargeobjectCol = com.dbobjekts.metadata.column.VarcharColumn(this, "CHARACTERLARGEOBJECT_COL")
    val characterlargeobjectColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CHARACTERLARGEOBJECT_COL_NIL")
    val varcharIgnorecaseCol = com.dbobjekts.metadata.column.VarcharColumn(this, "VARCHAR_IGNORECASE_COL")
    val varcharIgnorecaseColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "VARCHAR_IGNORECASE_COL_NIL")
    val enumCol = com.dbobjekts.metadata.column.VarcharColumn(this, "ENUM_COL")
    val enumColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "ENUM_COL_NIL")
    val binaryCol = com.dbobjekts.metadata.column.ByteArrayColumn(this, "BINARY_COL")
    val binaryColNil = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "BINARY_COL_NIL")
    val binaryvaryingCol = com.dbobjekts.metadata.column.ByteArrayColumn(this, "BINARYVARYING_COL")
    val binaryvaryingColNil = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "BINARYVARYING_COL_NIL")
    val binarylargeobjectCol = com.dbobjekts.metadata.column.BlobColumn(this, "BINARYLARGEOBJECT_COL")
    val binarylargeobjectColNil = com.dbobjekts.metadata.column.NullableBlobColumn(this, "BINARYLARGEOBJECT_COL_NIL")
    val jsonCol = com.dbobjekts.metadata.column.ByteArrayColumn(this, "JSON_COL")
    val jsonColNil = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "JSON_COL_NIL")
    val booleanCol = com.dbobjekts.metadata.column.BooleanColumn(this, "BOOLEAN_COL")
    val booleanColNil = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "BOOLEAN_COL_NIL")
    val tinyintCol = com.dbobjekts.metadata.column.ByteColumn(this, "TINYINT_COL")
    val tinyintColNil = com.dbobjekts.metadata.column.NullableByteColumn(this, "TINYINT_COL_NIL")
    val smallintCol = com.dbobjekts.metadata.column.IntegerColumn(this, "SMALLINT_COL")
    val smallintColNil = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "SMALLINT_COL_NIL")
    val integerCol = com.dbobjekts.metadata.column.IntegerColumn(this, "INTEGER_COL")
    val integerColNil = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "INTEGER_COL_NIL")
    val bigintCol = com.dbobjekts.metadata.column.LongColumn(this, "BIGINT_COL")
    val bigintColNil = com.dbobjekts.metadata.column.NullableLongColumn(this, "BIGINT_COL_NIL")
    val numericCol = com.dbobjekts.metadata.column.BigDecimalColumn(this, "NUMERIC_COL")
    val numericColNil = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "NUMERIC_COL_NIL")
    val decfloatCol = com.dbobjekts.metadata.column.BigDecimalColumn(this, "DECFLOAT_COL")
    val decfloatColNil = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "DECFLOAT_COL_NIL")
    val realCol = com.dbobjekts.metadata.column.FloatColumn(this, "REAL_COL")
    val realColNil = com.dbobjekts.metadata.column.NullableFloatColumn(this, "REAL_COL_NIL")
    val doubleprecisionCol = com.dbobjekts.metadata.column.DoubleColumn(this, "DOUBLEPRECISION_COL")
    val doubleprecisionColNil = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "DOUBLEPRECISION_COL_NIL")
    val dateCol = com.dbobjekts.metadata.column.DateColumn(this, "DATE_COL")
    val dateColNil = com.dbobjekts.metadata.column.NullableDateColumn(this, "DATE_COL_NIL")
    val timeCol = com.dbobjekts.metadata.column.TimeColumn(this, "TIME_COL")
    val timeColNil = com.dbobjekts.metadata.column.NullableTimeColumn(this, "TIME_COL_NIL")
    val timewithtimezoneCol = com.dbobjekts.metadata.column.OffsetDateTimeColumn(this, "TIMEWITHTIMEZONE_COL")
    val timewithtimezoneColNil = com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn(this, "TIMEWITHTIMEZONE_COL_NIL")
    val timestampCol = com.dbobjekts.metadata.column.TimeStampColumn(this, "TIMESTAMP_COL")
    val timestampColNil = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "TIMESTAMP_COL_NIL")
    val timestampwithtimezoneCol = com.dbobjekts.metadata.column.OffsetDateTimeColumn(this, "TIMESTAMPWITHTIMEZONE_COL")
    val timestampwithtimezoneColNil = com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn(this, "TIMESTAMPWITHTIMEZONE_COL_NIL")
    val uuidCol = com.dbobjekts.vendors.h2.UUIDColumn(this, "UUID_COL")
    val uuidColNil = com.dbobjekts.vendors.h2.NullableUUIDColumn(this, "UUID_COL_NIL")
    val intervalCol = com.dbobjekts.vendors.h2.IntervalColumn(this, "INTERVAL_COL")
    val intervalColNil = com.dbobjekts.vendors.h2.NullableIntervalColumn(this, "INTERVAL_COL_NIL")
    val geometryColNil = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "GEOMETRY_COL_NIL")
    val intArrayCol = com.dbobjekts.vendors.h2.ObjectArrayColumn(this, "INT_ARRAY_COL")
    val intArrayColNil = com.dbobjekts.vendors.h2.NullableObjectArrayColumn(this, "INT_ARRAY_COL_NIL")
    val addressInt = com.dbobjekts.integration.h2.custom.AddressTypeAsIntegerColumn(this, "ADDRESS_INT")
    val addressIntNil = com.dbobjekts.integration.h2.custom.AddressTypeAsIntegerColumn(this, "ADDRESS_INT_NIL")
    val addressString = com.dbobjekts.integration.h2.custom.AddressTypeAsStringColumn(this, "ADDRESS_STRING")
    val addressStringNil = com.dbobjekts.integration.h2.custom.AddressTypeAsStringColumn(this, "ADDRESS_STRING_NIL")
    override val columns: List<AnyColumn> = listOf(id,characterCol,characterColNil,charactervaryingCol,charactervaryingColNil,characterlargeobjectCol,characterlargeobjectColNil,varcharIgnorecaseCol,varcharIgnorecaseColNil,enumCol,enumColNil,binaryCol,binaryColNil,binaryvaryingCol,binaryvaryingColNil,binarylargeobjectCol,binarylargeobjectColNil,jsonCol,jsonColNil,booleanCol,booleanColNil,tinyintCol,tinyintColNil,smallintCol,smallintColNil,integerCol,integerColNil,bigintCol,bigintColNil,numericCol,numericColNil,decfloatCol,decfloatColNil,realCol,realColNil,doubleprecisionCol,doubleprecisionColNil,dateCol,dateColNil,timeCol,timeColNil,timewithtimezoneCol,timewithtimezoneColNil,timestampCol,timestampColNil,timestampwithtimezoneCol,timestampwithtimezoneColNil,uuidCol,uuidColNil,intervalCol,intervalColNil,geometryColNil,intArrayCol,intArrayColNil,addressInt,addressIntNil,addressString,addressStringNil)
    override fun metadata(): WriteQueryAccessors<AllTypesUpdateBuilder, AllTypesInsertBuilder> = WriteQueryAccessors(AllTypesUpdateBuilder(), AllTypesInsertBuilder())
}

class AllTypesUpdateBuilder() : UpdateBuilderBase(AllTypes) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data

    fun characterCol(value: String): AllTypesUpdateBuilder = ct.put(AllTypes.characterCol, value)
    fun characterColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.characterColNil, value)
    fun charactervaryingCol(value: String): AllTypesUpdateBuilder = ct.put(AllTypes.charactervaryingCol, value)
    fun charactervaryingColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.charactervaryingColNil, value)
    fun characterlargeobjectCol(value: String): AllTypesUpdateBuilder = ct.put(AllTypes.characterlargeobjectCol, value)
    fun characterlargeobjectColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.characterlargeobjectColNil, value)
    fun varcharIgnorecaseCol(value: String): AllTypesUpdateBuilder = ct.put(AllTypes.varcharIgnorecaseCol, value)
    fun varcharIgnorecaseColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.varcharIgnorecaseColNil, value)
    fun enumCol(value: String): AllTypesUpdateBuilder = ct.put(AllTypes.enumCol, value)
    fun enumColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.enumColNil, value)
    fun binaryCol(value: ByteArray): AllTypesUpdateBuilder = ct.put(AllTypes.binaryCol, value)
    fun binaryColNil(value: ByteArray?): AllTypesUpdateBuilder = ct.put(AllTypes.binaryColNil, value)
    fun binaryvaryingCol(value: ByteArray): AllTypesUpdateBuilder = ct.put(AllTypes.binaryvaryingCol, value)
    fun binaryvaryingColNil(value: ByteArray?): AllTypesUpdateBuilder = ct.put(AllTypes.binaryvaryingColNil, value)
    fun binarylargeobjectCol(value: java.sql.Blob): AllTypesUpdateBuilder = ct.put(AllTypes.binarylargeobjectCol, value)
    fun binarylargeobjectColNil(value: java.sql.Blob?): AllTypesUpdateBuilder = ct.put(AllTypes.binarylargeobjectColNil, value)
    fun jsonCol(value: ByteArray): AllTypesUpdateBuilder = ct.put(AllTypes.jsonCol, value)
    fun jsonColNil(value: ByteArray?): AllTypesUpdateBuilder = ct.put(AllTypes.jsonColNil, value)
    fun booleanCol(value: Boolean): AllTypesUpdateBuilder = ct.put(AllTypes.booleanCol, value)
    fun booleanColNil(value: Boolean?): AllTypesUpdateBuilder = ct.put(AllTypes.booleanColNil, value)
    fun tinyintCol(value: Byte): AllTypesUpdateBuilder = ct.put(AllTypes.tinyintCol, value)
    fun tinyintColNil(value: Byte?): AllTypesUpdateBuilder = ct.put(AllTypes.tinyintColNil, value)
    fun smallintCol(value: Int): AllTypesUpdateBuilder = ct.put(AllTypes.smallintCol, value)
    fun smallintColNil(value: Int?): AllTypesUpdateBuilder = ct.put(AllTypes.smallintColNil, value)
    fun integerCol(value: Int): AllTypesUpdateBuilder = ct.put(AllTypes.integerCol, value)
    fun integerColNil(value: Int?): AllTypesUpdateBuilder = ct.put(AllTypes.integerColNil, value)
    fun bigintCol(value: Long): AllTypesUpdateBuilder = ct.put(AllTypes.bigintCol, value)
    fun bigintColNil(value: Long?): AllTypesUpdateBuilder = ct.put(AllTypes.bigintColNil, value)
    fun numericCol(value: java.math.BigDecimal): AllTypesUpdateBuilder = ct.put(AllTypes.numericCol, value)
    fun numericColNil(value: java.math.BigDecimal?): AllTypesUpdateBuilder = ct.put(AllTypes.numericColNil, value)
    fun decfloatCol(value: java.math.BigDecimal): AllTypesUpdateBuilder = ct.put(AllTypes.decfloatCol, value)
    fun decfloatColNil(value: java.math.BigDecimal?): AllTypesUpdateBuilder = ct.put(AllTypes.decfloatColNil, value)
    fun realCol(value: Float): AllTypesUpdateBuilder = ct.put(AllTypes.realCol, value)
    fun realColNil(value: Float?): AllTypesUpdateBuilder = ct.put(AllTypes.realColNil, value)
    fun doubleprecisionCol(value: Double): AllTypesUpdateBuilder = ct.put(AllTypes.doubleprecisionCol, value)
    fun doubleprecisionColNil(value: Double?): AllTypesUpdateBuilder = ct.put(AllTypes.doubleprecisionColNil, value)
    fun dateCol(value: java.time.LocalDate): AllTypesUpdateBuilder = ct.put(AllTypes.dateCol, value)
    fun dateColNil(value: java.time.LocalDate?): AllTypesUpdateBuilder = ct.put(AllTypes.dateColNil, value)
    fun timeCol(value: java.time.LocalTime): AllTypesUpdateBuilder = ct.put(AllTypes.timeCol, value)
    fun timeColNil(value: java.time.LocalTime?): AllTypesUpdateBuilder = ct.put(AllTypes.timeColNil, value)
    fun timewithtimezoneCol(value: java.time.OffsetDateTime): AllTypesUpdateBuilder = ct.put(AllTypes.timewithtimezoneCol, value)
    fun timewithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesUpdateBuilder = ct.put(AllTypes.timewithtimezoneColNil, value)
    fun timestampCol(value: java.time.Instant): AllTypesUpdateBuilder = ct.put(AllTypes.timestampCol, value)
    fun timestampColNil(value: java.time.Instant?): AllTypesUpdateBuilder = ct.put(AllTypes.timestampColNil, value)
    fun timestampwithtimezoneCol(value: java.time.OffsetDateTime): AllTypesUpdateBuilder = ct.put(AllTypes.timestampwithtimezoneCol, value)
    fun timestampwithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesUpdateBuilder = ct.put(AllTypes.timestampwithtimezoneColNil, value)
    fun uuidCol(value: java.util.UUID): AllTypesUpdateBuilder = ct.put(AllTypes.uuidCol, value)
    fun uuidColNil(value: java.util.UUID?): AllTypesUpdateBuilder = ct.put(AllTypes.uuidColNil, value)
    fun intervalCol(value: org.h2.api.Interval): AllTypesUpdateBuilder = ct.put(AllTypes.intervalCol, value)
    fun intervalColNil(value: org.h2.api.Interval?): AllTypesUpdateBuilder = ct.put(AllTypes.intervalColNil, value)
    fun geometryColNil(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.geometryColNil, value)
    fun intArrayCol(value: Array<Any>): AllTypesUpdateBuilder = ct.put(AllTypes.intArrayCol, value)
    fun intArrayColNil(value: Array<Any>?): AllTypesUpdateBuilder = ct.put(AllTypes.intArrayColNil, value)
    fun addressInt(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesUpdateBuilder = ct.put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesUpdateBuilder = ct.put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesUpdateBuilder = ct.put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesUpdateBuilder = ct.put(AllTypes.addressStringNil, value)
}

class AllTypesInsertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    

    fun characterCol(value: String): AllTypesInsertBuilder = ct.put(AllTypes.characterCol, value)
    fun characterColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.characterColNil, value)
    fun charactervaryingCol(value: String): AllTypesInsertBuilder = ct.put(AllTypes.charactervaryingCol, value)
    fun charactervaryingColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.charactervaryingColNil, value)
    fun characterlargeobjectCol(value: String): AllTypesInsertBuilder = ct.put(AllTypes.characterlargeobjectCol, value)
    fun characterlargeobjectColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.characterlargeobjectColNil, value)
    fun varcharIgnorecaseCol(value: String): AllTypesInsertBuilder = ct.put(AllTypes.varcharIgnorecaseCol, value)
    fun varcharIgnorecaseColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.varcharIgnorecaseColNil, value)
    fun enumCol(value: String): AllTypesInsertBuilder = ct.put(AllTypes.enumCol, value)
    fun enumColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.enumColNil, value)
    fun binaryCol(value: ByteArray): AllTypesInsertBuilder = ct.put(AllTypes.binaryCol, value)
    fun binaryColNil(value: ByteArray?): AllTypesInsertBuilder = ct.put(AllTypes.binaryColNil, value)
    fun binaryvaryingCol(value: ByteArray): AllTypesInsertBuilder = ct.put(AllTypes.binaryvaryingCol, value)
    fun binaryvaryingColNil(value: ByteArray?): AllTypesInsertBuilder = ct.put(AllTypes.binaryvaryingColNil, value)
    fun binarylargeobjectCol(value: java.sql.Blob): AllTypesInsertBuilder = ct.put(AllTypes.binarylargeobjectCol, value)
    fun binarylargeobjectColNil(value: java.sql.Blob?): AllTypesInsertBuilder = ct.put(AllTypes.binarylargeobjectColNil, value)
    fun jsonCol(value: ByteArray): AllTypesInsertBuilder = ct.put(AllTypes.jsonCol, value)
    fun jsonColNil(value: ByteArray?): AllTypesInsertBuilder = ct.put(AllTypes.jsonColNil, value)
    fun booleanCol(value: Boolean): AllTypesInsertBuilder = ct.put(AllTypes.booleanCol, value)
    fun booleanColNil(value: Boolean?): AllTypesInsertBuilder = ct.put(AllTypes.booleanColNil, value)
    fun tinyintCol(value: Byte): AllTypesInsertBuilder = ct.put(AllTypes.tinyintCol, value)
    fun tinyintColNil(value: Byte?): AllTypesInsertBuilder = ct.put(AllTypes.tinyintColNil, value)
    fun smallintCol(value: Int): AllTypesInsertBuilder = ct.put(AllTypes.smallintCol, value)
    fun smallintColNil(value: Int?): AllTypesInsertBuilder = ct.put(AllTypes.smallintColNil, value)
    fun integerCol(value: Int): AllTypesInsertBuilder = ct.put(AllTypes.integerCol, value)
    fun integerColNil(value: Int?): AllTypesInsertBuilder = ct.put(AllTypes.integerColNil, value)
    fun bigintCol(value: Long): AllTypesInsertBuilder = ct.put(AllTypes.bigintCol, value)
    fun bigintColNil(value: Long?): AllTypesInsertBuilder = ct.put(AllTypes.bigintColNil, value)
    fun numericCol(value: java.math.BigDecimal): AllTypesInsertBuilder = ct.put(AllTypes.numericCol, value)
    fun numericColNil(value: java.math.BigDecimal?): AllTypesInsertBuilder = ct.put(AllTypes.numericColNil, value)
    fun decfloatCol(value: java.math.BigDecimal): AllTypesInsertBuilder = ct.put(AllTypes.decfloatCol, value)
    fun decfloatColNil(value: java.math.BigDecimal?): AllTypesInsertBuilder = ct.put(AllTypes.decfloatColNil, value)
    fun realCol(value: Float): AllTypesInsertBuilder = ct.put(AllTypes.realCol, value)
    fun realColNil(value: Float?): AllTypesInsertBuilder = ct.put(AllTypes.realColNil, value)
    fun doubleprecisionCol(value: Double): AllTypesInsertBuilder = ct.put(AllTypes.doubleprecisionCol, value)
    fun doubleprecisionColNil(value: Double?): AllTypesInsertBuilder = ct.put(AllTypes.doubleprecisionColNil, value)
    fun dateCol(value: java.time.LocalDate): AllTypesInsertBuilder = ct.put(AllTypes.dateCol, value)
    fun dateColNil(value: java.time.LocalDate?): AllTypesInsertBuilder = ct.put(AllTypes.dateColNil, value)
    fun timeCol(value: java.time.LocalTime): AllTypesInsertBuilder = ct.put(AllTypes.timeCol, value)
    fun timeColNil(value: java.time.LocalTime?): AllTypesInsertBuilder = ct.put(AllTypes.timeColNil, value)
    fun timewithtimezoneCol(value: java.time.OffsetDateTime): AllTypesInsertBuilder = ct.put(AllTypes.timewithtimezoneCol, value)
    fun timewithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesInsertBuilder = ct.put(AllTypes.timewithtimezoneColNil, value)
    fun timestampCol(value: java.time.Instant): AllTypesInsertBuilder = ct.put(AllTypes.timestampCol, value)
    fun timestampColNil(value: java.time.Instant?): AllTypesInsertBuilder = ct.put(AllTypes.timestampColNil, value)
    fun timestampwithtimezoneCol(value: java.time.OffsetDateTime): AllTypesInsertBuilder = ct.put(AllTypes.timestampwithtimezoneCol, value)
    fun timestampwithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesInsertBuilder = ct.put(AllTypes.timestampwithtimezoneColNil, value)
    fun uuidCol(value: java.util.UUID): AllTypesInsertBuilder = ct.put(AllTypes.uuidCol, value)
    fun uuidColNil(value: java.util.UUID?): AllTypesInsertBuilder = ct.put(AllTypes.uuidColNil, value)
    fun intervalCol(value: org.h2.api.Interval): AllTypesInsertBuilder = ct.put(AllTypes.intervalCol, value)
    fun intervalColNil(value: org.h2.api.Interval?): AllTypesInsertBuilder = ct.put(AllTypes.intervalColNil, value)
    fun geometryColNil(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.geometryColNil, value)
    fun intArrayCol(value: Array<Any>): AllTypesInsertBuilder = ct.put(AllTypes.intArrayCol, value)
    fun intArrayColNil(value: Array<Any>?): AllTypesInsertBuilder = ct.put(AllTypes.intArrayColNil, value)
    fun addressInt(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesInsertBuilder = ct.put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesInsertBuilder = ct.put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesInsertBuilder = ct.put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.integration.h2.custom.AddressType): AllTypesInsertBuilder = ct.put(AllTypes.addressStringNil, value)

    fun mandatoryColumns(characterCol: String, charactervaryingCol: String, characterlargeobjectCol: String, varcharIgnorecaseCol: String, enumCol: String, binaryCol: ByteArray, binaryvaryingCol: ByteArray, binarylargeobjectCol: java.sql.Blob, jsonCol: ByteArray, booleanCol: Boolean, tinyintCol: Byte, smallintCol: Int, integerCol: Int, bigintCol: Long, numericCol: java.math.BigDecimal, decfloatCol: java.math.BigDecimal, realCol: Float, doubleprecisionCol: Double, dateCol: java.time.LocalDate, timeCol: java.time.LocalTime, timewithtimezoneCol: java.time.OffsetDateTime, timestampCol: java.time.Instant, timestampwithtimezoneCol: java.time.OffsetDateTime, uuidCol: java.util.UUID, intervalCol: org.h2.api.Interval, intArrayCol: Array<Any>, addressInt: com.dbobjekts.integration.h2.custom.AddressType, addressIntNil: com.dbobjekts.integration.h2.custom.AddressType, addressString: com.dbobjekts.integration.h2.custom.AddressType, addressStringNil: com.dbobjekts.integration.h2.custom.AddressType) : AllTypesInsertBuilder {
      ct.put(AllTypes.characterCol, characterCol)
      ct.put(AllTypes.charactervaryingCol, charactervaryingCol)
      ct.put(AllTypes.characterlargeobjectCol, characterlargeobjectCol)
      ct.put(AllTypes.varcharIgnorecaseCol, varcharIgnorecaseCol)
      ct.put(AllTypes.enumCol, enumCol)
      ct.put(AllTypes.binaryCol, binaryCol)
      ct.put(AllTypes.binaryvaryingCol, binaryvaryingCol)
      ct.put(AllTypes.binarylargeobjectCol, binarylargeobjectCol)
      ct.put(AllTypes.jsonCol, jsonCol)
      ct.put(AllTypes.booleanCol, booleanCol)
      ct.put(AllTypes.tinyintCol, tinyintCol)
      ct.put(AllTypes.smallintCol, smallintCol)
      ct.put(AllTypes.integerCol, integerCol)
      ct.put(AllTypes.bigintCol, bigintCol)
      ct.put(AllTypes.numericCol, numericCol)
      ct.put(AllTypes.decfloatCol, decfloatCol)
      ct.put(AllTypes.realCol, realCol)
      ct.put(AllTypes.doubleprecisionCol, doubleprecisionCol)
      ct.put(AllTypes.dateCol, dateCol)
      ct.put(AllTypes.timeCol, timeCol)
      ct.put(AllTypes.timewithtimezoneCol, timewithtimezoneCol)
      ct.put(AllTypes.timestampCol, timestampCol)
      ct.put(AllTypes.timestampwithtimezoneCol, timestampwithtimezoneCol)
      ct.put(AllTypes.uuidCol, uuidCol)
      ct.put(AllTypes.intervalCol, intervalCol)
      ct.put(AllTypes.intArrayCol, intArrayCol)
      ct.put(AllTypes.addressInt, addressInt)
      ct.put(AllTypes.addressIntNil, addressIntNil)
      ct.put(AllTypes.addressString, addressString)
      ct.put(AllTypes.addressStringNil, addressStringNil)
      return this
    }

}

