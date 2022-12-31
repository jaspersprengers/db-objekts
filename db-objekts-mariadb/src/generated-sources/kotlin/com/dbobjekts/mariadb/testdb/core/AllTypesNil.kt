package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.Entity
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object AllTypesNil:Table<AllTypesNilRow>("ALL_TYPES_NIL"), HasUpdateBuilder<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val decimalCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "DECIMAL_COL")
    val decCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "DEC_COL")
    val numericCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "NUMERIC_COL")
    val fixedCol = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "FIXED_COL")
    val int1Col = com.dbobjekts.metadata.column.NumberAsBooleanColumn(this, "INT1_COL")
    val tinyintCol = com.dbobjekts.metadata.column.NumberAsBooleanColumn(this, "TINYINT_COL")
    val smallintCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "SMALLINT_COL")
    val int2Col = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "INT2_COL")
    val mediumintCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "MEDIUMINT_COL")
    val int3Col = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "INT3_COL")
    val intCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "INT_COL")
    val int4Col = com.dbobjekts.metadata.column.NullableLongColumn(this, "INT4_COL")
    val bigintCol = com.dbobjekts.metadata.column.NullableLongColumn(this, "BIGINT_COL")
    val int8Col = com.dbobjekts.metadata.column.NullableLongColumn(this, "INT8_COL")
    val floatCol = com.dbobjekts.metadata.column.NullableFloatColumn(this, "FLOAT_COL")
    val doubleCol = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "DOUBLE_COL")
    val doublePrecisionCol = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "DOUBLE_PRECISION_COL")
    val bitCol = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "BIT_COL")
    val binaryCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "BINARY_COL")
    val blobCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "BLOB_COL")
    val charCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CHAR_COL")
    val charByteCol = com.dbobjekts.metadata.column.NullableByteArrayColumn(this, "CHAR_BYTE_COL")
    val enumCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "ENUM_COL")
    val jsonCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "JSON_COL")
    val textCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "TEXT_COL")
    val varcharCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "VARCHAR_COL")
    val setCol = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "SET_COL")
    val dateCol = com.dbobjekts.metadata.column.NullableDateColumn(this, "DATE_COL")
    val timeCol = com.dbobjekts.metadata.column.NullableTimeColumn(this, "TIME_COL")
    val datetimeCol = com.dbobjekts.metadata.column.NullableDateTimeColumn(this, "DATETIME_COL")
    val timestampCol = com.dbobjekts.metadata.column.NullableDateTimeColumn(this, "TIMESTAMP_COL")
    val yearCol = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "YEAR_COL")
    override val columns: List<AnyColumn> = listOf(id,decimalCol,decCol,numericCol,fixedCol,int1Col,tinyintCol,smallintCol,int2Col,mediumintCol,int3Col,intCol,int4Col,bigintCol,int8Col,floatCol,doubleCol,doublePrecisionCol,bitCol,binaryCol,blobCol,charCol,charByteCol,enumCol,jsonCol,textCol,varcharCol,setCol,dateCol,timeCol,datetimeCol,timestampCol,yearCol)
    override fun toValue(values: List<Any?>) = AllTypesNilRow(values[0] as Long,values[1] as java.math.BigDecimal?,values[2] as java.math.BigDecimal?,values[3] as java.math.BigDecimal?,values[4] as java.math.BigDecimal?,values[5] as Boolean,values[6] as Boolean,values[7] as Int?,values[8] as Int?,values[9] as Int?,values[10] as Int?,values[11] as Long?,values[12] as Long?,values[13] as Long?,values[14] as Long?,values[15] as Float?,values[16] as Double?,values[17] as Double?,values[18] as Boolean?,values[19] as ByteArray?,values[20] as ByteArray?,values[21] as String?,values[22] as ByteArray?,values[23] as String?,values[24] as String?,values[25] as String?,values[26] as String?,values[27] as String?,values[28] as java.time.LocalDate?,values[29] as java.time.LocalTime?,values[30] as java.time.LocalDateTime?,values[31] as java.time.LocalDateTime?,values[32] as Int?)
    override fun metadata(): WriteQueryAccessors<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder> = WriteQueryAccessors(AllTypesNilUpdateBuilder(), AllTypesNilInsertBuilder())
}

class AllTypesNilUpdateBuilder() : UpdateBuilderBase(AllTypesNil) {
    fun decimalCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.decimalCol, value)
    fun decCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.decCol, value)
    fun numericCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.numericCol, value)
    fun fixedCol(value: java.math.BigDecimal?): AllTypesNilUpdateBuilder = put(AllTypesNil.fixedCol, value)
    fun int1Col(value: Boolean): AllTypesNilUpdateBuilder = put(AllTypesNil.int1Col, value)
    fun tinyintCol(value: Boolean): AllTypesNilUpdateBuilder = put(AllTypesNil.tinyintCol, value)
    fun smallintCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.smallintCol, value)
    fun int2Col(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.int2Col, value)
    fun mediumintCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.mediumintCol, value)
    fun int3Col(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.int3Col, value)
    fun intCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.intCol, value)
    fun int4Col(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.int4Col, value)
    fun bigintCol(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.bigintCol, value)
    fun int8Col(value: Long?): AllTypesNilUpdateBuilder = put(AllTypesNil.int8Col, value)
    fun floatCol(value: Float?): AllTypesNilUpdateBuilder = put(AllTypesNil.floatCol, value)
    fun doubleCol(value: Double?): AllTypesNilUpdateBuilder = put(AllTypesNil.doubleCol, value)
    fun doublePrecisionCol(value: Double?): AllTypesNilUpdateBuilder = put(AllTypesNil.doublePrecisionCol, value)
    fun bitCol(value: Boolean?): AllTypesNilUpdateBuilder = put(AllTypesNil.bitCol, value)
    fun binaryCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.binaryCol, value)
    fun blobCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.blobCol, value)
    fun charCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.charCol, value)
    fun charByteCol(value: ByteArray?): AllTypesNilUpdateBuilder = put(AllTypesNil.charByteCol, value)
    fun enumCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.enumCol, value)
    fun jsonCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.jsonCol, value)
    fun textCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.textCol, value)
    fun varcharCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.varcharCol, value)
    fun setCol(value: String?): AllTypesNilUpdateBuilder = put(AllTypesNil.setCol, value)
    fun dateCol(value: java.time.LocalDate?): AllTypesNilUpdateBuilder = put(AllTypesNil.dateCol, value)
    fun timeCol(value: java.time.LocalTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timeCol, value)
    fun datetimeCol(value: java.time.LocalDateTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.datetimeCol, value)
    fun timestampCol(value: java.time.LocalDateTime?): AllTypesNilUpdateBuilder = put(AllTypesNil.timestampCol, value)
    fun yearCol(value: Int?): AllTypesNilUpdateBuilder = put(AllTypesNil.yearCol, value)

    override fun updateRow(entity: Entity<*, *>): Long {
      entity as AllTypesNilRow
      add(AllTypesNil.id, entity.id)
      add(AllTypesNil.decimalCol, entity.decimalCol)
      add(AllTypesNil.decCol, entity.decCol)
      add(AllTypesNil.numericCol, entity.numericCol)
      add(AllTypesNil.fixedCol, entity.fixedCol)
      add(AllTypesNil.int1Col, entity.int1Col)
      add(AllTypesNil.tinyintCol, entity.tinyintCol)
      add(AllTypesNil.smallintCol, entity.smallintCol)
      add(AllTypesNil.int2Col, entity.int2Col)
      add(AllTypesNil.mediumintCol, entity.mediumintCol)
      add(AllTypesNil.int3Col, entity.int3Col)
      add(AllTypesNil.intCol, entity.intCol)
      add(AllTypesNil.int4Col, entity.int4Col)
      add(AllTypesNil.bigintCol, entity.bigintCol)
      add(AllTypesNil.int8Col, entity.int8Col)
      add(AllTypesNil.floatCol, entity.floatCol)
      add(AllTypesNil.doubleCol, entity.doubleCol)
      add(AllTypesNil.doublePrecisionCol, entity.doublePrecisionCol)
      add(AllTypesNil.bitCol, entity.bitCol)
      add(AllTypesNil.binaryCol, entity.binaryCol)
      add(AllTypesNil.blobCol, entity.blobCol)
      add(AllTypesNil.charCol, entity.charCol)
      add(AllTypesNil.charByteCol, entity.charByteCol)
      add(AllTypesNil.enumCol, entity.enumCol)
      add(AllTypesNil.jsonCol, entity.jsonCol)
      add(AllTypesNil.textCol, entity.textCol)
      add(AllTypesNil.varcharCol, entity.varcharCol)
      add(AllTypesNil.setCol, entity.setCol)
      add(AllTypesNil.dateCol, entity.dateCol)
      add(AllTypesNil.timeCol, entity.timeCol)
      add(AllTypesNil.datetimeCol, entity.datetimeCol)
      add(AllTypesNil.timestampCol, entity.timestampCol)
      add(AllTypesNil.yearCol, entity.yearCol)
      return where (AllTypesNil.id.eq(entity.id))
    }    
        
}

class AllTypesNilInsertBuilder():InsertBuilderBase(){
    fun decimalCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.decimalCol, value)
    fun decCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.decCol, value)
    fun numericCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.numericCol, value)
    fun fixedCol(value: java.math.BigDecimal?): AllTypesNilInsertBuilder = put(AllTypesNil.fixedCol, value)
    fun int1Col(value: Boolean): AllTypesNilInsertBuilder = put(AllTypesNil.int1Col, value)
    fun tinyintCol(value: Boolean): AllTypesNilInsertBuilder = put(AllTypesNil.tinyintCol, value)
    fun smallintCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.smallintCol, value)
    fun int2Col(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.int2Col, value)
    fun mediumintCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.mediumintCol, value)
    fun int3Col(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.int3Col, value)
    fun intCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.intCol, value)
    fun int4Col(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.int4Col, value)
    fun bigintCol(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.bigintCol, value)
    fun int8Col(value: Long?): AllTypesNilInsertBuilder = put(AllTypesNil.int8Col, value)
    fun floatCol(value: Float?): AllTypesNilInsertBuilder = put(AllTypesNil.floatCol, value)
    fun doubleCol(value: Double?): AllTypesNilInsertBuilder = put(AllTypesNil.doubleCol, value)
    fun doublePrecisionCol(value: Double?): AllTypesNilInsertBuilder = put(AllTypesNil.doublePrecisionCol, value)
    fun bitCol(value: Boolean?): AllTypesNilInsertBuilder = put(AllTypesNil.bitCol, value)
    fun binaryCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.binaryCol, value)
    fun blobCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.blobCol, value)
    fun charCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.charCol, value)
    fun charByteCol(value: ByteArray?): AllTypesNilInsertBuilder = put(AllTypesNil.charByteCol, value)
    fun enumCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.enumCol, value)
    fun jsonCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.jsonCol, value)
    fun textCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.textCol, value)
    fun varcharCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.varcharCol, value)
    fun setCol(value: String?): AllTypesNilInsertBuilder = put(AllTypesNil.setCol, value)
    fun dateCol(value: java.time.LocalDate?): AllTypesNilInsertBuilder = put(AllTypesNil.dateCol, value)
    fun timeCol(value: java.time.LocalTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timeCol, value)
    fun datetimeCol(value: java.time.LocalDateTime?): AllTypesNilInsertBuilder = put(AllTypesNil.datetimeCol, value)
    fun timestampCol(value: java.time.LocalDateTime?): AllTypesNilInsertBuilder = put(AllTypesNil.timestampCol, value)
    fun yearCol(value: Int?): AllTypesNilInsertBuilder = put(AllTypesNil.yearCol, value)

    fun mandatoryColumns(int1Col: Boolean, tinyintCol: Boolean) : AllTypesNilInsertBuilder {
      mandatory(AllTypesNil.int1Col, int1Col)
      mandatory(AllTypesNil.tinyintCol, tinyintCol)
      return this
    }


    override fun insertRow(entity: Entity<*, *>): Long {
      entity as AllTypesNilRow
      add(AllTypesNil.decimalCol, entity.decimalCol)
      add(AllTypesNil.decCol, entity.decCol)
      add(AllTypesNil.numericCol, entity.numericCol)
      add(AllTypesNil.fixedCol, entity.fixedCol)
      add(AllTypesNil.int1Col, entity.int1Col)
      add(AllTypesNil.tinyintCol, entity.tinyintCol)
      add(AllTypesNil.smallintCol, entity.smallintCol)
      add(AllTypesNil.int2Col, entity.int2Col)
      add(AllTypesNil.mediumintCol, entity.mediumintCol)
      add(AllTypesNil.int3Col, entity.int3Col)
      add(AllTypesNil.intCol, entity.intCol)
      add(AllTypesNil.int4Col, entity.int4Col)
      add(AllTypesNil.bigintCol, entity.bigintCol)
      add(AllTypesNil.int8Col, entity.int8Col)
      add(AllTypesNil.floatCol, entity.floatCol)
      add(AllTypesNil.doubleCol, entity.doubleCol)
      add(AllTypesNil.doublePrecisionCol, entity.doublePrecisionCol)
      add(AllTypesNil.bitCol, entity.bitCol)
      add(AllTypesNil.binaryCol, entity.binaryCol)
      add(AllTypesNil.blobCol, entity.blobCol)
      add(AllTypesNil.charCol, entity.charCol)
      add(AllTypesNil.charByteCol, entity.charByteCol)
      add(AllTypesNil.enumCol, entity.enumCol)
      add(AllTypesNil.jsonCol, entity.jsonCol)
      add(AllTypesNil.textCol, entity.textCol)
      add(AllTypesNil.varcharCol, entity.varcharCol)
      add(AllTypesNil.setCol, entity.setCol)
      add(AllTypesNil.dateCol, entity.dateCol)
      add(AllTypesNil.timeCol, entity.timeCol)
      add(AllTypesNil.datetimeCol, entity.datetimeCol)
      add(AllTypesNil.timestampCol, entity.timestampCol)
      add(AllTypesNil.yearCol, entity.yearCol)
      return execute()
    }    
        
}


data class AllTypesNilRow(
val id: Long = 0,
  val decimalCol: java.math.BigDecimal?,
  val decCol: java.math.BigDecimal?,
  val numericCol: java.math.BigDecimal?,
  val fixedCol: java.math.BigDecimal?,
  val int1Col: Boolean,
  val tinyintCol: Boolean,
  val smallintCol: Int?,
  val int2Col: Int?,
  val mediumintCol: Int?,
  val int3Col: Int?,
  val intCol: Long?,
  val int4Col: Long?,
  val bigintCol: Long?,
  val int8Col: Long?,
  val floatCol: Float?,
  val doubleCol: Double?,
  val doublePrecisionCol: Double?,
  val bitCol: Boolean?,
  val binaryCol: ByteArray?,
  val blobCol: ByteArray?,
  val charCol: String?,
  val charByteCol: ByteArray?,
  val enumCol: String?,
  val jsonCol: String?,
  val textCol: String?,
  val varcharCol: String?,
  val setCol: String?,
  val dateCol: java.time.LocalDate?,
  val timeCol: java.time.LocalTime?,
  val datetimeCol: java.time.LocalDateTime?,
  val timestampCol: java.time.LocalDateTime?,
  val yearCol: Int?    
) : Entity<AllTypesNilUpdateBuilder, AllTypesNilInsertBuilder>(AllTypesNil.metadata())
        
