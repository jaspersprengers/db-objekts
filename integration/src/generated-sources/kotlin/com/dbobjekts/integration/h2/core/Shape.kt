package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Shape:Table("SHAPE"), HasUpdateBuilder<ShapeUpdateBuilder, ShapeInsertBuilder> {
    val height = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "HEIGHT")
    val width = com.dbobjekts.metadata.column.NullableDoubleColumn(this, "WIDTH")
    override val columns: List<AnyColumn> = listOf(height,width)
    override val metadata: WriteQueryAccessors<ShapeUpdateBuilder, ShapeInsertBuilder> = WriteQueryAccessors(ShapeUpdateBuilder(), ShapeInsertBuilder())
}

class ShapeUpdateBuilder() : UpdateBuilderBase(Shape) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun height(value: Double?): ShapeUpdateBuilder = ct.put(Shape.height, value)
    fun width(value: Double?): ShapeUpdateBuilder = ct.put(Shape.width, value)
}

class ShapeInsertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun height(value: Double?): ShapeInsertBuilder = ct.put(Shape.height, value)
    fun width(value: Double?): ShapeInsertBuilder = ct.put(Shape.width, value)

}

