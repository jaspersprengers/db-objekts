package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.TableRowData
import com.dbobjekts.fixture.columns.AddressTypeAsIntegerColumn
import com.dbobjekts.fixture.columns.AddressTypeAsStringColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.metadata.column.BigDecimalColumn
import com.dbobjekts.metadata.column.BlobColumn
import com.dbobjekts.metadata.column.BooleanColumn
import com.dbobjekts.metadata.column.ByteArrayColumn
import com.dbobjekts.metadata.column.ByteColumn
import com.dbobjekts.metadata.column.DateColumn
import com.dbobjekts.metadata.column.DoubleColumn
import com.dbobjekts.metadata.column.FloatColumn
import com.dbobjekts.metadata.column.IntegerColumn
import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.metadata.column.NullableBigDecimalColumn
import com.dbobjekts.metadata.column.NullableBlobColumn
import com.dbobjekts.metadata.column.NullableBooleanColumn
import com.dbobjekts.metadata.column.NullableByteArrayColumn
import com.dbobjekts.metadata.column.NullableByteColumn
import com.dbobjekts.metadata.column.NullableDateColumn
import com.dbobjekts.metadata.column.NullableDoubleColumn
import com.dbobjekts.metadata.column.NullableFloatColumn
import com.dbobjekts.metadata.column.NullableIntegerColumn
import com.dbobjekts.metadata.column.NullableLongColumn
import com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn
import com.dbobjekts.metadata.column.NullableTimeColumn
import com.dbobjekts.metadata.column.NullableTimeStampColumn
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.OffsetDateTimeColumn
import com.dbobjekts.metadata.column.TimeColumn
import com.dbobjekts.metadata.column.TimeStampColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.vendors.h2.IntervalColumn
import com.dbobjekts.vendors.h2.NullableIntervalColumn
import com.dbobjekts.vendors.h2.NullableObjectArrayColumn
import com.dbobjekts.vendors.h2.NullableUUIDColumn
import com.dbobjekts.vendors.h2.ObjectArrayColumn
import com.dbobjekts.vendors.h2.UUIDColumn

/**           
 * Auto-generated metadata object for db table CORE.ALL_TYPES.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: 
 */
object AllTypes:Table<AllTypesRow>("ALL_TYPES"), HasUpdateBuilder<AllTypesUpdateBuilder, AllTypesInsertBuilder> {
    /**
     * Represents db column CORE.ALL_TYPES.ID
     */
    val id = AutoKeyLongColumn(this, "ID")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTER_COL
     */
    val characterCol = VarcharColumn(this, "CHARACTER_COL")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTER_COL_NIL
     */
    val characterColNil = NullableVarcharColumn(this, "CHARACTER_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTERVARYING_COL
     */
    val charactervaryingCol = VarcharColumn(this, "CHARACTERVARYING_COL")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTERVARYING_COL_NIL
     */
    val charactervaryingColNil = NullableVarcharColumn(this, "CHARACTERVARYING_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTERLARGEOBJECT_COL
     */
    val characterlargeobjectCol = VarcharColumn(this, "CHARACTERLARGEOBJECT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.CHARACTERLARGEOBJECT_COL_NIL
     */
    val characterlargeobjectColNil = NullableVarcharColumn(this, "CHARACTERLARGEOBJECT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.VARCHAR_IGNORECASE_COL
     */
    val varcharIgnorecaseCol = VarcharColumn(this, "VARCHAR_IGNORECASE_COL")
    /**
     * Represents db column CORE.ALL_TYPES.VARCHAR_IGNORECASE_COL_NIL
     */
    val varcharIgnorecaseColNil = NullableVarcharColumn(this, "VARCHAR_IGNORECASE_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.ENUM_COL
     */
    val enumCol = VarcharColumn(this, "ENUM_COL")
    /**
     * Represents db column CORE.ALL_TYPES.ENUM_COL_NIL
     */
    val enumColNil = NullableVarcharColumn(this, "ENUM_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARY_COL
     */
    val binaryCol = ByteArrayColumn(this, "BINARY_COL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARY_COL_NIL
     */
    val binaryColNil = NullableByteArrayColumn(this, "BINARY_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARYVARYING_COL
     */
    val binaryvaryingCol = ByteArrayColumn(this, "BINARYVARYING_COL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARYVARYING_COL_NIL
     */
    val binaryvaryingColNil = NullableByteArrayColumn(this, "BINARYVARYING_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARYLARGEOBJECT_COL
     */
    val binarylargeobjectCol = BlobColumn(this, "BINARYLARGEOBJECT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.BINARYLARGEOBJECT_COL_NIL
     */
    val binarylargeobjectColNil = NullableBlobColumn(this, "BINARYLARGEOBJECT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.JSON_COL
     */
    val jsonCol = ByteArrayColumn(this, "JSON_COL")
    /**
     * Represents db column CORE.ALL_TYPES.JSON_COL_NIL
     */
    val jsonColNil = NullableByteArrayColumn(this, "JSON_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.BOOLEAN_COL
     */
    val booleanCol = BooleanColumn(this, "BOOLEAN_COL")
    /**
     * Represents db column CORE.ALL_TYPES.BOOLEAN_COL_NIL
     */
    val booleanColNil = NullableBooleanColumn(this, "BOOLEAN_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.TINYINT_COL
     */
    val tinyintCol = ByteColumn(this, "TINYINT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.TINYINT_COL_NIL
     */
    val tinyintColNil = NullableByteColumn(this, "TINYINT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.SMALLINT_COL
     */
    val smallintCol = IntegerColumn(this, "SMALLINT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.SMALLINT_COL_NIL
     */
    val smallintColNil = NullableIntegerColumn(this, "SMALLINT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.INTEGER_COL
     */
    val integerCol = IntegerColumn(this, "INTEGER_COL")
    /**
     * Represents db column CORE.ALL_TYPES.INTEGER_COL_NIL
     */
    val integerColNil = NullableIntegerColumn(this, "INTEGER_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.BIGINT_COL
     */
    val bigintCol = LongColumn(this, "BIGINT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.BIGINT_COL_NIL
     */
    val bigintColNil = NullableLongColumn(this, "BIGINT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.NUMERIC_COL
     */
    val numericCol = BigDecimalColumn(this, "NUMERIC_COL")
    /**
     * Represents db column CORE.ALL_TYPES.NUMERIC_COL_NIL
     */
    val numericColNil = NullableBigDecimalColumn(this, "NUMERIC_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.DECFLOAT_COL
     */
    val decfloatCol = BigDecimalColumn(this, "DECFLOAT_COL")
    /**
     * Represents db column CORE.ALL_TYPES.DECFLOAT_COL_NIL
     */
    val decfloatColNil = NullableBigDecimalColumn(this, "DECFLOAT_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.REAL_COL
     */
    val realCol = FloatColumn(this, "REAL_COL")
    /**
     * Represents db column CORE.ALL_TYPES.REAL_COL_NIL
     */
    val realColNil = NullableFloatColumn(this, "REAL_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.DOUBLEPRECISION_COL
     */
    val doubleprecisionCol = DoubleColumn(this, "DOUBLEPRECISION_COL")
    /**
     * Represents db column CORE.ALL_TYPES.DOUBLEPRECISION_COL_NIL
     */
    val doubleprecisionColNil = NullableDoubleColumn(this, "DOUBLEPRECISION_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.DATE_COL
     */
    val dateCol = DateColumn(this, "DATE_COL")
    /**
     * Represents db column CORE.ALL_TYPES.DATE_COL_NIL
     */
    val dateColNil = NullableDateColumn(this, "DATE_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.TIME_COL
     */
    val timeCol = TimeColumn(this, "TIME_COL")
    /**
     * Represents db column CORE.ALL_TYPES.TIME_COL_NIL
     */
    val timeColNil = NullableTimeColumn(this, "TIME_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMEWITHTIMEZONE_COL
     */
    val timewithtimezoneCol = OffsetDateTimeColumn(this, "TIMEWITHTIMEZONE_COL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMEWITHTIMEZONE_COL_NIL
     */
    val timewithtimezoneColNil = NullableOffsetDateTimeColumn(this, "TIMEWITHTIMEZONE_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMESTAMP_COL
     */
    val timestampCol = TimeStampColumn(this, "TIMESTAMP_COL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMESTAMP_COL_NIL
     */
    val timestampColNil = NullableTimeStampColumn(this, "TIMESTAMP_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMESTAMPWITHTIMEZONE_COL
     */
    val timestampwithtimezoneCol = OffsetDateTimeColumn(this, "TIMESTAMPWITHTIMEZONE_COL")
    /**
     * Represents db column CORE.ALL_TYPES.TIMESTAMPWITHTIMEZONE_COL_NIL
     */
    val timestampwithtimezoneColNil = NullableOffsetDateTimeColumn(this, "TIMESTAMPWITHTIMEZONE_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.UUID_COL
     */
    val uuidCol = UUIDColumn(this, "UUID_COL")
    /**
     * Represents db column CORE.ALL_TYPES.UUID_COL_NIL
     */
    val uuidColNil = NullableUUIDColumn(this, "UUID_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.INTERVAL_COL
     */
    val intervalCol = IntervalColumn(this, "INTERVAL_COL")
    /**
     * Represents db column CORE.ALL_TYPES.INTERVAL_COL_NIL
     */
    val intervalColNil = NullableIntervalColumn(this, "INTERVAL_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.GEOMETRY_COL_NIL
     */
    val geometryColNil = NullableVarcharColumn(this, "GEOMETRY_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.INT_ARRAY_COL
     */
    val intArrayCol = ObjectArrayColumn(this, "INT_ARRAY_COL")
    /**
     * Represents db column CORE.ALL_TYPES.INT_ARRAY_COL_NIL
     */
    val intArrayColNil = NullableObjectArrayColumn(this, "INT_ARRAY_COL_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.ADDRESS_INT
     */
    val addressInt = AddressTypeAsIntegerColumn(this, "ADDRESS_INT")
    /**
     * Represents db column CORE.ALL_TYPES.ADDRESS_INT_NIL
     */
    val addressIntNil = AddressTypeAsIntegerColumn(this, "ADDRESS_INT_NIL")
    /**
     * Represents db column CORE.ALL_TYPES.ADDRESS_STRING
     */
    val addressString = AddressTypeAsStringColumn(this, "ADDRESS_STRING")
    /**
     * Represents db column CORE.ALL_TYPES.ADDRESS_STRING_NIL
     */
    val addressStringNil = AddressTypeAsStringColumn(this, "ADDRESS_STRING_NIL")
    override val columns: List<AnyColumn> = listOf(id,characterCol,characterColNil,charactervaryingCol,charactervaryingColNil,characterlargeobjectCol,characterlargeobjectColNil,varcharIgnorecaseCol,varcharIgnorecaseColNil,enumCol,enumColNil,binaryCol,binaryColNil,binaryvaryingCol,binaryvaryingColNil,binarylargeobjectCol,binarylargeobjectColNil,jsonCol,jsonColNil,booleanCol,booleanColNil,tinyintCol,tinyintColNil,smallintCol,smallintColNil,integerCol,integerColNil,bigintCol,bigintColNil,numericCol,numericColNil,decfloatCol,decfloatColNil,realCol,realColNil,doubleprecisionCol,doubleprecisionColNil,dateCol,dateColNil,timeCol,timeColNil,timewithtimezoneCol,timewithtimezoneColNil,timestampCol,timestampColNil,timestampwithtimezoneCol,timestampwithtimezoneColNil,uuidCol,uuidColNil,intervalCol,intervalColNil,geometryColNil,intArrayCol,intArrayColNil,addressInt,addressIntNil,addressString,addressStringNil)
    override fun toValue(values: List<Any?>) = AllTypesRow(values[0] as Long,values[1] as String,values[2] as String?,values[3] as String,values[4] as String?,values[5] as String,values[6] as String?,values[7] as String,values[8] as String?,values[9] as String,values[10] as String?,values[11] as ByteArray,values[12] as ByteArray?,values[13] as ByteArray,values[14] as ByteArray?,values[15] as java.sql.Blob,values[16] as java.sql.Blob?,values[17] as ByteArray,values[18] as ByteArray?,values[19] as Boolean,values[20] as Boolean?,values[21] as Byte,values[22] as Byte?,values[23] as Int,values[24] as Int?,values[25] as Int,values[26] as Int?,values[27] as Long,values[28] as Long?,values[29] as java.math.BigDecimal,values[30] as java.math.BigDecimal?,values[31] as java.math.BigDecimal,values[32] as java.math.BigDecimal?,values[33] as Float,values[34] as Float?,values[35] as Double,values[36] as Double?,values[37] as java.time.LocalDate,values[38] as java.time.LocalDate?,values[39] as java.time.LocalTime,values[40] as java.time.LocalTime?,values[41] as java.time.OffsetDateTime,values[42] as java.time.OffsetDateTime?,values[43] as java.time.Instant,values[44] as java.time.Instant?,values[45] as java.time.OffsetDateTime,values[46] as java.time.OffsetDateTime?,values[47] as java.util.UUID,values[48] as java.util.UUID?,values[49] as org.h2.api.Interval,values[50] as org.h2.api.Interval?,values[51] as String?,values[52] as Array<Any>,values[53] as Array<Any>?,values[54] as com.dbobjekts.fixture.columns.AddressType,values[55] as com.dbobjekts.fixture.columns.AddressType,values[56] as com.dbobjekts.fixture.columns.AddressType,values[57] as com.dbobjekts.fixture.columns.AddressType)
    override fun metadata(): WriteQueryAccessors<AllTypesUpdateBuilder, AllTypesInsertBuilder> = WriteQueryAccessors(AllTypesUpdateBuilder(), AllTypesInsertBuilder())

}

class AllTypesJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
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
    fun addressInt(value: com.dbobjekts.fixture.columns.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.fixture.columns.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.fixture.columns.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.fixture.columns.AddressType): AllTypesUpdateBuilder = put(AllTypes.addressStringNil, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AllTypesRow
      add(AllTypes.id, rowData.id)
      add(AllTypes.characterCol, rowData.characterCol)
      add(AllTypes.characterColNil, rowData.characterColNil)
      add(AllTypes.charactervaryingCol, rowData.charactervaryingCol)
      add(AllTypes.charactervaryingColNil, rowData.charactervaryingColNil)
      add(AllTypes.characterlargeobjectCol, rowData.characterlargeobjectCol)
      add(AllTypes.characterlargeobjectColNil, rowData.characterlargeobjectColNil)
      add(AllTypes.varcharIgnorecaseCol, rowData.varcharIgnorecaseCol)
      add(AllTypes.varcharIgnorecaseColNil, rowData.varcharIgnorecaseColNil)
      add(AllTypes.enumCol, rowData.enumCol)
      add(AllTypes.enumColNil, rowData.enumColNil)
      add(AllTypes.binaryCol, rowData.binaryCol)
      add(AllTypes.binaryColNil, rowData.binaryColNil)
      add(AllTypes.binaryvaryingCol, rowData.binaryvaryingCol)
      add(AllTypes.binaryvaryingColNil, rowData.binaryvaryingColNil)
      add(AllTypes.binarylargeobjectCol, rowData.binarylargeobjectCol)
      add(AllTypes.binarylargeobjectColNil, rowData.binarylargeobjectColNil)
      add(AllTypes.jsonCol, rowData.jsonCol)
      add(AllTypes.jsonColNil, rowData.jsonColNil)
      add(AllTypes.booleanCol, rowData.booleanCol)
      add(AllTypes.booleanColNil, rowData.booleanColNil)
      add(AllTypes.tinyintCol, rowData.tinyintCol)
      add(AllTypes.tinyintColNil, rowData.tinyintColNil)
      add(AllTypes.smallintCol, rowData.smallintCol)
      add(AllTypes.smallintColNil, rowData.smallintColNil)
      add(AllTypes.integerCol, rowData.integerCol)
      add(AllTypes.integerColNil, rowData.integerColNil)
      add(AllTypes.bigintCol, rowData.bigintCol)
      add(AllTypes.bigintColNil, rowData.bigintColNil)
      add(AllTypes.numericCol, rowData.numericCol)
      add(AllTypes.numericColNil, rowData.numericColNil)
      add(AllTypes.decfloatCol, rowData.decfloatCol)
      add(AllTypes.decfloatColNil, rowData.decfloatColNil)
      add(AllTypes.realCol, rowData.realCol)
      add(AllTypes.realColNil, rowData.realColNil)
      add(AllTypes.doubleprecisionCol, rowData.doubleprecisionCol)
      add(AllTypes.doubleprecisionColNil, rowData.doubleprecisionColNil)
      add(AllTypes.dateCol, rowData.dateCol)
      add(AllTypes.dateColNil, rowData.dateColNil)
      add(AllTypes.timeCol, rowData.timeCol)
      add(AllTypes.timeColNil, rowData.timeColNil)
      add(AllTypes.timewithtimezoneCol, rowData.timewithtimezoneCol)
      add(AllTypes.timewithtimezoneColNil, rowData.timewithtimezoneColNil)
      add(AllTypes.timestampCol, rowData.timestampCol)
      add(AllTypes.timestampColNil, rowData.timestampColNil)
      add(AllTypes.timestampwithtimezoneCol, rowData.timestampwithtimezoneCol)
      add(AllTypes.timestampwithtimezoneColNil, rowData.timestampwithtimezoneColNil)
      add(AllTypes.uuidCol, rowData.uuidCol)
      add(AllTypes.uuidColNil, rowData.uuidColNil)
      add(AllTypes.intervalCol, rowData.intervalCol)
      add(AllTypes.intervalColNil, rowData.intervalColNil)
      add(AllTypes.geometryColNil, rowData.geometryColNil)
      add(AllTypes.intArrayCol, rowData.intArrayCol)
      add(AllTypes.intArrayColNil, rowData.intArrayColNil)
      add(AllTypes.addressInt, rowData.addressInt)
      add(AllTypes.addressIntNil, rowData.addressIntNil)
      add(AllTypes.addressString, rowData.addressString)
      add(AllTypes.addressStringNil, rowData.addressStringNil)
      return where(AllTypes.id.eq(rowData.id))
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
    fun addressInt(value: com.dbobjekts.fixture.columns.AddressType): AllTypesInsertBuilder = put(AllTypes.addressInt, value)
    fun addressIntNil(value: com.dbobjekts.fixture.columns.AddressType): AllTypesInsertBuilder = put(AllTypes.addressIntNil, value)
    fun addressString(value: com.dbobjekts.fixture.columns.AddressType): AllTypesInsertBuilder = put(AllTypes.addressString, value)
    fun addressStringNil(value: com.dbobjekts.fixture.columns.AddressType): AllTypesInsertBuilder = put(AllTypes.addressStringNil, value)

    fun mandatoryColumns(characterCol: String, charactervaryingCol: String, characterlargeobjectCol: String, varcharIgnorecaseCol: String, enumCol: String, binaryCol: ByteArray, binaryvaryingCol: ByteArray, binarylargeobjectCol: java.sql.Blob, jsonCol: ByteArray, booleanCol: Boolean, tinyintCol: Byte, smallintCol: Int, integerCol: Int, bigintCol: Long, numericCol: java.math.BigDecimal, decfloatCol: java.math.BigDecimal, realCol: Float, doubleprecisionCol: Double, dateCol: java.time.LocalDate, timeCol: java.time.LocalTime, timewithtimezoneCol: java.time.OffsetDateTime, timestampCol: java.time.Instant, timestampwithtimezoneCol: java.time.OffsetDateTime, uuidCol: java.util.UUID, intervalCol: org.h2.api.Interval, intArrayCol: Array<Any>, addressInt: com.dbobjekts.fixture.columns.AddressType, addressIntNil: com.dbobjekts.fixture.columns.AddressType, addressString: com.dbobjekts.fixture.columns.AddressType, addressStringNil: com.dbobjekts.fixture.columns.AddressType) : AllTypesInsertBuilder {
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


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as AllTypesRow
      add(AllTypes.characterCol, rowData.characterCol)
      add(AllTypes.characterColNil, rowData.characterColNil)
      add(AllTypes.charactervaryingCol, rowData.charactervaryingCol)
      add(AllTypes.charactervaryingColNil, rowData.charactervaryingColNil)
      add(AllTypes.characterlargeobjectCol, rowData.characterlargeobjectCol)
      add(AllTypes.characterlargeobjectColNil, rowData.characterlargeobjectColNil)
      add(AllTypes.varcharIgnorecaseCol, rowData.varcharIgnorecaseCol)
      add(AllTypes.varcharIgnorecaseColNil, rowData.varcharIgnorecaseColNil)
      add(AllTypes.enumCol, rowData.enumCol)
      add(AllTypes.enumColNil, rowData.enumColNil)
      add(AllTypes.binaryCol, rowData.binaryCol)
      add(AllTypes.binaryColNil, rowData.binaryColNil)
      add(AllTypes.binaryvaryingCol, rowData.binaryvaryingCol)
      add(AllTypes.binaryvaryingColNil, rowData.binaryvaryingColNil)
      add(AllTypes.binarylargeobjectCol, rowData.binarylargeobjectCol)
      add(AllTypes.binarylargeobjectColNil, rowData.binarylargeobjectColNil)
      add(AllTypes.jsonCol, rowData.jsonCol)
      add(AllTypes.jsonColNil, rowData.jsonColNil)
      add(AllTypes.booleanCol, rowData.booleanCol)
      add(AllTypes.booleanColNil, rowData.booleanColNil)
      add(AllTypes.tinyintCol, rowData.tinyintCol)
      add(AllTypes.tinyintColNil, rowData.tinyintColNil)
      add(AllTypes.smallintCol, rowData.smallintCol)
      add(AllTypes.smallintColNil, rowData.smallintColNil)
      add(AllTypes.integerCol, rowData.integerCol)
      add(AllTypes.integerColNil, rowData.integerColNil)
      add(AllTypes.bigintCol, rowData.bigintCol)
      add(AllTypes.bigintColNil, rowData.bigintColNil)
      add(AllTypes.numericCol, rowData.numericCol)
      add(AllTypes.numericColNil, rowData.numericColNil)
      add(AllTypes.decfloatCol, rowData.decfloatCol)
      add(AllTypes.decfloatColNil, rowData.decfloatColNil)
      add(AllTypes.realCol, rowData.realCol)
      add(AllTypes.realColNil, rowData.realColNil)
      add(AllTypes.doubleprecisionCol, rowData.doubleprecisionCol)
      add(AllTypes.doubleprecisionColNil, rowData.doubleprecisionColNil)
      add(AllTypes.dateCol, rowData.dateCol)
      add(AllTypes.dateColNil, rowData.dateColNil)
      add(AllTypes.timeCol, rowData.timeCol)
      add(AllTypes.timeColNil, rowData.timeColNil)
      add(AllTypes.timewithtimezoneCol, rowData.timewithtimezoneCol)
      add(AllTypes.timewithtimezoneColNil, rowData.timewithtimezoneColNil)
      add(AllTypes.timestampCol, rowData.timestampCol)
      add(AllTypes.timestampColNil, rowData.timestampColNil)
      add(AllTypes.timestampwithtimezoneCol, rowData.timestampwithtimezoneCol)
      add(AllTypes.timestampwithtimezoneColNil, rowData.timestampwithtimezoneColNil)
      add(AllTypes.uuidCol, rowData.uuidCol)
      add(AllTypes.uuidColNil, rowData.uuidColNil)
      add(AllTypes.intervalCol, rowData.intervalCol)
      add(AllTypes.intervalColNil, rowData.intervalColNil)
      add(AllTypes.geometryColNil, rowData.geometryColNil)
      add(AllTypes.intArrayCol, rowData.intArrayCol)
      add(AllTypes.intArrayColNil, rowData.intArrayColNil)
      add(AllTypes.addressInt, rowData.addressInt)
      add(AllTypes.addressIntNil, rowData.addressIntNil)
      add(AllTypes.addressString, rowData.addressString)
      add(AllTypes.addressStringNil, rowData.addressStringNil)
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
  val addressInt: com.dbobjekts.fixture.columns.AddressType,
  val addressIntNil: com.dbobjekts.fixture.columns.AddressType,
  val addressString: com.dbobjekts.fixture.columns.AddressType,
  val addressStringNil: com.dbobjekts.fixture.columns.AddressType    
) : TableRowData<AllTypesUpdateBuilder, AllTypesInsertBuilder>(AllTypes.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(AllTypes.id, id))
}
        
