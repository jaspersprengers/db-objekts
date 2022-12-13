package com.dbobjekts.integration.mariadb

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.fixture.PathUtil
import com.dbobjekts.vendors.Vendors
import org.junit.jupiter.api.Test


class CreateCatalog {

    @Test
    fun main() {

        val gen = CodeGenerator()
        gen.sourceConfigurer().vendor(Vendors.MARIADB)
            .configureDataSource().password("test").user("root").url("jdbc:mariadb://localhost:3306/")
        gen.mappingConfigurer().generatedPrimaryKeyConfiguration().autoIncrementPrimaryKey()
        gen.outputConfigurer().basePackageForSources("com.dbobjekts.integration.mariadb.catalog")
            .outputDirectoryForGeneratedSources("/Users/jasper/dev/db-objekts/integration/src/test/kotlin")
        gen.generate()

    }
}
