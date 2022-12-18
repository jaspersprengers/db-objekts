package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.configbuilders.GeneratedPrimaryKeyConfigurer
import com.dbobjekts.codegen.configbuilders.MappingConfigurer
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class SequenceNameResolverStrategyTest {

    val props = ColumnMappingProperties(
        schema = SchemaName("hr"),
        table = TableName("employees"),
        column = ColumnName("address"),
        isNullable = true, isPrimaryKey = true, jdbcType = "VARCHAR"
    )

    @Test
    fun `get sequence`() {
        assertEquals("address", TestResolverStrategy().getSequence(props))
    }

    @Test
    fun `SequenceByPatternResolverStrategy`() {
        assertEquals("hr_employees_seq", SequenceByPatternResolverStrategy("SCHEMA_TABLE_seq").getSequence(props))
    }

    val conf = GeneratedPrimaryKeyConfigurer(MappingConfigurer())

    @Test
    fun `no generated key`() {
        assertEquals(NoGeneratedPrimaryKeyStrategy, conf.getStrategy())
    }

    @Test
    fun `auto-generated key`() {
        conf.autoIncrementPrimaryKey()
        assertEquals(AutoIncrementPrimaryKeyStrategy, conf.getStrategy())
    }

    @Test
    fun `sequence, pattern based`() {
        conf.sequenceNameByPatternResolverStrategy("SCHEMA_TABLE_seq")
        assertEquals("SequenceByPatternResolverStrategy", conf.getStrategy().javaClass.simpleName)
    }

    @Test
    fun `sequence, custom`() {
        conf.sequenceNameResolverStrategy(TestResolverStrategy())
        assertEquals("TestResolverStrategy", conf.getStrategy().javaClass.simpleName)
    }

    class TestResolverStrategy : SequenceNameResolverStrategy() {
        override fun getSequence(columnProperties: ColumnMappingProperties): String = columnProperties.column.value
    }


}



