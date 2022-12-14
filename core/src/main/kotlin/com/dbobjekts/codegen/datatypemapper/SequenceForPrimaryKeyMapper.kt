package com.dbobjekts.codegen.datatypemapper

class SequenceForPrimaryKeyMapper(
    val schema: String,
    val table: String,
    val column: String,
    val sequence: String
) {

    operator fun invoke(properties: ColumnMappingProperties): String? {
        return if (schema.equals(properties.schema.value, true)
            && table.equals(properties.schema.value, true)
            && column.equals(properties.column.value, true)
        )
            sequence else null
    }


}
