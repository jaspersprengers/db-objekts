package com.dbobjekts.example.s2

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.example.s1.Parent
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

object Child:Table("child"), HasUpdateBuilder<ChildUpdateBuilder, ChildInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyIntegerColumn(this, "id")
    val firstParentId = com.dbobjekts.metadata.column.ForeignKeyIntColumn(this, "first_parent_id", Parent.id)
    val secondParentId = com.dbobjekts.metadata.column.OptionalForeignKeyIntColumn(this, "second_parent_id", Parent.id)
    override val columns: List<AnyColumn> = listOf(id,firstParentId,secondParentId)
    override fun updater(connection: ConnectionAdapter): ChildUpdateBuilder = ChildUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): ChildInsertBuilder = ChildInsertBuilder(connection)
}

class ChildUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Child, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun firstParentId(value: Int): ChildUpdateBuilder = ct.put(Child.firstParentId, value)
    fun secondParentId(value: Int?): ChildUpdateBuilder = ct.put(Child.secondParentId, value)
}

class ChildInsertBuilder(connection: ConnectionAdapter): InsertBuilderBase(Child, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun firstParentId(value: Int): ChildInsertBuilder = ct.put(Child.firstParentId, value)
    fun secondParentId(value: Int?): ChildInsertBuilder = ct.put(Child.secondParentId, value)

    fun mandatoryColumns(firstParentId: Int) : ChildInsertBuilder {
      ct.put(Child.firstParentId, firstParentId)
      return this
    }

}

