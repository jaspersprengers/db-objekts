package com.dbobjekts.vendors.postgresql

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.parsers.*

/**
 * Accesses a live database to extract information from all the schemas
 */
object PostgreSQLMetaDataExtractor : VendorSpecificMetaDataExtractor {

    override fun extractColumnAndTableMetaDataFromDB(transactionManager: TransactionManager): List<TableMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
                select t.TABLE_SCHEMA,
                       t.TABLE_NAME,
                       c.is_identity,
                       c.column_name,
                       ccu.column_name,
                       c.is_nullable,
                       c.udt_name,
                       c.column_default
                from information_schema.TABLES t
                         join information_schema.COLUMNS c on c.TABLE_NAME = t.TABLE_NAME and c.TABLE_SCHEMA = t.TABLE_SCHEMA
                         left join information_schema.table_constraints tc on tc.table_schema = t.table_schema and tc.table_name = t.table_name and tc.constraint_type = 'PRIMARY KEY'
                         left join information_schema.constraint_column_usage ccu on ccu.constraint_name = tc.constraint_name and ccu.table_name = c.table_name and ccu.column_name = c.column_name
                where table_type = 'BASE TABLE' AND t.TABLE_SCHEMA NOT IN ('pg_catalog', 'information_schema')
                order by t.TABLE_SCHEMA, t.TABLE_NAME, c.ORDINAL_POSITION
            """.trimIndent()

            val rows = it.sql(sql).withResultTypes()
                .string()//schema
                .string()//table
                .string()//identity ?
                .string()//column name
                .stringNil()//primary key
                .string()//nullable?
                .string()// data type
                .stringNil()//column default
                .asList()
            rows.map({ tuple ->
                TableMetaDataRow(
                    schema = tuple.v1,
                    table = tuple.v2,
                    autoIncrement = (tuple.v3 == "YES") || (tuple.v8?.startsWith("nextval") ?: false),
                    column = tuple.v4,
                    isPrimaryKey = tuple.v5 != null,
                    nullable = tuple.v6 == "YES",
                    defaultValue = null,
                    dataType = tuple.v7
                )
            })
        }
    }

    override fun extractForeignKeyMetaDataFromDB(transactionManager: TransactionManager): List<ForeignKeyMetaDataRow> {
        return transactionManager.newTransaction {
            val sql = """
                select kcu.table_schema, kcu.table_name, kcu.column_name, ccu.table_schema,ccu.table_name, ccu.column_name
                from information_schema.table_constraints tc
                  join information_schema.constraint_column_usage ccu on ccu.constraint_name = tc.constraint_name
                  join information_schema.key_column_usage kcu on kcu.constraint_name = tc.constraint_name
                where
                    tc.constraint_type = 'FOREIGN KEY' and
                    tc.table_schema NOT IN ('pg_catalog', 'information_schema')
            """.trimIndent()

            val rows = it.sql(sql).withResultTypes().string().string().string().string().string().string().asList()
            rows.map({ tuple ->
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

