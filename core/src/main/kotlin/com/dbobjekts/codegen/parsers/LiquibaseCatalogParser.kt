package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.metadata.TableMetaData
import com.dbobjekts.util.XMLUtil
import org.slf4j.LoggerFactory
import java.io.File

class LiquibaseCatalogParser(config: CodeGeneratorConfig, private val logger: ProgressLogger) : CatalogParser(config) {

    private val log = LoggerFactory.getLogger(LiquibaseCatalogParser::class.java)

    override fun createTableMetaData(conf: CodeGeneratorConfig): List<TableMetaData> {
        if (conf.changeLogFiles.isEmpty())
            throw IllegalStateException("Expect at least one liquibase changelog file.")
        conf.customColumnMappers.forEach { log.info("Using custom mapper {}", it.javaClass.simpleName) }

        val includedSchemas = conf.changeLogFiles.filterNot { e -> conf.exclusionConfigurer.schemaIsExcluded(e.key.value) }

        return includedSchemas.flatMap {
            LiquibaseSchemaParser(XMLUtil.read(File(it.value)), it.key, logger).parse()
        }
    }


}
