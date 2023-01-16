package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.vendors.h2.H2Vendor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ColumnTypeResolverTest {

    val props = ColumnMappingProperties.of(jdbcType = "mood", isNullable = true)

    @Test
    fun `test custom type mapping`() {
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(MapAnythingToVarcharMapper))
        assertThat(resolver.mapDataType(props)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThat(resolver.mapDataType(props.copy(isNullable = false))).isInstanceOf(VarcharColumn::class.java)
    }

    @Test
    fun `test address type mapping`() {
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(MapAnythingToVarcharMapper))
        assertThat(resolver.mapDataType(props)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThat(resolver.mapDataType(props.copy(isNullable = false))).isInstanceOf(VarcharColumn::class.java)
    }

    @Test
    fun `map unknown column`(){
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper)
        assertThatThrownBy {resolver.mapDataType(props)}
    }

    object MapAnythingToVarcharMapper : CustomColumnTypeMapper<VarcharColumn>() {
        override fun invoke(properties: ColumnMappingProperties) = VarcharColumn::class.java
    }

}
