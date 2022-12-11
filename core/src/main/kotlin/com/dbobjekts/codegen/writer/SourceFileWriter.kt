package com.dbobjekts.codegen.writer

import com.dbobjekts.PackageName
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset

class SourceFileWriter : SourceWriter {

    override fun write(source: String, packageName: PackageName, baseDir: String?, fileName: String) {
        if (baseDir == null)
            throw IllegalStateException("Could not determine output directory for sources.")
        val sep = File.separator
        val packagePath = packageName.asFilePath()
        val fileObj = File("$baseDir$sep$packagePath$sep$fileName")
        FileUtils.writeStringToFile(fileObj, source, Charset.defaultCharset())
    }

}
