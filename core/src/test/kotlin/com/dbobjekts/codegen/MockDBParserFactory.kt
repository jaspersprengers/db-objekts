package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.parsers.*
import com.dbobjekts.vendors.Vendors


class MockDBParserFactory : DBParserFactory {

    private val tableRows = mutableListOf<TableMetaDataRow>()

    private val fkRows = mutableListOf<ForeignKeyMetaDataRow>()

    fun addRow(row: TableMetaDataRow): MockDBParserFactory {
        tableRows += row
        return this
    }

    fun addRow(row: ForeignKeyMetaDataRow): MockDBParserFactory {
        fkRows += row
        return this
    }


    override fun create(codeGeneratorConfig: CodeGeneratorConfig): CatalogParser {
        return MockH2Parser(tableRows, fkRows, ParserConfig.fromCodeGeneratorConfig(Vendors.H2, codeGeneratorConfig))
    }
}
