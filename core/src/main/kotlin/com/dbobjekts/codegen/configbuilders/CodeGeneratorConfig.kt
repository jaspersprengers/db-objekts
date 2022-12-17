package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.PackageName
import com.dbobjekts.SchemaName
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.SequenceForPrimaryKeyMapper
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.NoGeneratedPrimaryKeyStrategy
import com.dbobjekts.codegen.metadata.PrimaryKeyStrategy
import com.dbobjekts.vendors.Vendor
import javax.sql.DataSource


data class CodeGeneratorConfig(
    val vendor: Vendor,
    val dataSource: DataSource,
    val exclusionConfigurer: ExclusionConfigurer,
    val basePackage: PackageName,
    val customColumnMappers: List<ColumnTypeMapper> = listOf(),
    val sequenceMappers: List<SequenceForPrimaryKeyMapper> = listOf()
){

}

data class DataSourceInfo(val user: String, val password: String?, val url: String, val driver: String?)

