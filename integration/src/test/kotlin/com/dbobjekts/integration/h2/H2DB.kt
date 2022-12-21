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

            transaction.execute("create table inventory.author(id BIGINT NOT NULL primary key auto_increment,name varchar(200) NOT NULl,surname varchar(200) NOT NULl,date_of_birth DATE NOT NULL,date_of_death DATE NULL)")

            transaction.execute("create table inventory.book(id BIGINT NOT NULL primary key auto_increment,isbn varchar(20) NOT NULL,title varchar(200) NOT NULL,author_id BIGINT NOT NULL,published DATE NOT NULL,foreign key (author_id) references inventory.author(id))")

            transaction.execute("create table inventory.item(id BIGINT NOT NULL primary key auto_increment,book_id BIGINT NOT NULL,date_acquired DATE NOT NULL,foreign key (book_id) references inventory.book(id))")

            transaction.execute("create table operations.member(id BIGINT NOT NULL primary key auto_increment,name varchar(200) NOT NULl,surname varchar(200) NOT NULl,date_of_birth DATE NOT NULL)")

            transaction.execute("create table operations.loan(id BIGINT NOT NULL primary key auto_increment,item_id BIGINT NOT NULL,member_id BIGINT NOT NULL,date_loaned DATETIME NOT NULL,date_returned DATETIME NULL,foreign key (item_id) references inventory.item(id),foreign key (member_id) references operations.member(id))");

        }
    }
}
