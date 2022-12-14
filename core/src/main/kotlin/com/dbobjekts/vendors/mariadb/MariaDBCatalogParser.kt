package com.dbobjekts.vendors.mariadb

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
class MariaDBCatalogParser(codeGeneratorConfig: CodeGeneratorConfig, transactionManager: TransactionManager, logger: ProgressLogger) :
    LiveDBParser(codeGeneratorConfig, transactionManager, logger) {

    private val log = LoggerFactory.getLogger(MariaDBCatalogParser::class.java)

    override fun extractColumnAndTableMetaDataFromDB(): List<TableMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
                select t.TABLE_SCHEMA,
                       t.TABLE_NAME,
                       c.EXTRA,
                       c.COLUMN_NAME,
                       c.COLUMN_KEY,
                       c.IS_NULLABLE,
                       c.COLUMN_DEFAULT,
                       c.DATA_TYPE
                from information_schema.TABLES t
                         join information_schema.COLUMNS c on c.TABLE_NAME = t.TABLE_NAME and c.TABLE_SCHEMA = t.TABLE_SCHEMA
                where table_type = 'BASE TABLE' AND t.TABLE_SCHEMA NOT IN ('mysql','information_schema','performance_schema','sys')
                order by t.TABLE_SCHEMA, t.TABLE_NAME, c.ORDINAL_POSITION asc
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
            rows.map({ tuple ->
                TableMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    autoIncrement = (tuple.v3 ?: "") == "auto_increment",
                    column = tuple.v4,
                    isPrimaryKey = tuple.v5 == "PRI",
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
                select  u.TABLE_SCHEMA, 
                u.TABLE_NAME,
                u.REFERENCED_TABLE_NAME,
                u.REFERENCED_TABLE_SCHEMA,
                u.COLUMN_NAME,
                u.REFERENCED_COLUMN_NAME
                from information_schema.KEY_COLUMN_USAGE u where REFERENCED_TABLE_NAME is not null
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
            rows.map({ tuple ->
                ForeignKeyMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    refTable = tuple.v3,
                    refSchema = tuple.v4,
                    column = tuple.v5,
                    refColumn = tuple.v6
                )
            })
        }
    }

}

