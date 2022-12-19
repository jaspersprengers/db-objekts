package com.dbobjekts.integration.h2

import com.dbobjekts.api.Transaction
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.integration.h2.core.*
import com.dbobjekts.integration.h2.hr.Certificate
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.util.StatementLogger
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object H2DB {
    private val logger = LoggerFactory.getLogger(H2DB::class.java)

    fun <T> newTransaction(fct: (Transaction) -> T) = getTransactionManager().newTransaction(fct)

    fun setupDatabaseObjects() {
        getTransactionManager().newTransaction { createExampleCatalog(it) }
        deleteAllTables()
    }

    fun deleteAllTables() {
        getTransactionManager().newTransaction { tr ->
            tr.deleteFrom(EmployeeAddress).where()
            tr.deleteFrom(EmployeeDepartment).where()
            tr.deleteFrom(Department).where()
            tr.deleteFrom(Address).where()
            tr.deleteFrom(Certificate).where()
            tr.deleteFrom(Employee).where()
            tr.deleteFrom(Country).where()
            tr.deleteFrom(Hobby).where()
            //tr.deleteFrom(Shape).where()
        }
    }

    fun createDataSource(): DataSource =
        HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")

    val catalog: Catalog = Catalogdefinition

    fun getTransactionManager(autoCommit: Boolean = true): TransactionManager {
        TransactionManager.builder()
            .dataSource(dataSource = createDataSource())
            .catalog(catalog)
            .customLogger(StatementLogger())
            .autoCommit(autoCommit)
            .buildForSingleton()
        return TransactionManager.singletonInstance()
    }

    private fun createExampleCatalog(transaction: Transaction) {

        transaction.execute("CREATE SCHEMA if not exists core")

        transaction.execute("CREATE SCHEMA if not exists hr")

        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS hr.CERTIFICATE_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 10")

        transaction.execute("create table IF NOT EXISTS hr.HOBBY(id varchar(10) primary key, name varchar(50) not null)")
        transaction.execute(
            "create table IF NOT EXISTS core.EMPLOYEE(id BIGINT primary key, name varchar(50) not null, salary double not null, " +
                    "married boolean null, date_of_birth DATE not null, children SMALLINT null, hobby_id varchar(10) null, foreign key(hobby_id) references hr.HOBBY(id))"
        )
        transaction.execute("create table IF NOT EXISTS hr.CERTIFICATE(id BIGINT primary key, name varchar(50) not null, employee_id BIGINT not null, foreign key(employee_id) references core.employee(id))")
        transaction.execute("create table IF NOT EXISTS core.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")

        transaction.execute("create table IF NOT EXISTS core.ADDRESS(id BIGINT primary key, street varchar(50) not null, country_id varchar(10) not null, foreign key(country_id) references core.country(id))")
        transaction.execute("create table if not exists core.EMPLOYEE_ADDRESS(employee_id BIGINT not null, address_id BIGINT not null,kind varchar(10) not null, foreign key(employee_id) references core.employee(id), foreign key(address_id) references core.ADDRESS(id))")
        transaction.execute("create table if not exists core.SHAPE (height DOUBLE, width DOUBLE)")
        transaction.execute("create table IF NOT EXISTS core.DEPARTMENT(id BIGINT primary key, name varchar(50) not null)")
        transaction.execute(
            "create table if not exists core.EMPLOYEE_DEPARTMENT(employee_id BIGINT not null, department_id BIGINT not null, foreign key(employee_id) references core.employee(id), " +
                    "foreign key(department_id) references core.DEPARTMENT(id))"
        )

        val allTypesSql = """
            create table IF NOT EXISTS core.ALL_TYPES
            (
                id                            BIGINT not null primary key auto_increment,
                character_col                 CHARACTER                   NOT NULL,
                character_col_nil             CHARACTER                   NULL,
                charactervarying_col          CHARACTER VARYING           NOT NULL,
                charactervarying_col_nil      CHARACTER VARYING           NULL,
                characterlargeobject_col      CHARACTER LARGE OBJECT      NOT NULL,
                characterlargeobject_col_nil  CHARACTER LARGE OBJECT      NULL,
                varchar_ignorecase_col        VARCHAR_IGNORECASE          NOT NULL,
                varchar_ignorecase_col_nil    VARCHAR_IGNORECASE          NULL,
                enum_col                      ENUM ('yes', 'no', 'maybe') NOT NULL,
                enum_col_nil                  ENUM ('yes', 'no', 'maybe') NULL,
                binary_col                    BINARY(1024)                NOT NULL,
                binary_col_nil                BINARY(1024)                NULL,
                binaryvarying_col             BINARY VARYING(1024)        NOT NULL,
                binaryvarying_col_nil         BINARY VARYING(1024)        NULL,
                binarylargeobject_col         BINARY LARGE OBJECT         NOT NULL,
                binarylargeobject_col_nil     BINARY LARGE OBJECT         NULL,
                json_col                      JSON                        NOT NULL,
                json_col_nil                  JSON                        NULL,
                boolean_col                   BOOLEAN                     NOT NULL,
                boolean_col_nil               BOOLEAN                     NULL,
                tinyint_col                   TINYINT                     NOT NULL,
                tinyint_col_nil               TINYINT                     NULL,
                smallint_col                  SMALLINT                    NOT NULL,
                smallint_col_nil              SMALLINT                    NULL,
                integer_col                   INTEGER                     NOT NULL,
                integer_col_nil               INTEGER                     NULL,
                bigint_col                    BIGINT                      NOT NULL,
                bigint_col_nil                BIGINT                      NULL,
                numeric_col                   NUMERIC                     NOT NULL,
                numeric_col_nil               NUMERIC                     NULL,
                decfloat_col                  DECFLOAT                    NOT NULL,
                decfloat_col_nil              DECFLOAT                    NULL,
                real_col                      REAL                        NOT NULL,
                real_col_nil                  REAL                        NULL,
                doubleprecision_col           DOUBLE PRECISION            NOT NULL,
                doubleprecision_col_nil       DOUBLE PRECISION            NULL,
                date_col                      DATE                        NOT NULL,
                date_col_nil                  DATE                        NULL,
                time_col                      TIME                        NOT NULL,
                time_col_nil                  TIME                        NULL,
                timewithtimezone_col          TIME WITH TIME ZONE         NOT NULL,
                timewithtimezone_col_nil      TIME WITH TIME ZONE         NULL,
                timestamp_col                 TIMESTAMP                   NOT NULL,
                timestamp_col_nil             TIMESTAMP                   NULL,
                timestampwithtimezone_col     TIMESTAMP WITH TIME ZONE    NOT NULL,
                timestampwithtimezone_col_nil TIMESTAMP WITH TIME ZONE    NULL,
                uuid_col                      UUID                        NOT NULL,                             
                uuid_col_nil                  UUID                        NULL,
                interval_col                  INTERVAL MONTH                        NOT NULL,
                interval_col_nil              INTERVAL MONTH                     NULL,
                geometry_col_nil              GEOMETRY NULL,
                int_array_col                 INTEGER ARRAY[10] NOT NULL,
                int_array_col_nil             INTEGER ARRAY[10] NULL,
                address_int                   SMALLINT NOT NULL,
                address_int_nil               SMALLINT NULL,
                address_string                VARCHAR(10) not null,
                address_string_nil            VARCHAR(10) null
            );
        """.trimIndent()

        transaction.execute(allTypesSql)
        transaction.execute("create table IF NOT EXISTS core.TUPLES(c1 INTEGER,c2 INTEGER,c3 INTEGER,c4 INTEGER,c5 INTEGER,c6 INTEGER,c7 INTEGER,c8 INTEGER,c9 INTEGER,c10 INTEGER,c11 INTEGER,c12 INTEGER,c13 INTEGER,c14 INTEGER,c15 INTEGER,c16 INTEGER,c17 INTEGER,c18 INTEGER,c19 INTEGER,c20 INTEGER,c21 INTEGER,c22 INTEGER)")
    }
}