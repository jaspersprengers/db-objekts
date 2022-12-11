package com.dbobjekts.fixture.h2

import com.dbobjekts.example.Catalogdefinition
import com.dbobjekts.example.core.*
import com.dbobjekts.example.hr.Certificate
import com.dbobjekts.example.hr.Hobby
import com.dbobjekts.fixture.TestDatabaseFacade
import com.dbobjekts.jdbc.Transaction
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceFactory
import javax.sql.DataSource

object H2DB : TestDatabaseFacade() {

    fun <T> newTransaction(fct: (Transaction) -> T) = getTransactionManager().newTransaction(fct)

    fun <T> joinTransaction(fct: (Transaction) -> T) = getTransactionManager().joinTransaction(fct)

    override fun setupDatabaseObjects(forceDelete: Boolean) {
        getTransactionManager().newTransaction { createExampleCatalog(it) }
        if (forceDelete)
            deleteAllTables()
    }

    override fun deleteAllTables() {
        getTransactionManager().newTransaction { tr ->
            tr.deleteFrom(EmployeeAddress).noWhereClause()
            tr.deleteFrom(EmployeeDepartment).noWhereClause()
            tr.deleteFrom(Department).noWhereClause()
            tr.deleteFrom(Address).noWhereClause()
            tr.deleteFrom(Certificate).noWhereClause()
            tr.deleteFrom(Employee).noWhereClause()
            tr.deleteFrom(Country).noWhereClause()
            tr.deleteFrom(Hobby).noWhereClause()
            //tr.deleteFrom(Shape).noWhereClause()
        }
    }

    override fun createDataSource(): DataSource = HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")

    override val catalog: Catalog = Catalogdefinition

    private fun createExampleCatalog(transaction: Transaction) {

        transaction.execute("CREATE SCHEMA if not exists core authorization sa")
        transaction.execute("CREATE SCHEMA if not exists hr authorization sa")
        transaction.execute("CREATE SCHEMA if not exists custom authorization sa")

        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS hr.CERTIFICATE_SEQ START WITH 10")
        transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 10")

        transaction.execute("create table IF NOT EXISTS hr.HOBBY(id varchar(10) primary key, name varchar(50) not null)")
        transaction.execute(
            "create table IF NOT EXISTS core.EMPLOYEE(id INTEGER primary key, name varchar(50) not null, salary double not null, " +
                    "married boolean null, date_of_birth DATE not null, children SMALLINT null, hobby_id varchar(10) null, foreign key(hobby_id) references hr.HOBBY(id))"
        )
        transaction.execute("create table IF NOT EXISTS hr.CERTIFICATE(id BIGINT primary key, name varchar(50) not null, employee_id BIGINT not null, foreign key(employee_id) references core.employee(id))")
        transaction.execute("create table IF NOT EXISTS core.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")

        transaction.execute("create table IF NOT EXISTS core.ADDRESS(id INTEGER primary key, street varchar(50) not null, country_id varchar(10) not null, foreign key(country_id) references core.country(id))")
        transaction.execute("create table if not exists core.EMPLOYEE_ADDRESS(employee_id BIGINT not null, address_id BIGINT not null,kind varchar(10) not null, foreign key(employee_id) references core.employee(id), foreign key(address_id) references core.ADDRESS(id))")
        transaction.execute("create table if not exists core.SHAPE (height DOUBLE, width DOUBLE)")
        transaction.execute("create table IF NOT EXISTS core.DEPARTMENT(id INTEGER primary key, name varchar(50) not null)")
        transaction.execute(
            "create table if not exists core.EMPLOYEE_DEPARTMENT(employee_id BIGINT not null, department_id BIGINT not null, foreign key(employee_id) references core.employee(id), " +
                    "foreign key(department_id) references core.DEPARTMENT(id))"
        )
        transaction.execute(
            "create table IF NOT EXISTS custom.ALL_TYPES(" +
                    "ID INT," +
                    "TINYINT_C TINYINT, " +
                    "SMALLINT_C SMALLINT, " +
                    "INTEGER_C INTEGER," +
                    "INT_C INT, " +
                    "CHAR_C CHAR," +
                    "BLOB_C BLOB," +
                    "CLOB_C CLOB," +
                    "VARCHAR_C VARCHAR(50)," +
                    "BIGINT_C BIGINT, " +
                    "FLOAT_C FLOAT, " +
                    "DOUBLE_C DOUBLE, " +
                    "TIME_C TIME, " +
                    "DATE_C DATE, " +
                    "TIMESTAMP_C TIMESTAMP, " +
                    "TIMESTAMP_TZ_C TIMESTAMP WITH TIME ZONE," +
                    "BOOLEAN_C BOOLEAN, " +
                    "INT_BOOLEAN_C TINYINT)"
        )
        transaction.execute("create table IF NOT EXISTS CUSTOM.TUPLES(c1 INTEGER,c2 INTEGER,c3 INTEGER,c4 INTEGER,c5 INTEGER,c6 INTEGER,c7 INTEGER,c8 INTEGER,c9 INTEGER,c10 INTEGER,c11 INTEGER,c12 INTEGER,c13 INTEGER,c14 INTEGER,c15 INTEGER,c16 INTEGER,c17 INTEGER,c18 INTEGER,c19 INTEGER,c20 INTEGER,c21 INTEGER,c22 INTEGER)")


    }
}
