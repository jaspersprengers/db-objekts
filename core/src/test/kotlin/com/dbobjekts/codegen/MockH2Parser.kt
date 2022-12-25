package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.parsers.ForeignKeyMetaDataRow
import com.dbobjekts.codegen.parsers.CatalogParser
import com.dbobjekts.codegen.parsers.ParserConfig
import com.dbobjekts.codegen.parsers.TableMetaDataRow

class MockH2Parser(
    private val tableRows: List<TableMetaDataRow>,
    private val fkRows: List<ForeignKeyMetaDataRow>,
    parserConfig: ParserConfig
) : CatalogParser(parserConfig) {

    override fun extractCatalogs(): List<String> = listOf("TEST")

    override fun extractColumnAndTableMetaDataFromDB(): List<TableMetaDataRow> {
        return tableRows
    }

    override fun extractForeignKeyMetaDataFromDB(): List<ForeignKeyMetaDataRow> {
        return fkRows
    }
}
