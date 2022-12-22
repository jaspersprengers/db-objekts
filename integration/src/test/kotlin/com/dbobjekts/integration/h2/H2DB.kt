package com.dbobjekts.integration.h2

import com.dbobjekts.api.Transaction
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.util.HikariDataSourceFactory
import org.slf4j.LoggerFactory

object H2DB {
    private val logger = LoggerFactory.getLogger(H2DB::class.java)

    val dataSource =
        HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")

    val transactionManager = TransactionManager.builder().withDataSource(dataSource).withCatalog(TestCatalog).build()
    fun <T> newTransaction(fct: (Transaction) -> T) = transactionManager.newTransaction(fct)

    fun setupDatabaseObjects() {
        createExampleCatalog()
        //tables are not created when they exist and may contain data
        deleteAllTables()
    }

    fun deleteAllTables() {
        transactionManager.newTransaction { tr ->

        }
    }

    private fun createExampleCatalog() {
        transactionManager.newTransaction { transaction ->
            transaction.execute("CREATE SCHEMA if not exists inventory");
            transaction.execute("CREATE SCHEMA if not exists operations");

            transaction.execute("create table inventory.author(id BIGINT NOT NULL primary key auto_increment,name varchar(200) NOT NULl,bio varchar(1000) NULL)")

            transaction.execute("create table inventory.book(isbn varchar(20) primary key NOT NULL,title varchar(200) NOT NULL,author_id BIGINT NOT NULL,published DATE NOT NULL,foreign key (author_id) references inventory.author(id))")

            transaction.execute("create table inventory.item(id BIGINT NOT NULL primary key auto_increment,isbn varchar(20) NOT NULL,date_acquired DATE NOT NULL,foreign key (isbn) references inventory.book(isbn))")

            transaction.execute("create table operations.member(id BIGINT NOT NULL primary key auto_increment,name varchar(200) NOT NULl)")

            transaction.execute("create table operations.loan(item_id BIGINT NOT NULL,member_id BIGINT NOT NULL,date_loaned DATE NOT NULL,date_returned DATE NULL," +
                    "foreign key (item_id) references inventory.item(id),foreign key (member_id) references operations.member(id))");

        }
    }
}
