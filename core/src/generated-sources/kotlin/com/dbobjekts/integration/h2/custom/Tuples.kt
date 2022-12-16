package com.dbobjekts.integration.h2.custom

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Tuples:Table("TUPLES"), HasUpdateBuilder<TuplesUpdateBuilder, TuplesInsertBuilder> {
    val c1 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C1")
    val c2 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C2")
    val c3 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C3")
    val c4 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C4")
    val c5 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C5")
    val c6 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C6")
    val c7 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C7")
    val c8 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C8")
    val c9 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C9")
    val c10 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C10")
    val c11 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C11")
    val c12 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C12")
    val c13 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C13")
    val c14 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C14")
    val c15 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C15")
    val c16 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C16")
    val c17 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C17")
    val c18 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C18")
    val c19 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C19")
    val c20 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C20")
    val c21 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C21")
    val c22 = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "C22")
    override val columns: List<AnyColumn> = listOf(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22)
    override fun updater(connection: ConnectionAdapter): TuplesUpdateBuilder = TuplesUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): TuplesInsertBuilder = TuplesInsertBuilder(connection)
}

class TuplesUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Tuples, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun c1(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c1, value)
    fun c2(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c2, value)
    fun c3(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c3, value)
    fun c4(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c4, value)
    fun c5(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c5, value)
    fun c6(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c6, value)
    fun c7(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c7, value)
    fun c8(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c8, value)
    fun c9(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c9, value)
    fun c10(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c10, value)
    fun c11(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c11, value)
    fun c12(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c12, value)
    fun c13(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c13, value)
    fun c14(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c14, value)
    fun c15(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c15, value)
    fun c16(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c16, value)
    fun c17(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c17, value)
    fun c18(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c18, value)
    fun c19(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c19, value)
    fun c20(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c20, value)
    fun c21(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c21, value)
    fun c22(value: Int?): TuplesUpdateBuilder = ct.put(Tuples.c22, value)
}

class TuplesInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Tuples, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun c1(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c1, value)
    fun c2(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c2, value)
    fun c3(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c3, value)
    fun c4(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c4, value)
    fun c5(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c5, value)
    fun c6(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c6, value)
    fun c7(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c7, value)
    fun c8(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c8, value)
    fun c9(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c9, value)
    fun c10(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c10, value)
    fun c11(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c11, value)
    fun c12(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c12, value)
    fun c13(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c13, value)
    fun c14(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c14, value)
    fun c15(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c15, value)
    fun c16(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c16, value)
    fun c17(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c17, value)
    fun c18(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c18, value)
    fun c19(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c19, value)
    fun c20(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c20, value)
    fun c21(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c21, value)
    fun c22(value: Int?): TuplesInsertBuilder = ct.put(Tuples.c22, value)

}

