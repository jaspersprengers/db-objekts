package com.dbobjekts.integration

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.StandardColumnTypeMapper
import com.dbobjekts.example.custom.AddressTypeMapper
import com.dbobjekts.fixture.TestSourceWriter
import com.dbobjekts.metadata.column.ColumnType
import com.dbobjekts.vendors.MariaDB
import org.junit.jupiter.api.Test


class CodeGeneratorTest {


    @Test
    fun `create schemas for live db`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor(MariaDB).configureDataSource().password("test").url("jdbc:mariadb://localhost:3306/classicmodels")
            .user("root")//.driverClassName("org.mariadb.jdbc.Driver")
        generator.outputConfigurer().basePackageForSources("com.dbobjekts")

        val mapping = generator.mappingConfigurer()
        generator.exclusionConfigurer().ignoreTables("classicmodels", "flyway_schema_history")
        mapping.addColumnTypeMapper(AddressTypeMapper)
            .addColumnTypeMapper(StandardColumnTypeMapper("INT_BOOLEAN", ColumnType.NUMBER_AS_BOOLEAN))
        //.generatedPrimaryKeyConfiguration().autoIncrementPrimaryKey()

    }
}
