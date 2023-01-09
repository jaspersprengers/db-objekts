package com.dbobjekts.component

import com.dbobjekts.api.Transaction
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Certificate
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.util.HikariDataSourceFactory
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object AcmeDB {
    private val logger = LoggerFactory.getLogger(AcmeDB::class.java)

    val dataSource: DataSource

    val transactionManager: TransactionManager
    fun <T> newTransaction(fct: (Transaction) -> T) = transactionManager.newTransaction(fct)

    init {
        dataSource =
            HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")
        transactionManager = TransactionManager.builder().withDataSource(dataSource).withCatalog(CatalogDefinition).build()
        createExampleCatalog(transactionManager)
    }


    fun createExampleCatalog(tm: TransactionManager) {
        tm.newTransaction { tr ->
            tr.execute("CREATE SCHEMA if not exists core")
            tr.execute("CREATE SCHEMA if not exists hr")
            //deliberaty use a different sequence pattern
            tr.execute("CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQUENCE START WITH 100")
            tr.execute("CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 100")
            tr.execute("CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 100")

            tr.execute("create table IF NOT EXISTS hr.HOBBY(id varchar(10) primary key, name varchar(50) not null)")
            tr.execute(
                "create table IF NOT EXISTS core.EMPLOYEE(id BIGINT primary key, name varchar(50) not null, salary double not null, " +
                        "married boolean null, date_of_birth DATE not null, children SMALLINT null, hobby_id varchar(10) null," +
                        "foreign key(hobby_id) references hr.HOBBY(id))"
            )
            tr.execute("create table IF NOT EXISTS hr.CERTIFICATE(id BIGINT primary key auto_increment, name varchar(50) not null, employee_id BIGINT not null, foreign key(employee_id) references core.employee(id) on DELETE CASCADE)")
            tr.execute("create table IF NOT EXISTS core.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")

            tr.execute("create table IF NOT EXISTS core.ADDRESS(id BIGINT primary key, street varchar(50) not null, postcode varchar(10) null, country_id varchar(10) not null, foreign key(country_id) references core.country(id)  on DELETE CASCADE)")
            tr.execute("create table if not exists core.EMPLOYEE_ADDRESS(employee_id BIGINT not null, address_id BIGINT not null,kind varchar(10) not null, primary key (employee_id, address_id), foreign key(employee_id) references core.employee(id)  on DELETE CASCADE, foreign key(address_id) references core.ADDRESS(id)  on DELETE CASCADE)")

            tr.execute("create table IF NOT EXISTS core.DEPARTMENT(id BIGINT primary key, name varchar(50) not null)")
            tr.execute(
                "create table if not exists core.EMPLOYEE_DEPARTMENT(employee_id BIGINT not null, department_id BIGINT not null, primary key (employee_id, department_id), foreign key(employee_id) references core.employee(id)  on DELETE CASCADE, " +
                        "foreign key(department_id) references core.DEPARTMENT(id)  on DELETE CASCADE)"
            )

            tr.deleteFrom(EmployeeAddress).where()
            tr.deleteFrom(EmployeeDepartment).where()
            tr.deleteFrom(Department).where()
            tr.deleteFrom(Address).where()
            tr.deleteFrom(Certificate).where()
            tr.deleteFrom(Employee).where()
            tr.deleteFrom(Country).where()
            tr.deleteFrom(Hobby).where()

            tr.execute("insert into core.COUNTRY (ID, NAME) values ('nl', 'Nederland')")
            tr.execute("insert into core.COUNTRY (ID, NAME) values ('be', 'België')")
            tr.execute("insert into core.COUNTRY (ID, NAME) values ('de', 'Deutschland')")

            tr.execute("insert into core.DEPARTMENT (ID, NAME) values (1, 'Finance')")
            tr.execute("insert into core.DEPARTMENT (ID, NAME) values (2, 'Human Resources')")
            tr.execute("insert into core.DEPARTMENT (ID, NAME) values (3, 'Information Technology')")

            tr.execute("insert into hr.HOBBY (ID, NAME) values ('c', 'Chess')")
            tr.execute("insert into hr.HOBBY (ID, NAME) values ('p', 'Photography')")
            tr.execute("insert into hr.HOBBY (ID, NAME) values ('f', 'Football')")

            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (1, 'Zuidhoek 80', '3015HP', 'nl')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (2, 'Kerkstraat 15', '5941AA', 'nl')")
            //office address
                    tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (3, 'Damrak 3', '1015XX', 'nl')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (4, 'Zuidhoek 80', '3015HP', 'de')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (5, 'Südwal 10', '9998', 'de')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (6, 'Venloerstrase', '1234', 'de')")
            //office address
                    tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (7, 'Am Hof', '2000', 'de')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (8, 'Brusselsesteenweg 5', '12345', 'be')")
            tr.execute("insert into core.ADDRESS (ID, STREET, POSTCODE, COUNTRY_ID) values (9, 'Achelsesteenweg 10', '23456', 'be')")

            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (1, 'Eve', 34000, true, '1990-10-3', 2, 'c')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (2, 'Bob', 32000, true, '1980-5-5', 1, null)")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (3, 'Inez', 35000, true, '1968-8-15', 0, 'p')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (4, 'Diane', 33000, true, '1980-12-12', 2, 'p')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (5, 'Hardeep', 37000, false, '2000-1-27', 0, null)")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (6, 'Alice', 36000, true, '1960-8-8', 5, 'c')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (7, 'Gina', 31000, true, '1983-12-12', 2, 'f')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (8, 'Charlie', 30000, true, '1988-6-16', 3, 'p')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (9, 'Fred', 38000, false, '1996-3-8', 0, 'f')")
            tr.execute("insert into core.EMPLOYEE (ID, NAME, SALARY, MARRIED, DATE_OF_BIRTH, CHILDREN, HOBBY_ID) values (10, 'Jasper', 39000, false, '2000-5-5', 0, 'c')")

            //finance people
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 1,1 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 2,1 )")
            //hr
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 3,2 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 4,2 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 5,2 )")
            //it
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 6,3 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 7,3 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 8,3 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 9,3 )")
            tr.execute("insert into core.EMPLOYEE_DEPARTMENT (EMPLOYEE_ID, DEPARTMENT_ID) values ( 10,3 )")

            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 1, 3 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 2, 3 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 3, 3,'WORK' )")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 4, 3,'WORK' )")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 5, 3 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 6, 7 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 7, 7 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 8, 7 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 9, 7 ,'WORK')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 10, 7 ,'WORK')")

            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 1, 1 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 2, 2 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 3, 4,'HOME' )")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 4, 4,'HOME' )")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 5, 5 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 6, 6 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 7, 8 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 8, 9 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 9, 9 ,'HOME')")
            tr.execute("insert into core.EMPLOYEE_ADDRESS (EMPLOYEE_ID, ADDRESS_ID, KIND) values ( 10, 5 ,'HOME')")

            tr.execute("insert into hr.CERTIFICATE (NAME, EMPLOYEE_ID) values ('PSM1', 6)")
            tr.execute("insert into hr.CERTIFICATE (NAME, EMPLOYEE_ID) values ('PSM2', 6)")
            tr.execute("insert into hr.CERTIFICATE (NAME, EMPLOYEE_ID) values ('PSM1', 8)")
            tr.execute("insert into hr.CERTIFICATE (NAME, EMPLOYEE_ID) values ('Prince 2', 2)")



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

            tr.execute(allTypesSql)

            tr.execute("CREATE SCHEMA if not exists library");

            tr.execute("CREATE SEQUENCE IF NOT EXISTS library.AUTHOR_SEQ START WITH 10")
            tr.execute("CREATE SEQUENCE IF NOT EXISTS library.ITEM_SEQ START WITH 10")
            tr.execute("CREATE SEQUENCE IF NOT EXISTS library.MEMBER_SEQ START WITH 10")

            tr.execute("create table if not exists library.author(id BIGINT NOT NULL primary key,name varchar(200) NOT NULl,bio varchar(1000) NULL)")

            tr.execute("create table if not exists library.book(isbn varchar(20) primary key NOT NULL,title varchar(200) NOT NULL,author_id BIGINT NOT NULL,published DATE NOT NULL,foreign key (author_id) references library.author(id))")

            tr.execute("create table if not exists library.item(id BIGINT NOT NULL primary key,isbn varchar(20) NOT NULL,date_acquired DATE NOT NULL,foreign key (isbn) references library.book(isbn))")

            tr.execute("create table if not exists library.book_review(isbn varchar(20) NOT NULL,review VARCHAR(1000) NOT NULL,foreign key (isbn) references library.book(isbn))")

            tr.execute("create table if not exists library.member(id BIGINT NOT NULL primary key,name varchar(200) NOT NULl)")

            tr.execute(
                "create table if not exists library.loan(item_id BIGINT NOT NULL,member_id BIGINT NOT NULL,date_loaned DATE NOT NULL,date_returned DATE NULL," +
                        "primary key (item_id,member_id,date_loaned), foreign key (item_id) references library.item(id),foreign key (member_id) references library.member(id))"
            );


        }
    }
}
