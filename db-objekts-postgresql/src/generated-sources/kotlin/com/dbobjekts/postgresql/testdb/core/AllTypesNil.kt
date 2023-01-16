package com.dbobjekts.postgresql.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.column.IsGeneratedPrimaryKey
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Auto-generated metadata object for db table core.all_types_nil.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: id
 *
 * Foreign keys: [] 
 */
object AllTypesNil:Table<AllTypesNilRow>("all_types_nil"), HasUpdateBuilder<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder> {
    /**
     * Represents db column core.all_types_nil.id
     */
    val id = com.dbobjekts.metadata.column.AutoKeyIntegerColumn(this, "id")
    /**
     * Represents db column core.all_types_nil.big_int_col
     */
    val bigIntCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "big_int_col")
    /**
     * Represents db column core.all_types_nil.int8_col
     */
    val int8Col = com.dbobjekts.metadata.column.NullableLongColumn(this, "int8_col")
    /**
     * Represents db column core.all_types_nil.bigserial_col
     */
    val bigserialCol = com.dbobjekts.metadata.column.LongColumn(this, "bigserial_col")
    /**
     * Represents db column core.all_types_nil.serial8_col
     */
    val serial8Col = com.dbobjekts.metadata.column.LongColumn(this, "serial8_col")
    /**
     * Represents db column core.all_types_nil.bit4_col
     */
    val bit4Col = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "bit4_col")
    /**
     * Represents db column core.all_types_nil.bit_varying_col
     */
    val bitVaryingCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "bit_varying_col")
    /**
     * Represents db column core.all_types_nil.varbit_col
     */
    val varbitCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "varbit_col")
    /**
     * Represents db column core.all_types_nil.boolean_col
     */
    val booleanCol = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "boolean_col")
    /**
     * Represents db column core.all_types_nil.bool_col
     */
    val boolCol = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "bool_col")
    /**
     * Represents db column core.all_types_nil.box_col
     */
    val boxCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "box_col")
    /**
     * Represents db column core.all_types_nil.bytea_col
     */
    val byteaCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "bytea_col")
    /**
     * Represents db column core.all_types_nil.character_col
     */
    val characterCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "character_col")
    /**
     * Represents db column core.all_types_nil.char_col
     */
    val charCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "char_col")
    /**
     * Represents db column core.all_types_nil.character_varying_col
     */
    val characterVaryingCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "character_varying_col")
    /**
     * Represents db column core.all_types_nil.varchar_col
     */
    val varcharCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "varchar_col")
    /**
     * Represents db column core.all_types_nil.cidr_col
     */
    val cidrCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "cidr_col")
    /**
     * Represents db column core.all_types_nil.circle_col
     */
    val circleCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "circle_col")
    /**
     * Represents db column core.all_types_nil.date_col
     */
    val dateCol = com.dbobjekts.metadata.column.NullableDateColumn(this, "date_col")
    /**
     * Represents db column core.all_types_nil.double_precision_col
     */
    val doublePrecisionCol = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "double_precision_col")
    /**
     * Represents db column core.all_types_nil.float8_col
     */
    val float8Col = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "float8_col")
    /**
     * Represents db column core.all_types_nil.inet_col
     */
    val inetCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "inet_col")
    /**
     * Represents db column core.all_types_nil.integer_col
     */
    val integerCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "integer_col")
    /**
     * Represents db column core.all_types_nil.int_col
     */
    val intCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "int_col")
    /**
     * Represents db column core.all_types_nil.int4_col
     */
    val int4Col = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "int4_col")
    /**
     * Represents db column core.all_types_nil.internval_year_col
     */
    val internvalYearCol = com.dbobjekts.vendors.postgresql.NullableIntervalColumn(this, "internval_year_col")
    /**
     * Represents db column core.all_types_nil.json_col
     */
    val jsonCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "json_col")
    /**
     * Represents db column core.all_types_nil.json_binary_col
     */
    val jsonBinaryCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "json_binary_col")
    /**
     * Represents db column core.all_types_nil.line_col
     */
    val lineCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "line_col")
    /**
     * Represents db column core.all_types_nil.lseg_col
     */
    val lsegCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "lseg_col")
    /**
     * Represents db column core.all_types_nil.macaddress_col
     */
    val macaddressCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "macaddress_col")
    /**
     * Represents db column core.all_types_nil.macaddress8_col
     */
    val macaddress8Col = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "macaddress8_col")
    /**
     * Represents db column core.all_types_nil.money_col
     */
    val moneyCol = com.dbobjekts.vendors.postgresql.NullableMoneyColumn(this, "money_col")
    /**
     * Represents db column core.all_types_nil.numeric_col
     */
    val numericCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "numeric_col")
    /**
     * Represents db column core.all_types_nil.decimal_col
     */
    val decimalCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "decimal_col")
    /**
     * Represents db column core.all_types_nil.path_col
     */
    val pathCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "path_col")
    /**
     * Represents db column core.all_types_nil.pg_lsn_col
     */
    val pgLsnCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "pg_lsn_col")
    /**
     * Represents db column core.all_types_nil.pg_snapshot_col
     */
    val pgSnapshotCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "pg_snapshot_col")
    /**
     * Represents db column core.all_types_nil.point_col
     */
    val pointCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "point_col")
    /**
     * Represents db column core.all_types_nil.polygon_col
     */
    val polygonCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "polygon_col")
    /**
     * Represents db column core.all_types_nil.real_col
     */
    val realCol = com.dbobjekts.metadata.column.NullableFloatColumn(this, "real_col")
    /**
     * Represents db column core.all_types_nil.float4_col
     */
    val float4Col = com.dbobjekts.metadata.column.NullableFloatColumn(this, "float4_col")
    /**
     * Represents db column core.all_types_nil.small_int_col
     */
    val smallIntCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "small_int_col")
    /**
     * Represents db column core.all_types_nil.int2_col
     */
    val int2Col = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "int2_col")
    /**
     * Represents db column core.all_types_nil.small_serial_col
     */
    val smallSerialCol = com.dbobjekts.metadata.column.IntegerColumn(this, "small_serial_col")
    /**
     * Represents db column core.all_types_nil.serial2_col
     */
    val serial2Col = com.dbobjekts.metadata.column.IntegerColumn(this, "serial2_col")
    /**
     * Represents db column core.all_types_nil.serial_col
     */
    val serialCol = com.dbobjekts.metadata.column.IntegerColumn(this, "serial_col")
    /**
     * Represents db column core.all_types_nil.serial4_col
     */
    val serial4Col = com.dbobjekts.metadata.column.IntegerColumn(this, "serial4_col")
    /**
     * Represents db column core.all_types_nil.text_col
     */
    val textCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "text_col")
    /**
     * Represents db column core.all_types_nil.time_without_tz_col
     */
    val timeWithoutTzCol = com.dbobjekts.metadata.column.NullableTimeColumn(this, "time_without_tz_col")
    /**
     * Represents db column core.all_types_nil.time_with_tz_col
     */
    val timeWithTzCol = com.dbobjekts.metadata.column.NullableTimeColumn(this, "time_with_tz_col")
    /**
     * Represents db column core.all_types_nil.time_tz_col
     */
    val timeTzCol = com.dbobjekts.metadata.column.NullableTimeColumn(this, "time_tz_col")
    /**
     * Represents db column core.all_types_nil.timestamp_wihtout_tz_col
     */
    val timestampWihtoutTzCol = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "timestamp_wihtout_tz_col")
    /**
     * Represents db column core.all_types_nil.timestamp_with_tz_col
     */
    val timestampWithTzCol = com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn(this, "timestamp_with_tz_col")
    /**
     * Represents db column core.all_types_nil.timestamp_tz_col
     */
    val timestampTzCol = com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn(this, "timestamp_tz_col")
    /**
     * Represents db column core.all_types_nil.tsquery_col
     */
    val tsqueryCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "tsquery_col")
    /**
     * Represents db column core.all_types_nil.tsvector_col
     */
    val tsvectorCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "tsvector_col")
    /**
     * Represents db column core.all_types_nil.txid_snapshot_col
     */
    val txidSnapshotCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "txid_snapshot_col")
    /**
     * Represents db column core.all_types_nil.uuid_col
     */
    val uuidCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "uuid_col")
    /**
     * Represents db column core.all_types_nil.xml_col
     */
    val xmlCol = com.dbobjekts.metadata.column.NullableXMLColumn(this, "xml_col")
    /**
     * Represents db column core.all_types_nil.mood_col
     */
    val moodCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "mood_col")
    override val columns: List<AnyColumn> = listOf(id,bigIntCol,int8Col,bigserialCol,serial8Col,bit4Col,bitVaryingCol,varbitCol,booleanCol,boolCol,boxCol,byteaCol,characterCol,charCol,characterVaryingCol,varcharCol,cidrCol,circleCol,dateCol,doublePrecisionCol,float8Col,inetCol,integerCol,intCol,int4Col,internvalYearCol,jsonCol,jsonBinaryCol,lineCol,lsegCol,macaddressCol,macaddress8Col,moneyCol,numericCol,decimalCol,pathCol,pgLsnCol,pgSnapshotCol,pointCol,polygonCol,realCol,float4Col,smallIntCol,int2Col,smallSerialCol,serial2Col,serialCol,serial4Col,textCol,timeWithoutTzCol,timeWithTzCol,timeTzCol,timestampWihtoutTzCol,timestampWithTzCol,timestampTzCol,tsqueryCol,tsvectorCol,txidSnapshotCol,uuidCol,xmlCol,moodCol)
    override fun toValue(values: List<Any?>) = AllTypesNilRow(values[0] as Int,values[1] as Long?,values[2] as Long?,values[3] as Long,values[4] as Long,values[5] as Boolean?,values[6] as ByteArray?,values[7] as ByteArray?,values[8] as Boolean?,values[9] as Boolean?,values[10] as String?,values[11] as ByteArray?,values[12] as String?,values[13] as String?,values[14] as String?,values[15] as String?,values[16] as String?,values[17] as String?,values[18] as java.time.LocalDate?,values[19] as Double?,values[20] as Double?,values[21] as String?,values[22] as Int?,values[23] as Int?,values[24] as Int?,values[25] as org.postgresql.util.PGInterval?,values[26] as String?,values[27] as String?,values[28] as String?,values[29] as String?,values[30] as String?,values[31] as String?,values[32] as org.postgresql.util.PGmoney?,values[33] as java.math.BigDecimal?,values[34] as java.math.BigDecimal?,values[35] as String?,values[36] as Long?,values[37] as Long?,values[38] as String?,values[39] as String?,values[40] as Float?,values[41] as Float?,values[42] as Int?,values[43] as Int?,values[44] as Int,values[45] as Int,values[46] as Int,values[47] as Int,values[48] as String?,values[49] as java.time.LocalTime?,values[50] as java.time.LocalTime?,values[51] as java.time.LocalTime?,values[52] as java.time.Instant?,values[53] as java.time.OffsetDateTime?,values[54] as java.time.OffsetDateTime?,values[55] as String?,values[56] as String?,values[57] as Long?,values[58] as String?,values[59] as java.sql.SQLXML?,values[60] as String?)
    override fun metadata(): WriteQueryAccessors<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder> = WriteQueryAccessors(AllTypesNilUpdateBuilder(), AllTypesNilInsertBuilder())
}

class AllTypesNilUpdateBuilder() : UpdateBuilderBase(AllTypesNil) {
    fun bigIntCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.bigIntCol, value)
    fun int8Col(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.int8Col, value)
    fun bigserialCol(value: Long): AllTypesNilUpdateBuilder = put(AllTypesNil.bigserialCol, value)
    fun serial8Col(value: Long): AllTypesNilUpdateBuilder = put(AllTypesNil.serial8Col, value)
    fun bit4Col(value: Boolean?): AllTypesNilUpdateBuilder = put(AllTypesNil.bit4Col, value)
    fun bitVaryingCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.bitVaryingCol, value)
    fun varbitCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.varbitCol, value)
    fun booleanCol(value: Boolean?): AllTypesNilUpdateBuilder = put(AllTypesNil.booleanCol, value)
    fun boolCol(value: Boolean?): AllTypesNilUpdateBuilder = put(AllTypesNil.boolCol, value)
    fun boxCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.boxCol, value)
    fun byteaCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.byteaCol, value)
    fun characterCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.characterCol, value)
    fun charCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.charCol, value)
    fun characterVaryingCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.characterVaryingCol, value)
    fun varcharCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.varcharCol, value)
    fun cidrCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.cidrCol, value)
    fun circleCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.circleCol, value)
    fun dateCol(value: java.time.LocalDate?): AllTypesNilUpdateBuilder = put(AllTypesNil.dateCol, value)
    fun doublePrecisionCol(value: Double?): AllTypesNilUpdateBuilder = put(AllTypesNil.doublePrecisionCol, value)
    fun float8Col(value: Double?): AllTypesNilUpdateBuilder = put(AllTypesNil.float8Col, value)
    fun inetCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.inetCol, value)
    fun integerCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.integerCol, value)
    fun intCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.intCol, value)
    fun int4Col(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.int4Col, value)
    fun internvalYearCol(value: org.postgresql.util.PGInterval?): AllTypesNilUpdateBuilder = put(AllTypesNil.internvalYearCol, value)
    fun jsonCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.jsonCol, value)
    fun jsonBinaryCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.jsonBinaryCol, value)
    fun lineCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.lineCol, value)
    fun lsegCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.lsegCol, value)
    fun macaddressCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.macaddressCol, value)
    fun macaddress8Col(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.macaddress8Col, value)
    fun moneyCol(value: org.postgresql.util.PGmoney?): AllTypesNilUpdateBuilder = put(AllTypesNil.moneyCol, value)
    fun numericCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.numericCol, value)
    fun decimalCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.decimalCol, value)
    fun pathCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.pathCol, value)
    fun pgLsnCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.pgLsnCol, value)
    fun pgSnapshotCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.pgSnapshotCol, value)
    fun pointCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.pointCol, value)
    fun polygonCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.polygonCol, value)
    fun realCol(value: Float?): AllTypesNilUpdateBuilder = put(AllTypesNil.realCol, value)
    fun float4Col(value: Float?): AllTypesNilUpdateBuilder = put(AllTypesNil.float4Col, value)
    fun smallIntCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.smallIntCol, value)
    fun int2Col(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.int2Col, value)
    fun smallSerialCol(value: Int): AllTypesNilUpdateBuilder = put(AllTypesNil.smallSerialCol, value)
    fun serial2Col(value: Int): AllTypesNilUpdateBuilder = put(AllTypesNil.serial2Col, value)
    fun serialCol(value: Int): AllTypesNilUpdateBuilder = put(AllTypesNil.serialCol, value)
    fun serial4Col(value: Int): AllTypesNilUpdateBuilder = put(AllTypesNil.serial4Col, value)
    fun textCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.textCol, value)
    fun timeWithoutTzCol(value: java.time.LocalTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timeWithoutTzCol, value)
    fun timeWithTzCol(value: java.time.LocalTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timeWithTzCol, value)
    fun timeTzCol(value: java.time.LocalTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timeTzCol, value)
    fun timestampWihtoutTzCol(value: java.time.Instant?): AllTypesNilUpdateBuilder = put(AllTypesNil.timestampWihtoutTzCol, value)
    fun timestampWithTzCol(value: java.time.OffsetDateTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timestampWithTzCol, value)
    fun timestampTzCol(value: java.time.OffsetDateTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timestampTzCol, value)
    fun tsqueryCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.tsqueryCol, value)
    fun tsvectorCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.tsvectorCol, value)
    fun txidSnapshotCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.txidSnapshotCol, value)
    fun uuidCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.uuidCol, value)
    fun xmlCol(value: java.sql.SQLXML?): AllTypesNilUpdateBuilder = put(AllTypesNil.xmlCol, value)
    fun moodCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.moodCol, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AllTypesNilRow
      add(AllTypesNil.id, rowData.id)
      add(AllTypesNil.bigIntCol, rowData.bigIntCol)
      add(AllTypesNil.int8Col, rowData.int8Col)
      add(AllTypesNil.bigserialCol, rowData.bigserialCol)
      add(AllTypesNil.serial8Col, rowData.serial8Col)
      add(AllTypesNil.bit4Col, rowData.bit4Col)
      add(AllTypesNil.bitVaryingCol, rowData.bitVaryingCol)
      add(AllTypesNil.varbitCol, rowData.varbitCol)
      add(AllTypesNil.booleanCol, rowData.booleanCol)
      add(AllTypesNil.boolCol, rowData.boolCol)
      add(AllTypesNil.boxCol, rowData.boxCol)
      add(AllTypesNil.byteaCol, rowData.byteaCol)
      add(AllTypesNil.characterCol, rowData.characterCol)
      add(AllTypesNil.charCol, rowData.charCol)
      add(AllTypesNil.characterVaryingCol, rowData.characterVaryingCol)
      add(AllTypesNil.varcharCol, rowData.varcharCol)
      add(AllTypesNil.cidrCol, rowData.cidrCol)
      add(AllTypesNil.circleCol, rowData.circleCol)
      add(AllTypesNil.dateCol, rowData.dateCol)
      add(AllTypesNil.doublePrecisionCol, rowData.doublePrecisionCol)
      add(AllTypesNil.float8Col, rowData.float8Col)
      add(AllTypesNil.inetCol, rowData.inetCol)
      add(AllTypesNil.integerCol, rowData.integerCol)
      add(AllTypesNil.intCol, rowData.intCol)
      add(AllTypesNil.int4Col, rowData.int4Col)
      add(AllTypesNil.internvalYearCol, rowData.internvalYearCol)
      add(AllTypesNil.jsonCol, rowData.jsonCol)
      add(AllTypesNil.jsonBinaryCol, rowData.jsonBinaryCol)
      add(AllTypesNil.lineCol, rowData.lineCol)
      add(AllTypesNil.lsegCol, rowData.lsegCol)
      add(AllTypesNil.macaddressCol, rowData.macaddressCol)
      add(AllTypesNil.macaddress8Col, rowData.macaddress8Col)
      add(AllTypesNil.moneyCol, rowData.moneyCol)
      add(AllTypesNil.numericCol, rowData.numericCol)
      add(AllTypesNil.decimalCol, rowData.decimalCol)
      add(AllTypesNil.pathCol, rowData.pathCol)
      add(AllTypesNil.pgLsnCol, rowData.pgLsnCol)
      add(AllTypesNil.pgSnapshotCol, rowData.pgSnapshotCol)
      add(AllTypesNil.pointCol, rowData.pointCol)
      add(AllTypesNil.polygonCol, rowData.polygonCol)
      add(AllTypesNil.realCol, rowData.realCol)
      add(AllTypesNil.float4Col, rowData.float4Col)
      add(AllTypesNil.smallIntCol, rowData.smallIntCol)
      add(AllTypesNil.int2Col, rowData.int2Col)
      add(AllTypesNil.smallSerialCol, rowData.smallSerialCol)
      add(AllTypesNil.serial2Col, rowData.serial2Col)
      add(AllTypesNil.serialCol, rowData.serialCol)
      add(AllTypesNil.serial4Col, rowData.serial4Col)
      add(AllTypesNil.textCol, rowData.textCol)
      add(AllTypesNil.timeWithoutTzCol, rowData.timeWithoutTzCol)
      add(AllTypesNil.timeWithTzCol, rowData.timeWithTzCol)
      add(AllTypesNil.timeTzCol, rowData.timeTzCol)
      add(AllTypesNil.timestampWihtoutTzCol, rowData.timestampWihtoutTzCol)
      add(AllTypesNil.timestampWithTzCol, rowData.timestampWithTzCol)
      add(AllTypesNil.timestampTzCol, rowData.timestampTzCol)
      add(AllTypesNil.tsqueryCol, rowData.tsqueryCol)
      add(AllTypesNil.tsvectorCol, rowData.tsvectorCol)
      add(AllTypesNil.txidSnapshotCol, rowData.txidSnapshotCol)
      add(AllTypesNil.uuidCol, rowData.uuidCol)
      add(AllTypesNil.xmlCol, rowData.xmlCol)
      add(AllTypesNil.moodCol, rowData.moodCol)
      return where(AllTypesNil.id.eq(rowData.id))
    }    
        
}

class AllTypesNilInsertBuilder():InsertBuilderBase(){
    fun bigIntCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.bigIntCol, value)
    fun int8Col(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.int8Col, value)
    fun bigserialCol(value: Long): AllTypesNilInsertBuilder = put(AllTypesNil.bigserialCol, value)
    fun serial8Col(value: Long): AllTypesNilInsertBuilder = put(AllTypesNil.serial8Col, value)
    fun bit4Col(value: Boolean?): AllTypesNilInsertBuilder = put(AllTypesNil.bit4Col, value)
    fun bitVaryingCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.bitVaryingCol, value)
    fun varbitCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.varbitCol, value)
    fun booleanCol(value: Boolean?): AllTypesNilInsertBuilder = put(AllTypesNil.booleanCol, value)
    fun boolCol(value: Boolean?): AllTypesNilInsertBuilder = put(AllTypesNil.boolCol, value)
    fun boxCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.boxCol, value)
    fun byteaCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.byteaCol, value)
    fun characterCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.characterCol, value)
    fun charCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.charCol, value)
    fun characterVaryingCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.characterVaryingCol, value)
    fun varcharCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.varcharCol, value)
    fun cidrCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.cidrCol, value)
    fun circleCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.circleCol, value)
    fun dateCol(value: java.time.LocalDate?): AllTypesNilInsertBuilder = put(AllTypesNil.dateCol, value)
    fun doublePrecisionCol(value: Double?): AllTypesNilInsertBuilder = put(AllTypesNil.doublePrecisionCol, value)
    fun float8Col(value: Double?): AllTypesNilInsertBuilder = put(AllTypesNil.float8Col, value)
    fun inetCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.inetCol, value)
    fun integerCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.integerCol, value)
    fun intCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.intCol, value)
    fun int4Col(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.int4Col, value)
    fun internvalYearCol(value: org.postgresql.util.PGInterval?): AllTypesNilInsertBuilder = put(AllTypesNil.internvalYearCol, value)
    fun jsonCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.jsonCol, value)
    fun jsonBinaryCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.jsonBinaryCol, value)
    fun lineCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.lineCol, value)
    fun lsegCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.lsegCol, value)
    fun macaddressCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.macaddressCol, value)
    fun macaddress8Col(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.macaddress8Col, value)
    fun moneyCol(value: org.postgresql.util.PGmoney?): AllTypesNilInsertBuilder = put(AllTypesNil.moneyCol, value)
    fun numericCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.numericCol, value)
    fun decimalCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.decimalCol, value)
    fun pathCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.pathCol, value)
    fun pgLsnCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.pgLsnCol, value)
    fun pgSnapshotCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.pgSnapshotCol, value)
    fun pointCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.pointCol, value)
    fun polygonCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.polygonCol, value)
    fun realCol(value: Float?): AllTypesNilInsertBuilder = put(AllTypesNil.realCol, value)
    fun float4Col(value: Float?): AllTypesNilInsertBuilder = put(AllTypesNil.float4Col, value)
    fun smallIntCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.smallIntCol, value)
    fun int2Col(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.int2Col, value)
    fun smallSerialCol(value: Int): AllTypesNilInsertBuilder = put(AllTypesNil.smallSerialCol, value)
    fun serial2Col(value: Int): AllTypesNilInsertBuilder = put(AllTypesNil.serial2Col, value)
    fun serialCol(value: Int): AllTypesNilInsertBuilder = put(AllTypesNil.serialCol, value)
    fun serial4Col(value: Int): AllTypesNilInsertBuilder = put(AllTypesNil.serial4Col, value)
    fun textCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.textCol, value)
    fun timeWithoutTzCol(value: java.time.LocalTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timeWithoutTzCol, value)
    fun timeWithTzCol(value: java.time.LocalTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timeWithTzCol, value)
    fun timeTzCol(value: java.time.LocalTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timeTzCol, value)
    fun timestampWihtoutTzCol(value: java.time.Instant?): AllTypesNilInsertBuilder = put(AllTypesNil.timestampWihtoutTzCol, value)
    fun timestampWithTzCol(value: java.time.OffsetDateTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timestampWithTzCol, value)
    fun timestampTzCol(value: java.time.OffsetDateTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timestampTzCol, value)
    fun tsqueryCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.tsqueryCol, value)
    fun tsvectorCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.tsvectorCol, value)
    fun txidSnapshotCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.txidSnapshotCol, value)
    fun uuidCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.uuidCol, value)
    fun xmlCol(value: java.sql.SQLXML?): AllTypesNilInsertBuilder = put(AllTypesNil.xmlCol, value)
    fun moodCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.moodCol, value)

    fun mandatoryColumns(bigserialCol: Long, serial8Col: Long, smallSerialCol: Int, serial2Col: Int, serialCol: Int, serial4Col: Int) : AllTypesNilInsertBuilder {
      mandatory(AllTypesNil.bigserialCol, bigserialCol)
      mandatory(AllTypesNil.serial8Col, serial8Col)
      mandatory(AllTypesNil.smallSerialCol, smallSerialCol)
      mandatory(AllTypesNil.serial2Col, serial2Col)
      mandatory(AllTypesNil.serialCol, serialCol)
      mandatory(AllTypesNil.serial4Col, serial4Col)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as AllTypesNilRow
      add(AllTypesNil.bigIntCol, rowData.bigIntCol)
      add(AllTypesNil.int8Col, rowData.int8Col)
      add(AllTypesNil.bigserialCol, rowData.bigserialCol)
      add(AllTypesNil.serial8Col, rowData.serial8Col)
      add(AllTypesNil.bit4Col, rowData.bit4Col)
      add(AllTypesNil.bitVaryingCol, rowData.bitVaryingCol)
      add(AllTypesNil.varbitCol, rowData.varbitCol)
      add(AllTypesNil.booleanCol, rowData.booleanCol)
      add(AllTypesNil.boolCol, rowData.boolCol)
      add(AllTypesNil.boxCol, rowData.boxCol)
      add(AllTypesNil.byteaCol, rowData.byteaCol)
      add(AllTypesNil.characterCol, rowData.characterCol)
      add(AllTypesNil.charCol, rowData.charCol)
      add(AllTypesNil.characterVaryingCol, rowData.characterVaryingCol)
      add(AllTypesNil.varcharCol, rowData.varcharCol)
      add(AllTypesNil.cidrCol, rowData.cidrCol)
      add(AllTypesNil.circleCol, rowData.circleCol)
      add(AllTypesNil.dateCol, rowData.dateCol)
      add(AllTypesNil.doublePrecisionCol, rowData.doublePrecisionCol)
      add(AllTypesNil.float8Col, rowData.float8Col)
      add(AllTypesNil.inetCol, rowData.inetCol)
      add(AllTypesNil.integerCol, rowData.integerCol)
      add(AllTypesNil.intCol, rowData.intCol)
      add(AllTypesNil.int4Col, rowData.int4Col)
      add(AllTypesNil.internvalYearCol, rowData.internvalYearCol)
      add(AllTypesNil.jsonCol, rowData.jsonCol)
      add(AllTypesNil.jsonBinaryCol, rowData.jsonBinaryCol)
      add(AllTypesNil.lineCol, rowData.lineCol)
      add(AllTypesNil.lsegCol, rowData.lsegCol)
      add(AllTypesNil.macaddressCol, rowData.macaddressCol)
      add(AllTypesNil.macaddress8Col, rowData.macaddress8Col)
      add(AllTypesNil.moneyCol, rowData.moneyCol)
      add(AllTypesNil.numericCol, rowData.numericCol)
      add(AllTypesNil.decimalCol, rowData.decimalCol)
      add(AllTypesNil.pathCol, rowData.pathCol)
      add(AllTypesNil.pgLsnCol, rowData.pgLsnCol)
      add(AllTypesNil.pgSnapshotCol, rowData.pgSnapshotCol)
      add(AllTypesNil.pointCol, rowData.pointCol)
      add(AllTypesNil.polygonCol, rowData.polygonCol)
      add(AllTypesNil.realCol, rowData.realCol)
      add(AllTypesNil.float4Col, rowData.float4Col)
      add(AllTypesNil.smallIntCol, rowData.smallIntCol)
      add(AllTypesNil.int2Col, rowData.int2Col)
      add(AllTypesNil.smallSerialCol, rowData.smallSerialCol)
      add(AllTypesNil.serial2Col, rowData.serial2Col)
      add(AllTypesNil.serialCol, rowData.serialCol)
      add(AllTypesNil.serial4Col, rowData.serial4Col)
      add(AllTypesNil.textCol, rowData.textCol)
      add(AllTypesNil.timeWithoutTzCol, rowData.timeWithoutTzCol)
      add(AllTypesNil.timeWithTzCol, rowData.timeWithTzCol)
      add(AllTypesNil.timeTzCol, rowData.timeTzCol)
      add(AllTypesNil.timestampWihtoutTzCol, rowData.timestampWihtoutTzCol)
      add(AllTypesNil.timestampWithTzCol, rowData.timestampWithTzCol)
      add(AllTypesNil.timestampTzCol, rowData.timestampTzCol)
      add(AllTypesNil.tsqueryCol, rowData.tsqueryCol)
      add(AllTypesNil.tsvectorCol, rowData.tsvectorCol)
      add(AllTypesNil.txidSnapshotCol, rowData.txidSnapshotCol)
      add(AllTypesNil.uuidCol, rowData.uuidCol)
      add(AllTypesNil.xmlCol, rowData.xmlCol)
      add(AllTypesNil.moodCol, rowData.moodCol)
      return execute()
    }    
        
}


data class AllTypesNilRow(
val id: Int = 0,
  val bigIntCol: Long?,
  val int8Col: Long?,
  val bigserialCol: Long,
  val serial8Col: Long,
  val bit4Col: Boolean?,
  val bitVaryingCol: ByteArray?,
  val varbitCol: ByteArray?,
  val booleanCol: Boolean?,
  val boolCol: Boolean?,
  val boxCol: String?,
  val byteaCol: ByteArray?,
  val characterCol: String?,
  val charCol: String?,
  val characterVaryingCol: String?,
  val varcharCol: String?,
  val cidrCol: String?,
  val circleCol: String?,
  val dateCol: java.time.LocalDate?,
  val doublePrecisionCol: Double?,
  val float8Col: Double?,
  val inetCol: String?,
  val integerCol: Int?,
  val intCol: Int?,
  val int4Col: Int?,
  val internvalYearCol: org.postgresql.util.PGInterval?,
  val jsonCol: String?,
  val jsonBinaryCol: String?,
  val lineCol: String?,
  val lsegCol: String?,
  val macaddressCol: String?,
  val macaddress8Col: String?,
  val moneyCol: org.postgresql.util.PGmoney?,
  val numericCol: java.math.BigDecimal?,
  val decimalCol: java.math.BigDecimal?,
  val pathCol: String?,
  val pgLsnCol: Long?,
  val pgSnapshotCol: Long?,
  val pointCol: String?,
  val polygonCol: String?,
  val realCol: Float?,
  val float4Col: Float?,
  val smallIntCol: Int?,
  val int2Col: Int?,
  val smallSerialCol: Int,
  val serial2Col: Int,
  val serialCol: Int,
  val serial4Col: Int,
  val textCol: String?,
  val timeWithoutTzCol: java.time.LocalTime?,
  val timeWithTzCol: java.time.LocalTime?,
  val timeTzCol: java.time.LocalTime?,
  val timestampWihtoutTzCol: java.time.Instant?,
  val timestampWithTzCol: java.time.OffsetDateTime?,
  val timestampTzCol: java.time.OffsetDateTime?,
  val tsqueryCol: String?,
  val tsvectorCol: String?,
  val txidSnapshotCol: Long?,
  val uuidCol: String?,
  val xmlCol: java.sql.SQLXML?,
  val moodCol: String?    
) : TableRowData<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder>(AllTypesNil.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(AllTypesNil.id, id))
}
        
