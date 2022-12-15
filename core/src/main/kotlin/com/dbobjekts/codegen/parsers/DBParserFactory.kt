package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig


interface DBParserFactory {
    fun create(
        codeGeneratorConfig: CodeGeneratorConfig, logger: ProgressLogger
    ): LiveDBParser
}
