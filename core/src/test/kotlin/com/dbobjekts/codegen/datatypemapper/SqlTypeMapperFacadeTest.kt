package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.vendors.Vendors
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SqlTypeMapperFacadeTest {

    val customMappers: List<CustomColumnTypeMapper<*>> = listOf<CustomColumnTypeMapper<*>>(
        JDBCTypeOverrideMapper("BIT", NumberAsBooleanColumn::class.java),
        JDBCTypeOverrideMapper("INTEGER", LongColumn::class.java),
        ColumnTypeMapperByNameMatch("person_id", LongColumn::class.java),
        ColumnTypeMapperByNameMatch("employees", LongColumn::class.java),
        ColumnTypeMapperByNameMatch("employees", LongColumn::class.java, schema = "hr"),
    )
    val mapper: ColumnTypeResolver = ColumnTypeResolver(Vendors.H2.defaultMapper, customMappers)

    @Test
    fun `person_id is LONG`() {
        assertEquals(
            "LongColumn",
            mapper.mapDataType(ColumnMappingProperties.of(column = "person_id", jdbcType = "CHAR"))::class.java.simpleName
        )
    }

    @Test
    fun `int becomes long`() {
        assertEquals(
            "LongColumn",
            mapper.mapDataType(ColumnMappingProperties.of(column = "nmb_children", jdbcType = "INTEGER"))::class.java.simpleName
        )
    }

    @Test
    fun `bit becomes boolean`() {
        assertEquals(
            "NumberAsBooleanColumn",
            mapper.mapDataType(ColumnMappingProperties.of(column = "has_children", jdbcType = "BIT"))::class.java.simpleName
        )
    }

    @Test
    fun `varchar takes default mapping`() {
        val columnType = mapper.mapDataType(ColumnMappingProperties.of(column = "name", jdbcType = "CHARACTER", isNullable = false))
        assertEquals("VarcharColumn", columnType::class.java.simpleName)
    }

    @Test
    fun `unknown type throws`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            mapper.mapDataType(ColumnMappingProperties.of(column = "location", jdbcType = "GEODATA"))
        }
    }

    @Test
    fun `map schema and column to long column`() {
        assertThat(
            mapper.mapDataType(
                ColumnMappingProperties.of(
                    schema = "hr",
                    column = "employees",
                    jdbcType = "whatever"
                )
            )::class.java.simpleName
        ).isEqualTo("LongColumn")
        assertThat(
            mapper.mapDataType(
                ColumnMappingProperties.of(
                    schema = "finance",
                    column = "employees",
                    jdbcType = "whatever"
                )
            )::class.java.simpleName
        ).isEqualTo("LongColumn")
    }
}

