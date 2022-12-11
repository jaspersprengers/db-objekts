package com.dbobjekts.fixture

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.writer.SourceWriter
import java.util.*


class TestSourceWriter : SourceWriter {
    private val captured = LinkedList<CapturedData>()

    fun valuesCaptured(): List<CapturedData> = captured.toList()

    override fun write(
        source: String,
        packageName: PackageName,
        baseDir: String?,
        fileName: String
    ) {
        captured += CapturedData(source, packageName, baseDir, fileName)
    }

    override fun toString(): String =
        valuesCaptured()
            .map { line -> "${line.baseDir}.${line.file}\n${line.source}" }
            .joinToString("\n\n")


    data class CapturedData(
        val source: String,
        val packageName: PackageName,
        val baseDir: String?,
        val file: String
    )

}
