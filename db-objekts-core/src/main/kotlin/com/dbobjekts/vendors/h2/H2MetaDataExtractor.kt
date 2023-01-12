package com.dbobjekts.vendors.h2

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.api.Tuple4
import com.dbobjekts.api.Tuple8
import com.dbobjekts.codegen.parsers.*
import org.slf4j.LoggerFactory

/**
 * Accesses a live database to extract information from all the schemas
 */
object H2MetaDataExtractor : VendorSpecificMetaDataExtractor {

    private val log = LoggerFactory.getLogger(H2MetaDataExtractor::class.java)

    override fun extractColumnAndTableMetaDataFromDB(transactionManager: TransactionManager): List<TableMetaDataRow> {
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

            val rows: List<Tuple8<String, String, String, String, String?, String, String?, String>> = it.sql(sql)
                .withResultTypes()
                .string().string().string().string().stringNil().string().stringNil().string()
                .asList()

            rows.map({ tuple ->
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

    override fun extractForeignKeyMetaDataFromDB(transactionManager: TransactionManager): List<ForeignKeyMetaDataRow> {
        return transactionManager.newTransaction { tr ->
            val sql = """
               select tc.CONSTRAINT_NAME, kcu.TABLE_SCHEMA, kcu.TABLE_NAME,kcu.COLUMN_NAME 
                from INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
                join INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu on kcu.CONSTRAINT_NAME = tc.CONSTRAINT_NAME and 
                  tc.TABLE_SCHEMA = kcu.TABLE_SCHEMA and tc.TABLE_NAME = tc.TABLE_NAME
                where tc.CONSTRAINT_TYPE = 'FOREIGN KEY'
            """.trimIndent()
            //CONSTRAINT_9C,CORE,EMPLOYEE_ADDRESS,ADDRESS_ID
            val rows: List<Tuple4<String, String, String, String>> =
                tr.sql(sql).withResultTypes().string().string().string().string().asList()

            rows.map { (constraint, schema, table, column) ->
                val ids = tr.sql(
                    """select ccu.TABLE_SCHEMA, ccu.TABLE_NAME, ccu.COLUMN_NAME 
                    from INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu                     
                    where ccu.CONSTRAINT_NAME = ? and ccu.TABLE_NAME != ?""", constraint, table
                )
                    .withResultTypes().string().string().string().asList()
                if (ids.size != 1) null else {
                    val (refSchema, refTable, refColumn) = ids[0]
                    ForeignKeyMetaDataRow(
                        schema = schema,
                        table = table,
                        column = column,
                        refSchema = refSchema,
                        refTable = refTable,
                        refColumn = refColumn
                    )
                }
            }.filterNotNull()
        }
    }

}

