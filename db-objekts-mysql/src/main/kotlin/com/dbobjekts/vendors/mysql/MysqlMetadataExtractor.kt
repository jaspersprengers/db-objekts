package com.dbobjekts.vendors.mysql

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.parsers.*

/**
 * Accesses a live database to extract information from all the schemas
 */
object MysqlMetadataExtractor : VendorSpecificMetaDataExtractor {

    override fun extractColumnAndTableMetaDataFromDB(transactionManager: TransactionManager): List<TableMetaDataRow> {
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

            val rows = it.sql(sql).withResultTypes().string().string().string().string().stringNil().string().stringNil().string().asList()
            rows.map({ tuple ->
                TableMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    autoIncrement = (tuple.v3 ) == "auto_increment",
                    column = tuple.v4,
                    isPrimaryKey = tuple.v5 == "PRI",
                    nullable = tuple.v6 == "YES",
                    defaultValue = tuple.v7,
                    dataType = tuple.v8
                )
            })
        }
    }

    override fun extractForeignKeyMetaDataFromDB(transactionManager: TransactionManager): List<ForeignKeyMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
              select  ccu.table_schema,ccu.table_name, ccu.column_name, kcu.table_schema, kcu.table_name, kcu.column_name
                from information_schema.constraint_column_usage ccu
                    join (select  count(1), tc.constraint_name as ctr
                          from information_schema.table_constraints tc
                                   join information_schema.key_column_usage kcu on kcu.constraint_name = tc.constraint_name
                          where
                                  tc.constraint_type = 'FOREIGN KEY' and
                                  tc.table_schema NOT IN ('pg_catalog', 'information_schema')
                          group by tc.constraint_name having count(1) = 1) as j on j.ctr = ccu.constraint_name
                    join information_schema.key_column_usage kcu on kcu.constraint_name = ccu.constraint_name
            """.trimIndent()

            val rows = it.sql(sql).withResultTypes().string().string().string().string().string().string().asList()
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

