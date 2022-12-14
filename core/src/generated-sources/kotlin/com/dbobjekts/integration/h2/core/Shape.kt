package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Shape:Table("SHAPE"), HasUpdateBuilder<ShapeUpdateBuilder, ShapeInsertBuilder> {
    val height = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "HEIGHT")
    val width = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "WIDTH")
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(height,width,createdDt,modifiedDt)
    override fun updater(connection: ConnectionAdapter): ShapeUpdateBuilder = ShapeUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): ShapeInsertBuilder = ShapeInsertBuilder(connection)
}

class ShapeUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Shape, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun height(value: Double?): ShapeUpdateBuilder = ct.put(Shape.height, value)
    fun width(value: Double?): ShapeUpdateBuilder = ct.put(Shape.width, value)
    fun createdDt(value: java.time.Instant): ShapeUpdateBuilder = ct.put(Shape.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): ShapeUpdateBuilder = ct.put(Shape.modifiedDt, value)
}

class ShapeInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Shape, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun height(value: Double?): ShapeInsertBuilder = ct.put(Shape.height, value)
    fun width(value: Double?): ShapeInsertBuilder = ct.put(Shape.width, value)
    fun createdDt(value: java.time.Instant): ShapeInsertBuilder = ct.put(Shape.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): ShapeInsertBuilder = ct.put(Shape.modifiedDt, value)

    fun mandatoryColumns(createdDt: java.time.Instant) : ShapeInsertBuilder {
      ct.put(Shape.createdDt, createdDt)
      return this
    }

}

