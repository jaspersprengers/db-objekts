package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.parsers.ForeignKeyMetaDataRow
import com.dbobjekts.codegen.parsers.LiveDBParser
import com.dbobjekts.codegen.parsers.TableMetaDataRow


class MockH2Parser(
    private val tableRows: List<TableMetaDataRow>,
    private val fkRows : List<ForeignKeyMetaDataRow>,
    codeGeneratorConfig: CodeGeneratorConfig,
    logger: ProgressLogger
) : LiveDBParser(codeGeneratorConfig, logger) {

    override fun extractColumnAndTableMetaDataFromDB(): List<TableMetaDataRow> {
        return tableRows
    }

    override fun extractForeignKeyMetaDataFromDB(): List<ForeignKeyMetaDataRow> {
        return fkRows
    }
}
