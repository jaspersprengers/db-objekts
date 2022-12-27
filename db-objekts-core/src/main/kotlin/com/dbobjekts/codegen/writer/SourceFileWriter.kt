package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset

class SourceFileWriter(private val baseDir: String) {
    private val sep = File.separator

    fun write(source: String, packageName: PackageName, fileName: String) {
        val fileObj = File("${packagePath(packageName)}$sep$fileName")
        FileUtils.writeStringToFile(fileObj, source, Charset.defaultCharset())
    }

    fun packagePath(packageName: PackageName): String {
        val sep = File.separator
        val packagePath = packageName.asFilePath()
        return "$baseDir$sep$packagePath"
    }

}
