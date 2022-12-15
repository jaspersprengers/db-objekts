package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig


interface DBParserFactory {
    fun create(codeGeneratorConfig: CodeGeneratorConfig): CatalogParser
}
