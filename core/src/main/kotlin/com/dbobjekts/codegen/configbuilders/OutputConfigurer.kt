package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.writer.SourceWriter


class OutputConfigurer {

    var basedirOpt: String? = null
    var basePackage: PackageName? = null
    var customSourceWriter: SourceWriter? = null

    fun basePackageForSources(pkg: String): OutputConfigurer {
        basePackage = PackageName(pkg)
        return this
    }

    fun outputDirectoryForGeneratedSources(path: String): OutputConfigurer {
        require(ValidateFile(path, isDir = true), { "$path is not a valid directory" })
        basedirOpt = path
        return this
    }

    fun sourceWriter(writer: SourceWriter): OutputConfigurer {
        this.customSourceWriter = writer
        return this
    }


}
