package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object AllTypesNn:Table("ALL_TYPES_NN"), HasUpdateBuilder<AllTypesNnUpdateBuilder, AllTypesNnInsertBuilder> {
    val tinyintC = com.dbobjekts.metadata.column.ByteColumn(this, "TINYINT_C")
    val smallintC = com.dbobjekts.metadata.column.IntegerColumn(this, "SMALLINT_C")
    val integerC = com.dbobjekts.metadata.column.IntegerColumn(this, "INTEGER_C")
    val intC = com.dbobjekts.metadata.column.IntegerColumn(this, "INT_C")
    val charC = com.dbobjekts.metadata.column.VarcharColumn(this, "CHAR_C")
    val varcharC = com.dbobjekts.metadata.column.VarcharColumn(this, "VARCHAR_C")
    val bigintC = com.dbobjekts.metadata.column.LongColumn(this, "BIGINT_C")
    val floatC = com.dbobjekts.metadata.column.FloatColumn(this, "FLOAT_C")
    val doubleC = com.dbobjekts.metadata.column.DoubleColumn(this, "DOUBLE_C")
    val timeC = com.dbobjekts.metadata.column.TimeColumn(this, "TIME_C")
    val dateC = com.dbobjekts.metadata.column.DateColumn(this, "DATE_C")
    val timestampC = com.dbobjekts.metadata.column.TimeStampColumn(this, "TIMESTAMP_C")
    val timestampTzC = com.dbobjekts.metadata.column.OffsetDateTimeColumn(this, "TIMESTAMP_TZ_C")
    val booleanC = com.dbobjekts.metadata.column.BooleanColumn(this, "BOOLEAN_C")
    val intBooleanC = com.dbobjekts.metadata.column.ByteColumn(this, "INT_BOOLEAN_C")
    val blobC = com.dbobjekts.metadata.column.BlobColumn(this, "BLOB_C")
    val clobC = com.dbobjekts.metadata.column.ClobColumn(this, "CLOB_C")
    override val columns: List<AnyColumn> = listOf(tinyintC,smallintC,integerC,intC,charC,varcharC,bigintC,floatC,doubleC,timeC,dateC,timestampC,timestampTzC,booleanC,intBooleanC,blobC,clobC)
    override fun updater(connection: ConnectionAdapter): AllTypesNnUpdateBuilder = AllTypesNnUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): AllTypesNnInsertBuilder = AllTypesNnInsertBuilder(connection)
}

class AllTypesNnUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(AllTypesNn, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun tinyintC(value: Byte): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.tinyintC, value)
    fun smallintC(value: Int): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.smallintC, value)
    fun integerC(value: Int): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.integerC, value)
    fun intC(value: Int): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.intC, value)
    fun charC(value: String): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.charC, value)
    fun varcharC(value: String): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.varcharC, value)
    fun bigintC(value: Long): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.bigintC, value)
    fun floatC(value: Float): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.floatC, value)
    fun doubleC(value: Double): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.doubleC, value)
    fun timeC(value: java.time.LocalTime): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.timeC, value)
    fun dateC(value: java.time.LocalDate): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.dateC, value)
    fun timestampC(value: java.time.Instant): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.timestampC, value)
    fun timestampTzC(value: java.time.OffsetDateTime): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.timestampTzC, value)
    fun booleanC(value: Boolean): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.booleanC, value)
    fun intBooleanC(value: Byte): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.intBooleanC, value)
    fun blobC(value: java.sql.Blob): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.blobC, value)
    fun clobC(value: java.sql.Clob): AllTypesNnUpdateBuilder = ct.put(AllTypesNn.clobC, value)
}

class AllTypesNnInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(AllTypesNn, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun tinyintC(value: Byte): AllTypesNnInsertBuilder = ct.put(AllTypesNn.tinyintC, value)
    fun smallintC(value: Int): AllTypesNnInsertBuilder = ct.put(AllTypesNn.smallintC, value)
    fun integerC(value: Int): AllTypesNnInsertBuilder = ct.put(AllTypesNn.integerC, value)
    fun intC(value: Int): AllTypesNnInsertBuilder = ct.put(AllTypesNn.intC, value)
    fun charC(value: String): AllTypesNnInsertBuilder = ct.put(AllTypesNn.charC, value)
    fun varcharC(value: String): AllTypesNnInsertBuilder = ct.put(AllTypesNn.varcharC, value)
    fun bigintC(value: Long): AllTypesNnInsertBuilder = ct.put(AllTypesNn.bigintC, value)
    fun floatC(value: Float): AllTypesNnInsertBuilder = ct.put(AllTypesNn.floatC, value)
    fun doubleC(value: Double): AllTypesNnInsertBuilder = ct.put(AllTypesNn.doubleC, value)
    fun timeC(value: java.time.LocalTime): AllTypesNnInsertBuilder = ct.put(AllTypesNn.timeC, value)
    fun dateC(value: java.time.LocalDate): AllTypesNnInsertBuilder = ct.put(AllTypesNn.dateC, value)
    fun timestampC(value: java.time.Instant): AllTypesNnInsertBuilder = ct.put(AllTypesNn.timestampC, value)
    fun timestampTzC(value: java.time.OffsetDateTime): AllTypesNnInsertBuilder = ct.put(AllTypesNn.timestampTzC, value)
    fun booleanC(value: Boolean): AllTypesNnInsertBuilder = ct.put(AllTypesNn.booleanC, value)
    fun intBooleanC(value: Byte): AllTypesNnInsertBuilder = ct.put(AllTypesNn.intBooleanC, value)
    fun blobC(value: java.sql.Blob): AllTypesNnInsertBuilder = ct.put(AllTypesNn.blobC, value)
    fun clobC(value: java.sql.Clob): AllTypesNnInsertBuilder = ct.put(AllTypesNn.clobC, value)

    fun mandatoryColumns(tinyintC: Byte, smallintC: Int, integerC: Int, intC: Int, charC: String, varcharC: String, bigintC: Long, floatC: Float, doubleC: Double, timeC: java.time.LocalTime, dateC: java.time.LocalDate, timestampC: java.time.Instant, timestampTzC: java.time.OffsetDateTime, booleanC: Boolean, intBooleanC: Byte, blobC: java.sql.Blob, clobC: java.sql.Clob) : AllTypesNnInsertBuilder {
      ct.put(AllTypesNn.tinyintC, tinyintC)
      ct.put(AllTypesNn.smallintC, smallintC)
      ct.put(AllTypesNn.integerC, integerC)
      ct.put(AllTypesNn.intC, intC)
      ct.put(AllTypesNn.charC, charC)
      ct.put(AllTypesNn.varcharC, varcharC)
      ct.put(AllTypesNn.bigintC, bigintC)
      ct.put(AllTypesNn.floatC, floatC)
      ct.put(AllTypesNn.doubleC, doubleC)
      ct.put(AllTypesNn.timeC, timeC)
      ct.put(AllTypesNn.dateC, dateC)
      ct.put(AllTypesNn.timestampC, timestampC)
      ct.put(AllTypesNn.timestampTzC, timestampTzC)
      ct.put(AllTypesNn.booleanC, booleanC)
      ct.put(AllTypesNn.intBooleanC, intBooleanC)
      ct.put(AllTypesNn.blobC, blobC)
      ct.put(AllTypesNn.clobC, clobC)
      return this
    }

}

