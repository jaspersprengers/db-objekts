package com.dbobjekts.util

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.writer.SourceWriter
import java.util.*


class TestSourceWriter : SourceWriter {
    private val captured = LinkedList<SourceFileInfo>()

    fun sourceFilesProduced(): List<SourceFileInfo> = captured.toList()

    override fun write(
        source: String,
        packageName: PackageName,
        baseDir: String?,
        fileName: String
    ) {
        captured += SourceFileInfo(source, packageName, baseDir, fileName)
    }

    override fun toString(): String =
        sourceFilesProduced()
            .map { line -> "${line.baseDir}.${line.file}\n${line.source}" }
            .joinToString("\n\n")


    data class SourceFileInfo(
        val source: String,
        val packageName: PackageName,
        val baseDir: String?,
        val file: String
    )

}
