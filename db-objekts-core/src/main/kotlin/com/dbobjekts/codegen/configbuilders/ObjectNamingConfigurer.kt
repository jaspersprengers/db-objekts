package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.util.ObjectNameValidator


class ObjectNamingConfigurer {

    private val columnOverrides = mutableListOf<ColumnOverride>()
    private val tableOverrides = mutableListOf<TableOverride>()
    private val schemaOverrides = mutableListOf<SchemaOverride>()

    internal fun getColumnName(schema: String, table: String, column: String): ColumnName {
        return columnOverrides.firstOrNull {
            it.schema.equals(schema, true)
                    && it.table.equals(table, true)
                    && it.column.equals(column, true)
        }
            ?.let {
                ColumnName(column, it.fieldName)
            } ?: ColumnName(column)
    }

    internal fun getTableName(schema: String, table: String): TableName {
        return tableOverrides.firstOrNull {
            it.schema.equals(schema, true)
                    && it.table.equals(table, true)
        }
            ?.let {
                TableName(table, it.objectName)
            } ?: TableName(table)
    }

    internal fun getSchemaName(schema: String): SchemaName {
        return schemaOverrides.firstOrNull {
            it.schema.equals(schema, true)
        }
            ?.let {
                SchemaName(schema, it.objectName)
            } ?: SchemaName(schema)
    }

    /**
     * Sets an optional override for the field name to be used for a table column in the generated Table metadata object.
     *
     * Normally the column name is converted to lower case, with underscores converted to camel case.
     *
     * @param schema the schema name, e.g. core
     * @param table the table name, e.g. employee
     * @param column the column name, e.g. dob
     * @param fieldName a valid Java method name, e.g. dateOfBirth. Initial character will be lower-cased
     */
    fun setFieldNameForColumn(schema: String, table: String, column: String, fieldName: String): ObjectNamingConfigurer {
        ObjectNameValidator.validate(fieldName, "$fieldName cannot be used as an override for column '$schema.$table.$column'.")
        columnOverrides.add(ColumnOverride(schema, table, column, fieldName))
        return this
    }

    /**
     * Sets an optional override for the name to be used for a generated Table metadata object.
     *
     * Normally the name is converted to lower case, with underscores converted to camel case.
     *
     * @param schema the schema name, e.g. core
     * @param table the table name, e.g. emp
     * @param objectName a valid Java method name, e.g. Employee. Initial character will be upper-cased
     */
    fun setObjectNameForTable(schema: String, table: String, objectName: String): ObjectNamingConfigurer {
        ObjectNameValidator.validate(objectName, "$objectName cannot be used as an override for table '$schema.$table'.")
        tableOverrides.add(TableOverride(schema, table, objectName))
        return this
    }

    /**
     * Sets an optional override for the name to be used for a generated Schema metadata object.
     *
     * Normally the name is converted to lower case, with underscores converted to camel case.
     *
     * @param schema the schema name, e.g. core
     * @param objectName a valid Java method name, e.g. CoreSchema. Initial character will be upper-cased
     */
    fun setObjectNameForSchema(schema: String, objectName: String): ObjectNamingConfigurer {
        ObjectNameValidator.validate(objectName, "$objectName cannot be used as an override for schema '$schema'.")
        schemaOverrides.add(SchemaOverride(schema, objectName))
        return this
    }
}

internal data class SchemaOverride(val schema: String, val objectName: String)
internal data class ColumnOverride(val schema: String, val table: String, val column: String, val fieldName: String)
internal data class TableOverride(val schema: String, val table: String, val objectName: String)
