package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.writer.SourceWriter
import com.dbobjekts.util.StringUtil


class OutputConfigurer {

    internal var catalogName: String? = null
    internal var basedirOpt: String? = null
    internal var basePackage: PackageName? = null
    internal var customSourceWriter: SourceWriter? = null

    fun catalogName(name: String): OutputConfigurer {
        catalogName = name
        return this
    }

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
