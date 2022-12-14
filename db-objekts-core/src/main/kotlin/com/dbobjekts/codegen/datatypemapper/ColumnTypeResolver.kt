package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.*
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.*
import org.slf4j.LoggerFactory

class ColumnTypeResolver(
    val defaultMapper: VendorDefaultColumnTypeMapper,
    val customMappers: List<CustomColumnTypeMapper<*>> = listOf(),
    val sequenceMappers: List<SequenceForPrimaryKeyResolver> = listOf()
) {
    private val logger = LoggerFactory.getLogger(ColumnTypeResolver::class.java)

    fun mapDataType(props: ColumnMappingProperties): AnyColumn =
        getCustomMapping(props)?.let {
            logger.debug("Using custom datatype for ${props.schema}.${props.table}.${props.column}")
            it
        } ?: getDefaultMapping(props)

    fun mapAutoIncrementColumn(props: ColumnMappingProperties): AnyColumn {
        val defaultColumn = getDefaultMapping(props)
        return when (defaultColumn) {
            is LongColumn -> ColumnFactory.AUTOKEY_LONG
            is IntegerColumn -> ColumnFactory.AUTOKEY_INTEGER
            else -> throw CodeGenerationException("Only LongColumn or IntegerColumn is allowed as an autogenerated column.")
        }
    }

    fun findSequence(props: ColumnMappingProperties): String? {
        return sequenceMappers.map { it.invoke(props) }.filterNotNull().firstOrNull()
    }

    fun determineSequenceColumn(column: AnyColumn): AnyColumn =
        when (column) {
            is LongColumn -> ColumnFactory.SEQUENCE_LONG
            is IntegerColumn -> ColumnFactory.SEQUENCE_INTEGER
            else -> throw CodeGenerationException("${column.javaClass} cannot be used as a sequence-generated primary key: only LongColumn or IntegerColumn are allowed.")
        }

    fun getDefaultMapping(props: ColumnMappingProperties): AnyColumn =
        defaultMapper.map(props)
            ?: throw CodeGenerationException("Unable to find matching datatype for column ${props.schema}.${props.table}.${props.column} of type ${props.jdbcType}. No custom mapping associated.")

    private fun getCustomMapping(props: ColumnMappingProperties): AnyColumn? =
        customMappers.map { it.map(props) }
            .filterNotNull().firstOrNull()

    fun getForeignKeyColumnForType(schema: SchemaName, tableName: TableName, columnMetaData: ColumnMetaData): AnyColumn {
        val column = mapDataType(ColumnMappingProperties.fromMetaData(schema, tableName, columnMetaData))
        return when (column) {
            is IntegerColumn -> ColumnFactory.FOREIGN_KEY_INT
            is NullableIntegerColumn -> ColumnFactory.FOREIGN_KEY_INT_NIL
            is LongColumn -> ColumnFactory.FOREIGN_KEY_LONG
            is NullableLongColumn -> ColumnFactory.FOREIGN_KEY_LONG_NIL
            is VarcharColumn -> ColumnFactory.FOREIGN_KEY_VARCHAR
            is NullableVarcharColumn -> ColumnFactory.FOREIGN_KEY_VARCHAR_NIL
            else -> throw CodeGenerationException("Column type ${column.javaClass.simpleName} cannot be used as a foreign key. It has to be Int, Long or String type")
        }
    }


}
