package com.dbobjekts.codegen.parsers.h2

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
               c.IS_IDENTITY, -- is an identity auto increment column      
               c.COLUMN_NAME,
               u.COLUMN_NAME,
               c.IS_NULLABLE,
               c.COLUMN_DEFAULT,
               c.DATA_TYPE
                from information_schema.COLUMNS c
                    left join INFORMATION_SCHEMA.TABLE_CONSTRAINTS co on c.TABLE_SCHEMA = co.TABLE_SCHEMA and c.TABLE_NAME = co.TABLE_NAME and co.CONSTRAINT_TYPE = 'PRIMARY KEY'
                    left join INFORMATION_SCHEMA.KEY_COLUMN_USAGE u on u.CONSTRAINT_NAME = co.CONSTRAINT_NAME and u.TABLE_SCHEMA = co.TABLE_SCHEMA and u.TABLE_NAME = u.TABLE_NAME and u.COLUMN_NAME = c.COLUMN_NAME
                WHERE c.TABLE_SCHEMA in ('CORE','HR')
                order by c.TABLE_SCHEMA, c.TABLE_NAME, c.ORDINAL_POSITION
            """.trimIndent()

            val rows = it.select(
                sql,
                VARCHAR, // schema
                VARCHAR, // table
                VARCHAR, //look for auto_increment
                VARCHAR, //column name
                VARCHAR_NIL, //column key
                VARCHAR, //nullable NO/YES
                VARCHAR_NIL, //default value
                VARCHAR // data type
            ).asList()
            rows.map({
                TableMetaDataRow.parse(it)
            })
        }
    }

    override fun extractForeignKeyMetaDataFromDB(): List<ForeignKeyMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
               select  u.TABLE_SCHEMA, u.TABLE_NAME, ccu.TABLE_SCHEMA, ccu.TABLE_NAME, kcu.COLUMN_NAME, ccu.COLUMN_NAME
                    from information_schema.TABLE_CONSTRAINTS u
                    join INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu on ccu.CONSTRAINT_NAME = u.CONSTRAINT_NAME
                    join INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu on kcu.TABLE_SCHEMA = u.TABLE_SCHEMA and kcu.TABLE_NAME = u.TABLE_NAME and kcu.CONSTRAINT_NAME = u.CONSTRAINT_NAME
                    where u.CONSTRAINT_TYPE = 'FOREIGN KEY'
            """.trimIndent()

            val rows = it.select(
                sql,
                VARCHAR, // schema
                VARCHAR, // table
                VARCHAR, //referenced table
                VARCHAR, //referenced schema
                VARCHAR, //column name
                VARCHAR, //referenced column name
            ).asList()
            rows.map({
                ForeignKeyMetaDataRow.parse(it)
            })
        }
    }

}

