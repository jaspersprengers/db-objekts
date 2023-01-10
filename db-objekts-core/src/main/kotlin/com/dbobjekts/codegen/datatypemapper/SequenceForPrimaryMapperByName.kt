package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.SequenceForPrimaryKeyResolver

class SequenceForPrimaryMapperByName(
    private val schema: String,
    private  val table: String,
    private  val column: String,
    private  val sequence: String
) : SequenceForPrimaryKeyResolver {

    override operator fun invoke(properties: ColumnMappingProperties): String? {
        return if (schema.equals(properties.schema.value, true)
            && table.equals(properties.table.value, true)
            && column.equals(properties.column.value, true)
        )
            sequence else null
    }
}
