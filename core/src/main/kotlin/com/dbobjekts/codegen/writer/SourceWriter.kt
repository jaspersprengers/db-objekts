package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName


interface SourceWriter {
  fun write(source: String,
            packageName: PackageName,
            baseDir: String?,
            fileName: String)
}
