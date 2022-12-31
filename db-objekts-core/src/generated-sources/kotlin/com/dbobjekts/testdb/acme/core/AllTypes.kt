package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.Entity
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object AllTypes:Table<AllTypesRow>("ALL_TYPES"), HasUpdateBuilder<AllTypesUpdateBuilder, AllTypesInsertBuilder> {
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
    val addressInt = com.dbobjekts.testdb.AddressTypeAsIntegerColumn(this, "ADDRESS_INT")
    val addressIntNil = com.dbobjekts.testdb.AddressTypeAsIntegerColumn(this, "ADDRESS_INT_NIL")
    val addressString = com.dbobjekts.testdb.AddressTypeAsStringColumn(this, "ADDRESS_STRING")
    val addressStringNil = com.dbobjekts.testdb.AddressTypeAsStringColumn(this, "ADDRESS_STRING_NIL")
    override val columns: List<AnyColumn> = listOf(id,characterCol,characterColNil,charactervaryingCol,charactervaryingColNil,characterlargeobjectCol,characterlargeobjectColNil,varcharIgnorecaseCol,varcharIgnorecaseColNil,enumCol,enumColNil,binaryCol,binaryColNil,binaryvaryingCol,binaryvaryingColNil,binarylargeobjectCol,binarylargeobjectColNil,jsonCol,jsonColNil,booleanCol,booleanColNil,tinyintCol,tinyintColNil,smallintCol,smallintColNil,integerCol,integerColNil,bigintCol,bigintColNil,numericCol,numericColNil,decfloatCol,decfloatColNil,realCol,realColNil,doubleprecisionCol,doubleprecisionColNil,dateCol,dateColNil,timeCol,timeColNil,timewithtimezoneCol,timewithtimezoneColNil,timestampCol,timestampColNil,timestampwithtimezoneCol,timestampwithtimezoneColNil,uuidCol,uuidColNil,intervalCol,intervalColNil,geometryColNil,intArrayCol,intArrayColNil,addressInt,addressIntNil,addressString,addressStringNil)
    override fun toValue(values: List<Any?>) = AllTypesRow(values[0] as Long,values[1] as String,values[2] as String?,values[3] as String,values[4] as String?,values[5] as String,values[6] as String?,values[7] as String,values[8] as String?,values[9] as String,values[10] as String?,values[11] as ByteArray,values[12] as ByteArray?,values[13] as ByteArray,values[14] as ByteArray?,values[15] as java.sql.Blob,values[16] as java.sql.Blob?,values[17] as ByteArray,values[18] as ByteArray?,values[19] as Boolean,values[20] as Boolean?,values[21] as Byte,values[22] as Byte?,values[23] as Int,values[24] as Int?,values[25] as Int,values[26] as Int?,values[27] as Long,values[28] as Long?,values[29] as java.math.BigDecimal,values[30] as java.math.BigDecimal?,values[31] as java.math.BigDecimal,values[32] as java.math.BigDecimal?,values[33] as Float,values[34] as Float?,values[35] as Double,values[36] as Double?,values[37] as java.time.LocalDate,values[38] as java.time.LocalDate?,values[39] as java.time.LocalTime,values[40] as java.time.LocalTime?,values[41] as java.time.OffsetDateTime,values[42] as java.time.OffsetDateTime?,values[43] as java.time.Instant,values[44] as java.time.Instant?,values[45] as java.time.OffsetDateTime,values[46] as java.time.OffsetDateTime?,values[47] as java.util.UUID,values[48] as java.util.UUID?,values[49] as org.h2.api.Interval,values[50] as org.h2.api.Interval?,values[51] as String?,values[52] as Array<Any>,values[53] as Array<Any>?,values[54] as com.dbobjekts.testdb.AddressType,values[55] as com.dbobjekts.testdb.AddressType,values[56] as com.dbobjekts.testdb.AddressType,values[57] as com.dbobjekts.testdb.AddressType)
    override fun metadata(): WriteQueryAccessors<AllTypesUpdateBuilder, AllTypesInsertBuilder> = WriteQueryAccessors(AllTypesUpdateBuilder(), AllTypesInsertBuilder())
}

class AllTypesUpdateBuilder() : UpdateBuilderBase(AllTypes) {
    fun characterCol(value: String): AllTypesUpdateBuilder = put(AllTypes.characterCol, value)
    fun characterColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.characterColNil, value)
    fun charactervaryingCol(value: String): AllTypesUpdateBuilder = put(AllTypes.charactervaryingCol, value)
    fun charactervaryingColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.charactervaryingColNil, value)
    fun characterlargeobjectCol(value: String): AllTypesUpdateBuilder = put(AllTypes.characterlargeobjectCol, value)
    fun characterlargeobjectColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.characterlargeobjectColNil, value)
    fun varcharIgnorecaseCol(value: String): AllTypesUpdateBuilder = put(AllTypes.varcharIgnorecaseCol, value)
    fun varcharIgnorecaseColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.varcharIgnorecaseColNil, value)
    fun enumCol(value: String): AllTypesUpdateBuilder = put(AllTypes.enumCol, value)
    fun enumColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.enumColNil, value)
    fun binaryCol(value: ByteArray): AllTypesUpdateBuilder = put(AllTypes.binaryCol, value)
    fun binaryColNil(value: ByteArray?): AllTypesUpdateBuilder = put(AllTypes.binaryColNil, value)
    fun binaryvaryingCol(value: ByteArray): AllTypesUpdateBuilder = put(AllTypes.binaryvaryingCol, value)
    fun binaryvaryingColNil(value: ByteArray?): AllTypesUpdateBuilder = put(AllTypes.binaryvaryingColNil, value)
    fun binarylargeobjectCol(value: java.sql.Blob): AllTypesUpdateBuilder = put(AllTypes.binarylargeobjectCol, value)
    fun binarylargeobjectColNil(value: java.sql.Blob?): AllTypesUpdateBuilder = put(AllTypes.binarylargeobjectColNil, value)
    fun jsonCol(value: ByteArray): AllTypesUpdateBuilder = put(AllTypes.jsonCol, value)
    fun jsonColNil(value: ByteArray?): AllTypesUpdateBuilder = put(AllTypes.jsonColNil, value)
    fun booleanCol(value: Boolean): AllTypesUpdateBuilder = put(AllTypes.booleanCol, value)
    fun booleanColNil(value: Boolean?): AllTypesUpdateBuilder = put(AllTypes.booleanColNil, value)
    fun tinyintCol(value: Byte): AllTypesUpdateBuilder = put(AllTypes.tinyintCol, value)
    fun tinyintColNil(value: Byte?): AllTypesUpdateBuilder = put(AllTypes.tinyintColNil, value)
    fun smallintCol(value: Int): AllTypesUpdateBuilder = put(AllTypes.smallintCol, value)
    fun smallintColNil(value: Int?): AllTypesUpdateBuilder = put(AllTypes.smallintColNil, value)
    fun integerCol(value: Int): AllTypesUpdateBuilder = put(AllTypes.integerCol, value)
    fun integerColNil(value: Int?): AllTypesUpdateBuilder = put(AllTypes.integerColNil, value)
    fun bigintCol(value: Long): AllTypesUpdateBuilder = put(AllTypes.bigintCol, value)
    fun bigintColNil(value: Long?): AllTypesUpdateBuilder = put(AllTypes.bigintColNil, value)
    fun numericCol(value: java.math.BigDecimal): AllTypesUpdateBuilder = put(AllTypes.numericCol, value)
    fun numericColNil(value: java.math.BigDecimal?): AllTypesUpdateBuilder = put(AllTypes.numericColNil, value)
    fun decfloatCol(value: java.math.BigDecimal): AllTypesUpdateBuilder = put(AllTypes.decfloatCol, value)
    fun decfloatColNil(value: java.math.BigDecimal?): AllTypesUpdateBuilder = put(AllTypes.decfloatColNil, value)
    fun realCol(value: Float): AllTypesUpdateBuilder = put(AllTypes.realCol, value)
    fun realColNil(value: Float?): AllTypesUpdateBuilder = put(AllTypes.realColNil, value)
    fun doubleprecisionCol(value: Double): AllTypesUpdateBuilder = put(AllTypes.doubleprecisionCol, value)
    fun doubleprecisionColNil(value: Double?): AllTypesUpdateBuilder = put(AllTypes.doubleprecisionColNil, value)
    fun dateCol(value: java.time.LocalDate): AllTypesUpdateBuilder = put(AllTypes.dateCol, value)
    fun dateColNil(value: java.time.LocalDate?): AllTypesUpdateBuilder = put(AllTypes.dateColNil, value)
    fun timeCol(value: java.time.LocalTime): AllTypesUpdateBuilder = put(AllTypes.timeCol, value)
    fun timeColNil(value: java.time.LocalTime?): AllTypesUpdateBuilder = put(AllTypes.timeColNil, value)
    fun timewithtimezoneCol(value: java.time.OffsetDateTime): AllTypesUpdateBuilder = put(AllTypes.timewithtimezoneCol, value)
    fun timewithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesUpdateBuilder = put(AllTypes.timewithtimezoneColNil, value)
    fun timestampCol(value: java.time.Instant): AllTypesUpdateBuilder = put(AllTypes.timestampCol, value)
    fun timestampColNil(value: java.time.Instant?): AllTypesUpdateBuilder = put(AllTypes.timestampColNil, value)
    fun timestampwithtimezoneCol(value: java.time.OffsetDateTime): AllTypesUpdateBuilder = put(AllTypes.timestampwithtimezoneCol, value)
    fun timestampwithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesUpdateBuilder = put(AllTypes.timestampwithtimezoneColNil, value)
    fun uuidCol(value: java.util.UUID): AllTypesUpdateBuilder = put(AllTypes.uuidCol, value)
    fun uuidColNil(value: java.util.UUID?): AllTypesUpdateBuilder = put(AllTypes.uuidColNil, value)
    fun intervalCol(value: org.h2.api.Interval): AllTypesUpdateBuilder = put(AllTypes.intervalCol, value)
    fun intervalColNil(value: org.h2.api.Interval?): AllTypesUpdateBuilder = put(AllTypes.intervalColNil, value)
    fun geometryColNil(value: String?): AllTypesUpdateBuilder = put(AllTypes.geometryColNil, value)
    fun intArrayCol(value: Array<Any>): AllTypesUpdateBuilder = put(AllTypes.intArrayCol, value)
    fun intArrayColNil(value: Array<Any>?): AllTypesUpdateBuilder = put(AllTypes.intArrayColNil, value)
    fun addressInt(value: com.dbobjekts.testdb.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.testdb.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.testdb.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.testdb.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressStringNil, value)

    override fun updateRow(entity: Entity<*, *>): Long {
      entity as AllTypesRow
      add(AllTypes.id, entity.id)
      add(AllTypes.characterCol, entity.characterCol)
      add(AllTypes.characterColNil, entity.characterColNil)
      add(AllTypes.charactervaryingCol, entity.charactervaryingCol)
      add(AllTypes.charactervaryingColNil, entity.charactervaryingColNil)
      add(AllTypes.characterlargeobjectCol, entity.characterlargeobjectCol)
      add(AllTypes.characterlargeobjectColNil, entity.characterlargeobjectColNil)
      add(AllTypes.varcharIgnorecaseCol, entity.varcharIgnorecaseCol)
      add(AllTypes.varcharIgnorecaseColNil, entity.varcharIgnorecaseColNil)
      add(AllTypes.enumCol, entity.enumCol)
      add(AllTypes.enumColNil, entity.enumColNil)
      add(AllTypes.binaryCol, entity.binaryCol)
      add(AllTypes.binaryColNil, entity.binaryColNil)
      add(AllTypes.binaryvaryingCol, entity.binaryvaryingCol)
      add(AllTypes.binaryvaryingColNil, entity.binaryvaryingColNil)
      add(AllTypes.binarylargeobjectCol, entity.binarylargeobjectCol)
      add(AllTypes.binarylargeobjectColNil, entity.binarylargeobjectColNil)
      add(AllTypes.jsonCol, entity.jsonCol)
      add(AllTypes.jsonColNil, entity.jsonColNil)
      add(AllTypes.booleanCol, entity.booleanCol)
      add(AllTypes.booleanColNil, entity.booleanColNil)
      add(AllTypes.tinyintCol, entity.tinyintCol)
      add(AllTypes.tinyintColNil, entity.tinyintColNil)
      add(AllTypes.smallintCol, entity.smallintCol)
      add(AllTypes.smallintColNil, entity.smallintColNil)
      add(AllTypes.integerCol, entity.integerCol)
      add(AllTypes.integerColNil, entity.integerColNil)
      add(AllTypes.bigintCol, entity.bigintCol)
      add(AllTypes.bigintColNil, entity.bigintColNil)
      add(AllTypes.numericCol, entity.numericCol)
      add(AllTypes.numericColNil, entity.numericColNil)
      add(AllTypes.decfloatCol, entity.decfloatCol)
      add(AllTypes.decfloatColNil, entity.decfloatColNil)
      add(AllTypes.realCol, entity.realCol)
      add(AllTypes.realColNil, entity.realColNil)
      add(AllTypes.doubleprecisionCol, entity.doubleprecisionCol)
      add(AllTypes.doubleprecisionColNil, entity.doubleprecisionColNil)
      add(AllTypes.dateCol, entity.dateCol)
      add(AllTypes.dateColNil, entity.dateColNil)
      add(AllTypes.timeCol, entity.timeCol)
      add(AllTypes.timeColNil, entity.timeColNil)
      add(AllTypes.timewithtimezoneCol, entity.timewithtimezoneCol)
      add(AllTypes.timewithtimezoneColNil, entity.timewithtimezoneColNil)
      add(AllTypes.timestampCol, entity.timestampCol)
      add(AllTypes.timestampColNil, entity.timestampColNil)
      add(AllTypes.timestampwithtimezoneCol, entity.timestampwithtimezoneCol)
      add(AllTypes.timestampwithtimezoneColNil, entity.timestampwithtimezoneColNil)
      add(AllTypes.uuidCol, entity.uuidCol)
      add(AllTypes.uuidColNil, entity.uuidColNil)
      add(AllTypes.intervalCol, entity.intervalCol)
      add(AllTypes.intervalColNil, entity.intervalColNil)
      add(AllTypes.geometryColNil, entity.geometryColNil)
      add(AllTypes.intArrayCol, entity.intArrayCol)
      add(AllTypes.intArrayColNil, entity.intArrayColNil)
      add(AllTypes.addressInt, entity.addressInt)
      add(AllTypes.addressIntNil, entity.addressIntNil)
      add(AllTypes.addressString, entity.addressString)
      add(AllTypes.addressStringNil, entity.addressStringNil)
      return where (AllTypes.id.eq(entity.id))
    }    
        
}

class AllTypesInsertBuilder():InsertBuilderBase(){
    fun characterCol(value: String): AllTypesInsertBuilder = put(AllTypes.characterCol, value)
    fun characterColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.characterColNil, value)
    fun charactervaryingCol(value: String): AllTypesInsertBuilder = put(AllTypes.charactervaryingCol, value)
    fun charactervaryingColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.charactervaryingColNil, value)
    fun characterlargeobjectCol(value: String): AllTypesInsertBuilder = put(AllTypes.characterlargeobjectCol, value)
    fun characterlargeobjectColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.characterlargeobjectColNil, value)
    fun varcharIgnorecaseCol(value: String): AllTypesInsertBuilder = put(AllTypes.varcharIgnorecaseCol, value)
    fun varcharIgnorecaseColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.varcharIgnorecaseColNil, value)
    fun enumCol(value: String): AllTypesInsertBuilder = put(AllTypes.enumCol, value)
    fun enumColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.enumColNil, value)
    fun binaryCol(value: ByteArray): AllTypesInsertBuilder = put(AllTypes.binaryCol, value)
    fun binaryColNil(value: ByteArray?): AllTypesInsertBuilder = put(AllTypes.binaryColNil, value)
    fun binaryvaryingCol(value: ByteArray): AllTypesInsertBuilder = put(AllTypes.binaryvaryingCol, value)
    fun binaryvaryingColNil(value: ByteArray?): AllTypesInsertBuilder = put(AllTypes.binaryvaryingColNil, value)
    fun binarylargeobjectCol(value: java.sql.Blob): AllTypesInsertBuilder = put(AllTypes.binarylargeobjectCol, value)
    fun binarylargeobjectColNil(value: java.sql.Blob?): AllTypesInsertBuilder = put(AllTypes.binarylargeobjectColNil, value)
    fun jsonCol(value: ByteArray): AllTypesInsertBuilder = put(AllTypes.jsonCol, value)
    fun jsonColNil(value: ByteArray?): AllTypesInsertBuilder = put(AllTypes.jsonColNil, value)
    fun booleanCol(value: Boolean): AllTypesInsertBuilder = put(AllTypes.booleanCol, value)
    fun booleanColNil(value: Boolean?): AllTypesInsertBuilder = put(AllTypes.booleanColNil, value)
    fun tinyintCol(value: Byte): AllTypesInsertBuilder = put(AllTypes.tinyintCol, value)
    fun tinyintColNil(value: Byte?): AllTypesInsertBuilder = put(AllTypes.tinyintColNil, value)
    fun smallintCol(value: Int): AllTypesInsertBuilder = put(AllTypes.smallintCol, value)
    fun smallintColNil(value: Int?): AllTypesInsertBuilder = put(AllTypes.smallintColNil, value)
    fun integerCol(value: Int): AllTypesInsertBuilder = put(AllTypes.integerCol, value)
    fun integerColNil(value: Int?): AllTypesInsertBuilder = put(AllTypes.integerColNil, value)
    fun bigintCol(value: Long): AllTypesInsertBuilder = put(AllTypes.bigintCol, value)
    fun bigintColNil(value: Long?): AllTypesInsertBuilder = put(AllTypes.bigintColNil, value)
    fun numericCol(value: java.math.BigDecimal): AllTypesInsertBuilder = put(AllTypes.numericCol, value)
    fun numericColNil(value: java.math.BigDecimal?): AllTypesInsertBuilder = put(AllTypes.numericColNil, value)
    fun decfloatCol(value: java.math.BigDecimal): AllTypesInsertBuilder = put(AllTypes.decfloatCol, value)
    fun decfloatColNil(value: java.math.BigDecimal?): AllTypesInsertBuilder = put(AllTypes.decfloatColNil, value)
    fun realCol(value: Float): AllTypesInsertBuilder = put(AllTypes.realCol, value)
    fun realColNil(value: Float?): AllTypesInsertBuilder = put(AllTypes.realColNil, value)
    fun doubleprecisionCol(value: Double): AllTypesInsertBuilder = put(AllTypes.doubleprecisionCol, value)
    fun doubleprecisionColNil(value: Double?): AllTypesInsertBuilder = put(AllTypes.doubleprecisionColNil, value)
    fun dateCol(value: java.time.LocalDate): AllTypesInsertBuilder = put(AllTypes.dateCol, value)
    fun dateColNil(value: java.time.LocalDate?): AllTypesInsertBuilder = put(AllTypes.dateColNil, value)
    fun timeCol(value: java.time.LocalTime): AllTypesInsertBuilder = put(AllTypes.timeCol, value)
    fun timeColNil(value: java.time.LocalTime?): AllTypesInsertBuilder = put(AllTypes.timeColNil, value)
    fun timewithtimezoneCol(value: java.time.OffsetDateTime): AllTypesInsertBuilder = put(AllTypes.timewithtimezoneCol, value)
    fun timewithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesInsertBuilder = put(AllTypes.timewithtimezoneColNil, value)
    fun timestampCol(value: java.time.Instant): AllTypesInsertBuilder = put(AllTypes.timestampCol, value)
    fun timestampColNil(value: java.time.Instant?): AllTypesInsertBuilder = put(AllTypes.timestampColNil, value)
    fun timestampwithtimezoneCol(value: java.time.OffsetDateTime): AllTypesInsertBuilder = put(AllTypes.timestampwithtimezoneCol, value)
    fun timestampwithtimezoneColNil(value: java.time.OffsetDateTime?): AllTypesInsertBuilder = put(AllTypes.timestampwithtimezoneColNil, value)
    fun uuidCol(value: java.util.UUID): AllTypesInsertBuilder = put(AllTypes.uuidCol, value)
    fun uuidColNil(value: java.util.UUID?): AllTypesInsertBuilder = put(AllTypes.uuidColNil, value)
    fun intervalCol(value: org.h2.api.Interval): AllTypesInsertBuilder = put(AllTypes.intervalCol, value)
    fun intervalColNil(value: org.h2.api.Interval?): AllTypesInsertBuilder = put(AllTypes.intervalColNil, value)
    fun geometryColNil(value: String?): AllTypesInsertBuilder = put(AllTypes.geometryColNil, value)
    fun intArrayCol(value: Array<Any>): AllTypesInsertBuilder = put(AllTypes.intArrayCol, value)
    fun intArrayColNil(value: Array<Any>?): AllTypesInsertBuilder = put(AllTypes.intArrayColNil, value)
    fun addressInt(value: com.dbobjekts.testdb.AddressType): AllTypesInsertBuilder = put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.testdb.AddressType): AllTypesInsertBuilder = put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.testdb.AddressType): AllTypesInsertBuilder = put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.testdb.AddressType): AllTypesInsertBuilder = put(AllTypes.addressStringNil, value)

    fun mandatoryColumns(characterCol: String, charactervaryingCol: String, characterlargeobjectCol: String, varcharIgnorecaseCol: String, enumCol: String, binaryCol: ByteArray, binaryvaryingCol: ByteArray, binarylargeobjectCol: java.sql.Blob, jsonCol: ByteArray, booleanCol: Boolean, tinyintCol: Byte, smallintCol: Int, integerCol: Int, bigintCol: Long, numericCol: java.math.BigDecimal, decfloatCol: java.math.BigDecimal, realCol: Float, doubleprecisionCol: Double, dateCol: java.time.LocalDate, timeCol: java.time.LocalTime, timewithtimezoneCol: java.time.OffsetDateTime, timestampCol: java.time.Instant, timestampwithtimezoneCol: java.time.OffsetDateTime, uuidCol: java.util.UUID, intervalCol: org.h2.api.Interval, intArrayCol: Array<Any>, addressInt: com.dbobjekts.testdb.AddressType, addressIntNil: com.dbobjekts.testdb.AddressType, addressString: com.dbobjekts.testdb.AddressType, addressStringNil: com.dbobjekts.testdb.AddressType) : AllTypesInsertBuilder {
      mandatory(AllTypes.characterCol, characterCol)
      mandatory(AllTypes.charactervaryingCol, charactervaryingCol)
      mandatory(AllTypes.characterlargeobjectCol, characterlargeobjectCol)
      mandatory(AllTypes.varcharIgnorecaseCol, varcharIgnorecaseCol)
      mandatory(AllTypes.enumCol, enumCol)
      mandatory(AllTypes.binaryCol, binaryCol)
      mandatory(AllTypes.binaryvaryingCol, binaryvaryingCol)
      mandatory(AllTypes.binarylargeobjectCol, binarylargeobjectCol)
      mandatory(AllTypes.jsonCol, jsonCol)
      mandatory(AllTypes.booleanCol, booleanCol)
      mandatory(AllTypes.tinyintCol, tinyintCol)
      mandatory(AllTypes.smallintCol, smallintCol)
      mandatory(AllTypes.integerCol, integerCol)
      mandatory(AllTypes.bigintCol, bigintCol)
      mandatory(AllTypes.numericCol, numericCol)
      mandatory(AllTypes.decfloatCol, decfloatCol)
      mandatory(AllTypes.realCol, realCol)
      mandatory(AllTypes.doubleprecisionCol, doubleprecisionCol)
      mandatory(AllTypes.dateCol, dateCol)
      mandatory(AllTypes.timeCol, timeCol)
      mandatory(AllTypes.timewithtimezoneCol, timewithtimezoneCol)
      mandatory(AllTypes.timestampCol, timestampCol)
      mandatory(AllTypes.timestampwithtimezoneCol, timestampwithtimezoneCol)
      mandatory(AllTypes.uuidCol, uuidCol)
      mandatory(AllTypes.intervalCol, intervalCol)
      mandatory(AllTypes.intArrayCol, intArrayCol)
      mandatory(AllTypes.addressInt, addressInt)
      mandatory(AllTypes.addressIntNil, addressIntNil)
      mandatory(AllTypes.addressString, addressString)
      mandatory(AllTypes.addressStringNil, addressStringNil)
      return this
    }


    override fun insertRow(entity: Entity<*, *>): Long {
      entity as AllTypesRow
      add(AllTypes.characterCol, entity.characterCol)
      add(AllTypes.characterColNil, entity.characterColNil)
      add(AllTypes.charactervaryingCol, entity.charactervaryingCol)
      add(AllTypes.charactervaryingColNil, entity.charactervaryingColNil)
      add(AllTypes.characterlargeobjectCol, entity.characterlargeobjectCol)
      add(AllTypes.characterlargeobjectColNil, entity.characterlargeobjectColNil)
      add(AllTypes.varcharIgnorecaseCol, entity.varcharIgnorecaseCol)
      add(AllTypes.varcharIgnorecaseColNil, entity.varcharIgnorecaseColNil)
      add(AllTypes.enumCol, entity.enumCol)
      add(AllTypes.enumColNil, entity.enumColNil)
      add(AllTypes.binaryCol, entity.binaryCol)
      add(AllTypes.binaryColNil, entity.binaryColNil)
      add(AllTypes.binaryvaryingCol, entity.binaryvaryingCol)
      add(AllTypes.binaryvaryingColNil, entity.binaryvaryingColNil)
      add(AllTypes.binarylargeobjectCol, entity.binarylargeobjectCol)
      add(AllTypes.binarylargeobjectColNil, entity.binarylargeobjectColNil)
      add(AllTypes.jsonCol, entity.jsonCol)
      add(AllTypes.jsonColNil, entity.jsonColNil)
      add(AllTypes.booleanCol, entity.booleanCol)
      add(AllTypes.booleanColNil, entity.booleanColNil)
      add(AllTypes.tinyintCol, entity.tinyintCol)
      add(AllTypes.tinyintColNil, entity.tinyintColNil)
      add(AllTypes.smallintCol, entity.smallintCol)
      add(AllTypes.smallintColNil, entity.smallintColNil)
      add(AllTypes.integerCol, entity.integerCol)
      add(AllTypes.integerColNil, entity.integerColNil)
      add(AllTypes.bigintCol, entity.bigintCol)
      add(AllTypes.bigintColNil, entity.bigintColNil)
      add(AllTypes.numericCol, entity.numericCol)
      add(AllTypes.numericColNil, entity.numericColNil)
      add(AllTypes.decfloatCol, entity.decfloatCol)
      add(AllTypes.decfloatColNil, entity.decfloatColNil)
      add(AllTypes.realCol, entity.realCol)
      add(AllTypes.realColNil, entity.realColNil)
      add(AllTypes.doubleprecisionCol, entity.doubleprecisionCol)
      add(AllTypes.doubleprecisionColNil, entity.doubleprecisionColNil)
      add(AllTypes.dateCol, entity.dateCol)
      add(AllTypes.dateColNil, entity.dateColNil)
      add(AllTypes.timeCol, entity.timeCol)
      add(AllTypes.timeColNil, entity.timeColNil)
      add(AllTypes.timewithtimezoneCol, entity.timewithtimezoneCol)
      add(AllTypes.timewithtimezoneColNil, entity.timewithtimezoneColNil)
      add(AllTypes.timestampCol, entity.timestampCol)
      add(AllTypes.timestampColNil, entity.timestampColNil)
      add(AllTypes.timestampwithtimezoneCol, entity.timestampwithtimezoneCol)
      add(AllTypes.timestampwithtimezoneColNil, entity.timestampwithtimezoneColNil)
      add(AllTypes.uuidCol, entity.uuidCol)
      add(AllTypes.uuidColNil, entity.uuidColNil)
      add(AllTypes.intervalCol, entity.intervalCol)
      add(AllTypes.intervalColNil, entity.intervalColNil)
      add(AllTypes.geometryColNil, entity.geometryColNil)
      add(AllTypes.intArrayCol, entity.intArrayCol)
      add(AllTypes.intArrayColNil, entity.intArrayColNil)
      add(AllTypes.addressInt, entity.addressInt)
      add(AllTypes.addressIntNil, entity.addressIntNil)
      add(AllTypes.addressString, entity.addressString)
      add(AllTypes.addressStringNil, entity.addressStringNil)
      return execute()
    }    
        
}


data class AllTypesRow(
  val id: Long = 0,
  val characterCol: String,
  val characterColNil: String?,
  val charactervaryingCol: String,
  val charactervaryingColNil: String?,
  val characterlargeobjectCol: String,
  val characterlargeobjectColNil: String?,
  val varcharIgnorecaseCol: String,
  val varcharIgnorecaseColNil: String?,
  val enumCol: String,
  val enumColNil: String?,
  val binaryCol: ByteArray,
  val binaryColNil: ByteArray?,
  val binaryvaryingCol: ByteArray,
  val binaryvaryingColNil: ByteArray?,
  val binarylargeobjectCol: java.sql.Blob,
  val binarylargeobjectColNil: java.sql.Blob?,
  val jsonCol: ByteArray,
  val jsonColNil: ByteArray?,
  val booleanCol: Boolean,
  val booleanColNil: Boolean?,
  val tinyintCol: Byte,
  val tinyintColNil: Byte?,
  val smallintCol: Int,
  val smallintColNil: Int?,
  val integerCol: Int,
  val integerColNil: Int?,
  val bigintCol: Long,
  val bigintColNil: Long?,
  val numericCol: java.math.BigDecimal,
  val numericColNil: java.math.BigDecimal?,
  val decfloatCol: java.math.BigDecimal,
  val decfloatColNil: java.math.BigDecimal?,
  val realCol: Float,
  val realColNil: Float?,
  val doubleprecisionCol: Double,
  val doubleprecisionColNil: Double?,
  val dateCol: java.time.LocalDate,
  val dateColNil: java.time.LocalDate?,
  val timeCol: java.time.LocalTime,
  val timeColNil: java.time.LocalTime?,
  val timewithtimezoneCol: java.time.OffsetDateTime,
  val timewithtimezoneColNil: java.time.OffsetDateTime?,
  val timestampCol: java.time.Instant,
  val timestampColNil: java.time.Instant?,
  val timestampwithtimezoneCol: java.time.OffsetDateTime,
  val timestampwithtimezoneColNil: java.time.OffsetDateTime?,
  val uuidCol: java.util.UUID,
  val uuidColNil: java.util.UUID?,
  val intervalCol: org.h2.api.Interval,
  val intervalColNil: org.h2.api.Interval?,
  val geometryColNil: String?,
  val intArrayCol: Array<Any>,
  val intArrayColNil: Array<Any>?,
  val addressInt: com.dbobjekts.testdb.AddressType,
  val addressIntNil: com.dbobjekts.testdb.AddressType,
  val addressString: com.dbobjekts.testdb.AddressType,
  val addressStringNil: com.dbobjekts.testdb.AddressType    
) : Entity<AllTypesUpdateBuilder, AllTypesInsertBuilder>(AllTypes.metadata())
        
