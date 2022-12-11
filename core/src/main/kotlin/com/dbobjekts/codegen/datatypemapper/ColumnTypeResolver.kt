package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.AnyColumn
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.metadata.Columns
import org.slf4j.LoggerFactory

class ColumnTypeResolver(
    val defaultMapper: ColumnTypeMapper,
    val customMappers: List<ColumnTypeMapper> = listOf()
) {
    private val logger = LoggerFactory.getLogger(ColumnTypeResolver::class.java)

    fun mapDataType(props: ColumnMappingProperties): AnyColumn =
        getCustomMapping(props)?.let {
            logger.debug("Using custom datatype for ${props.schema}.${props.table}.${props.column}")
            it
        } ?: getDefaultMapping(props)


    fun getDefaultMapping(props: ColumnMappingProperties): AnyColumn =
        defaultMapper(props)
            ?: throw IllegalStateException("Unable to find matching datatype for column ${props.schema}.${props.table}.${props.column} of type ${props.jdbcType}. No custom mapping associated.")

    fun getForeignKeyColumnForType(schema: SchemaName, tableName: TableName, columnMetaData: ColumnMetaData): AnyColumn =
        Columns.getForeignKeyColumnForType(mapDataType(ColumnMappingProperties.fromMetaData(schema, tableName, columnMetaData)))

    private fun getCustomMapping(props: ColumnMappingProperties): AnyColumn? =
        customMappers.map { it.invoke(props) }
            .filterNotNull().firstOrNull()


}
