package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class SequenceNameResolverStrategyTest {

    val props = ColumnMappingProperties(
        schema = SchemaName("hr"),
        table = TableName("employees"),
        column = ColumnName("address"),
        isNullable = true,
        isPrimaryKey = true,
        jdbcType = "VARCHAR",
        defaultMappingType = null
    )

    @Test
    fun `get sequence`() {
        assertEquals("address_seq", TestResolverStrategy().getSequence(props))
    }

    class TestResolverStrategy : SequenceNameResolverStrategy() {
        override fun getSequence(columnProperties: ColumnMappingProperties): String = columnProperties.column.value + "_seq"
    }


}



