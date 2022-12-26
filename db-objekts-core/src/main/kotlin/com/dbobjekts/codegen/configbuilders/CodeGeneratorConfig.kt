package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.SequenceForPrimaryKeyMapper
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import javax.sql.DataSource


data class CodeGeneratorConfig(
    val dataSource: DataSource,
    val exclusionConfigurer: ExclusionConfigurer,
    val basePackage: PackageName,
    val customColumnMappers: List<CustomColumnTypeMapper<*>> = listOf(),
    val sequenceMappers: List<SequenceForPrimaryKeyMapper> = listOf()
){

}
