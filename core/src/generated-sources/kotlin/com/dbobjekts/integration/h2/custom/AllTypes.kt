package com.dbobjekts.integration.h2.custom

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object AllTypes:Table("ALL_TYPES"), HasUpdateBuilder<AllTypesUpdateBuilder, AllTypesInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val tinyintC = com.dbobjekts.metadata.column.NullableByteColumn(this, "TINYINT_C")
    val smallintC = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "SMALLINT_C")
    val integerC = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "INTEGER_C")
    val intC = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "INT_C")
    val charC = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CHAR_C")
    val blobC = com.dbobjekts.metadata.column.NullableBlobColumn(this, "BLOB_C")
    val clobC = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "CLOB_C")
    val varcharC = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "VARCHAR_C")
    val bigintC = com.dbobjekts.metadata.column.NullableLongColumn(this, "BIGINT_C")
    val floatC = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "FLOAT_C")
    val doubleC = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "DOUBLE_C")
    val timeC = com.dbobjekts.metadata.column.NullableTimeColumn(this, "TIME_C")
    val dateC = com.dbobjekts.metadata.column.NullableDateColumn(this, "DATE_C")
    val timestampC = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "TIMESTAMP_C")
    val timestampTzC = com.dbobjekts.metadata.column.NullableOffsetDateTimeColumn(this, "TIMESTAMP_TZ_C")
    val booleanC = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "BOOLEAN_C")
    val uuidC = com.dbobjekts.vendors.h2.UUIDColumn(this, "UUID_C")
    val intBooleanC = com.dbobjekts.metadata.column.NullableByteColumn(this, "INT_BOOLEAN_C")
    override val columns: List<AnyColumn> = listOf(id,tinyintC,smallintC,integerC,intC,charC,blobC,clobC,varcharC,bigintC,floatC,doubleC,timeC,dateC,timestampC,timestampTzC,booleanC,uuidC,intBooleanC)
    override fun updater(connection: ConnectionAdapter): AllTypesUpdateBuilder = AllTypesUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): AllTypesInsertBuilder = AllTypesInsertBuilder(connection)
}

class AllTypesUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(AllTypes, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun tinyintC(value: Byte?): AllTypesUpdateBuilder = ct.put(AllTypes.tinyintC, value)
    fun smallintC(value: Int?): AllTypesUpdateBuilder = ct.put(AllTypes.smallintC, value)
    fun integerC(value: Int?): AllTypesUpdateBuilder = ct.put(AllTypes.integerC, value)
    fun intC(value: Int?): AllTypesUpdateBuilder = ct.put(AllTypes.intC, value)
    fun charC(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.charC, value)
    fun blobC(value: java.sql.Blob?): AllTypesUpdateBuilder = ct.put(AllTypes.blobC, value)
    fun clobC(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.clobC, value)
    fun varcharC(value: String?): AllTypesUpdateBuilder = ct.put(AllTypes.varcharC, value)
    fun bigintC(value: Long?): AllTypesUpdateBuilder = ct.put(AllTypes.bigintC, value)
    fun floatC(value: Double?): AllTypesUpdateBuilder = ct.put(AllTypes.floatC, value)
    fun doubleC(value: Double?): AllTypesUpdateBuilder = ct.put(AllTypes.doubleC, value)
    fun timeC(value: java.time.LocalTime?): AllTypesUpdateBuilder = ct.put(AllTypes.timeC, value)
    fun dateC(value: java.time.LocalDate?): AllTypesUpdateBuilder = ct.put(AllTypes.dateC, value)
    fun timestampC(value: java.time.Instant?): AllTypesUpdateBuilder = ct.put(AllTypes.timestampC, value)
    fun timestampTzC(value: java.time.OffsetDateTime?): AllTypesUpdateBuilder = ct.put(AllTypes.timestampTzC, value)
    fun booleanC(value: Boolean?): AllTypesUpdateBuilder = ct.put(AllTypes.booleanC, value)
    fun uuidC(value: java.util.UUID): AllTypesUpdateBuilder = ct.put(AllTypes.uuidC, value)
    fun intBooleanC(value: Byte?): AllTypesUpdateBuilder = ct.put(AllTypes.intBooleanC, value)
}

class AllTypesInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(AllTypes, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun tinyintC(value: Byte?): AllTypesInsertBuilder = ct.put(AllTypes.tinyintC, value)
    fun smallintC(value: Int?): AllTypesInsertBuilder = ct.put(AllTypes.smallintC, value)
    fun integerC(value: Int?): AllTypesInsertBuilder = ct.put(AllTypes.integerC, value)
    fun intC(value: Int?): AllTypesInsertBuilder = ct.put(AllTypes.intC, value)
    fun charC(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.charC, value)
    fun blobC(value: java.sql.Blob?): AllTypesInsertBuilder = ct.put(AllTypes.blobC, value)
    fun clobC(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.clobC, value)
    fun varcharC(value: String?): AllTypesInsertBuilder = ct.put(AllTypes.varcharC, value)
    fun bigintC(value: Long?): AllTypesInsertBuilder = ct.put(AllTypes.bigintC, value)
    fun floatC(value: Double?): AllTypesInsertBuilder = ct.put(AllTypes.floatC, value)
    fun doubleC(value: Double?): AllTypesInsertBuilder = ct.put(AllTypes.doubleC, value)
    fun timeC(value: java.time.LocalTime?): AllTypesInsertBuilder = ct.put(AllTypes.timeC, value)
    fun dateC(value: java.time.LocalDate?): AllTypesInsertBuilder = ct.put(AllTypes.dateC, value)
    fun timestampC(value: java.time.Instant?): AllTypesInsertBuilder = ct.put(AllTypes.timestampC, value)
    fun timestampTzC(value: java.time.OffsetDateTime?): AllTypesInsertBuilder = ct.put(AllTypes.timestampTzC, value)
    fun booleanC(value: Boolean?): AllTypesInsertBuilder = ct.put(AllTypes.booleanC, value)
    fun uuidC(value: java.util.UUID): AllTypesInsertBuilder = ct.put(AllTypes.uuidC, value)
    fun intBooleanC(value: Byte?): AllTypesInsertBuilder = ct.put(AllTypes.intBooleanC, value)

    fun mandatoryColumns(uuidC: java.util.UUID) : AllTypesInsertBuilder {
      ct.put(AllTypes.uuidC, uuidC)
      return this
    }

}

