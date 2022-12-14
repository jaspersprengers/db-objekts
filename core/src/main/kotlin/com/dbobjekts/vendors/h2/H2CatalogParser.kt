package com.dbobjekts.vendors.h2

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.parsers.ForeignKeyMetaDataRow
import com.dbobjekts.codegen.parsers.LiveDBParser
import com.dbobjekts.codegen.parsers.TableMetaDataRow
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Columns.VARCHAR
import com.dbobjekts.metadata.Columns.VARCHAR_NIL
import org.slf4j.LoggerFactory

/**
 * Accesses a live database to extract information from all the schemas
 */
class H2CatalogParser(codeGeneratorConfig: CodeGeneratorConfig, transactionManager: TransactionManager, logger: ProgressLogger) : LiveDBParser(codeGeneratorConfig, transactionManager, logger) {

    private val log = LoggerFactory.getLogger(H2CatalogParser::class.java)

    override fun extractColumnAndTableMetaDataFromDB(): List<TableMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
               select c.TABLE_SCHEMA,
               c.TABLE_NAME,
               c.IS_IDENTITY,   
               c.COLUMN_NAME,
               u.COLUMN_NAME, 
               c.IS_NULLABLE,
               c.COLUMN_DEFAULT,
               c.DATA_TYPE
                from information_schema.COLUMNS c
                    left join INFORMATION_SCHEMA.TABLE_CONSTRAINTS co on c.TABLE_SCHEMA = co.TABLE_SCHEMA and c.TABLE_NAME = co.TABLE_NAME and co.CONSTRAINT_TYPE = 'PRIMARY KEY'
                    left join INFORMATION_SCHEMA.KEY_COLUMN_USAGE u on u.CONSTRAINT_NAME = co.CONSTRAINT_NAME and u.TABLE_SCHEMA = co.TABLE_SCHEMA and u.TABLE_NAME = u.TABLE_NAME and u.COLUMN_NAME = c.COLUMN_NAME
                WHERE c.TABLE_SCHEMA != 'INFORMATION_SCHEMA'
                order by c.TABLE_SCHEMA, c.TABLE_NAME, c.ORDINAL_POSITION
            """.trimIndent()

            val rows = it.select(
                sql,
                VARCHAR, // schema
                VARCHAR, // table
                VARCHAR, //IDENTITY Y/N
                VARCHAR, //column name
                VARCHAR_NIL, //same as column name when PK
                VARCHAR, //nullable NO/YES
                VARCHAR_NIL, //default value
                VARCHAR // data type
            ).asList()
            rows.map({tuple ->
                TableMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    autoIncrement = tuple.v3 == "YES",
                    column = tuple.v4,
                    isPrimaryKey = tuple.v5 != null,
                    nullable = tuple.v6 == "YES",
                    defaultValue = tuple.v7,
                    dataType = tuple.v8
                )
            })
        }
    }

    override fun extractForeignKeyMetaDataFromDB(): List<ForeignKeyMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
               select  u.TABLE_SCHEMA, u.TABLE_NAME, kcu.COLUMN_NAME, ccu.TABLE_SCHEMA, ccu.TABLE_NAME, ccu.COLUMN_NAME
                    from information_schema.TABLE_CONSTRAINTS u
                    join INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu on ccu.CONSTRAINT_NAME = u.CONSTRAINT_NAME
                    join INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu on kcu.TABLE_SCHEMA = u.TABLE_SCHEMA and kcu.TABLE_NAME = u.TABLE_NAME and kcu.CONSTRAINT_NAME = u.CONSTRAINT_NAME
                    where u.CONSTRAINT_TYPE = 'FOREIGN KEY'
            """.trimIndent()
            //CORE,EMPLOYEE_ADDRESS,ADDRESS_ID,CORE,ADDRESS,ID
            val rows = it.select(
                sql,
                VARCHAR, // schema
                VARCHAR, // table
                VARCHAR, //referenced table
                VARCHAR, //referenced schema
                VARCHAR, //column name
                VARCHAR, //referenced column name
            ).asList()
            rows.map({tuple ->
                ForeignKeyMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    column = tuple.v3,
                    refSchema = tuple.v4,
                    refTable = tuple.v5,
                    refColumn = tuple.v6
                )
            })
        }
    }

}

