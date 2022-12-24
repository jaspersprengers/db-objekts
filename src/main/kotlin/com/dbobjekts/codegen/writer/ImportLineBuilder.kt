package com.dbobjekts.codegen.writer

class ImportLineBuilder {
    private val builder = StringBuilder()

    fun add(portion: String): ImportLineBuilder {
        builder.appendLine("import $portion")
        return this
    }

    fun build(): String = builder.toString()

}
