package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.parsers.DBParserFactory
import com.dbobjekts.codegen.parsers.ForeignKeyMetaDataRow
import com.dbobjekts.codegen.parsers.LiveDBParser
import com.dbobjekts.codegen.parsers.TableMetaDataRow


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


    override fun create(codeGeneratorConfig: CodeGeneratorConfig, logger: ProgressLogger): LiveDBParser {
        return MockH2Parser(tableRows, fkRows, codeGeneratorConfig, logger)
    }
}
