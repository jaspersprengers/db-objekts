package com.dbobjekts.integration.mariadb

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.util.PathUtil
import org.junit.jupiter.api.Test


class CreateCatalog {

    @Test
    fun main() {

        val dataSource = HikariDataSourceFactory.create(url = "jdbc:mariadb://localhost:3306/", username = "test", password = "test")

        val gen = CodeGenerator()
        gen.withDataSource(dataSource)
        gen.outputConfigurer().basePackageForSources("com.dbobjekts.integration.mariadb.catalog")
            .basePackageForSources("com.dbobjekts.integration.mariadb")
            //.sourceWriter(writer)
            .outputDirectoryForGeneratedSources(PathUtil.getGeneratedSourceDir())
        gen.generateSourceFiles()
    }
}
