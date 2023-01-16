package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.vendors.h2.H2Vendor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ColumnTypeResolverTest {

    val customProps = ColumnMappingProperties.of(
        schema = "core",
        table = "employee",
        column = "date_of_birth",
        jdbcType = "custom",
        isNullable = true
    )

    @Test
    fun `test custom type mapping`() {
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(MapAnythingToVarcharMapper))
        assertThat(resolver.mapDataType(customProps)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThat(resolver.mapDataType(customProps.copy(isNullable = false))).isInstanceOf(VarcharColumn::class.java)
    }

    @Test
    fun `test address type mapping`() {
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(MapAnythingToVarcharMapper))
        assertThat(resolver.mapDataType(customProps)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThat(resolver.mapDataType(customProps.copy(isNullable = false))).isInstanceOf(VarcharColumn::class.java)
    }

    @Test
    fun `test exact name match on any table and schema`() {
        val mapper = ColumnTypeMapperByNameMatch("date_of_birth", VarcharColumn::class.java, exactMatch = true)
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(mapper))
        assertThat(resolver.mapDataType(customProps)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThatThrownBy { resolver.mapDataType(customProps.copy(column = ColumnName("date"))) }
    }

    @Test
    fun `test exact name match on specific table and schema`() {
        val mapper = ColumnTypeMapperByNameMatch("date_of_birth", VarcharColumn::class.java, schema = "core", table="employee", exactMatch = true)
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(mapper))
        assertThat(resolver.mapDataType(customProps)).isInstanceOf(NullableVarcharColumn::class.java)
    }

    @Test
    fun `test fuzzy name match on any table and schema`() {
        val mapper = ColumnTypeMapperByNameMatch("date", VarcharColumn::class.java, exactMatch = false)
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper, customMappers = listOf(mapper))
        assertThat(resolver.mapDataType(customProps)).isInstanceOf(NullableVarcharColumn::class.java)
        assertThat(resolver.mapDataType(customProps.copy(column = ColumnName("date")))).isInstanceOf(NullableVarcharColumn::class.java)
    }

    @Test
    fun `map unknown column`() {
        val resolver = ColumnTypeResolver(H2Vendor.defaultMapper)
        assertThatThrownBy { resolver.mapDataType(customProps) }
    }

    object MapAnythingToVarcharMapper : CustomColumnTypeMapper<VarcharColumn>() {
        override fun invoke(properties: ColumnMappingProperties) = VarcharColumn::class.java
    }

}
