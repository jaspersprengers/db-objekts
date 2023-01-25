package com.dbobjekts.codegen

import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AggregateType
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.metadata.column.VarcharColumn
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor


class EnumTest {

    @Test
    fun `enum test `(){

        val clz = AddressType::class

        val home = AddressType::class.java.enumConstants.filterIsInstance(Enum::class.java).first { it.name == "HOME" } as AddressType
        Assertions.assertThat(home).isSameAs(AddressType.HOME)
    }


    @Test
    fun `get nullable column`(){
        val col = ColumnFactory.varcharColumn(false) as NonNullableColumn<String>
        val nullable = ColumnFactory.nullableColumn(col)
    }



}
