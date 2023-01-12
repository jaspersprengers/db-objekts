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
                select u.TABLE_SCHEMA,
               u.TABLE_NAME,
               u.COLUMN_NAME,
               u.REFERENCED_TABLE_SCHEMA,
               u.REFERENCED_TABLE_NAME,
               u.REFERENCED_COLUMN_NAME
                from information_schema.KEY_COLUMN_USAGE u
                 join (select count(1) as c, u2.CONSTRAINT_NAME as ctr
                       from information_schema.KEY_COLUMN_USAGE u2
                       where u2.REFERENCED_TABLE_NAME is not null
                       group by u2.CONSTRAINT_NAME
                       having c = 1) as j on j.ctr = u.CONSTRAINT_NAME
                    where REFERENCED_TABLE_NAME is not null
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

