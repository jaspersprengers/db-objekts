package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset

class SourceFileWriter {

    fun write(source: String, packageName: PackageName, baseDir: String, fileName: String) {
        val sep = File.separator
        val packagePath = packageName.asFilePath()
        val fileObj = File("$baseDir$sep$packagePath$sep$fileName")
        FileUtils.writeStringToFile(fileObj, source, Charset.defaultCharset())
    }

}
