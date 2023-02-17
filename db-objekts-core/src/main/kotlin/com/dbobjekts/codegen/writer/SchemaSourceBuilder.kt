package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.metadata.DBSchemaDefinition
import com.dbobjekts.util.StringUtil

class SchemaSourceBuilder(val schema: DBSchemaDefinition) {
    private val strBuilder = StringBuilder()

    fun buildForApplication(): String {
        strBuilder.append("package ${schema.packageName}\n")
        strBuilder.append("import com.dbobjekts.metadata.Schema\n")
        val tables = schema.tables.map { it.asClassName() }.joinToString(", ")

        val fields = schema.tables.map { """
    /** 
     * Refers to metadata object for table ${it.tableName.value} 
     */
    val ${StringUtil.initLowerCase(it.asClassName())} = ${it.asClassName()}
        """ }.joinToString("")



        val source = """
/**            
 * Auto-generated metadata object representing a schema consisting of one or more tables.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.             
*/
object ${schema.schemaName.metaDataObjectName} : Schema("${schema.schemaName.value}", listOf($tables)){
$fields
}
      """
        strBuilder.append(source)
        return strBuilder.toString()
    }


}
