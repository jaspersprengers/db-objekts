package com.dbobjekts.fixture

import com.dbobjekts.jdbc.SingletonTransactionManager
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Catalog
import org.slf4j.LoggerFactory
import javax.sql.DataSource

abstract class DatabaseFacade {

    private val logger = LoggerFactory.getLogger(DatabaseFacade::class.java)
    val queryLogger = SqlLogger()

    fun getTransactionManager(autoCommit: Boolean = true): TransactionManager {
        if (!SingletonTransactionManager.isConfigured())
            createTransactionManager(autoCommit)
        return SingletonTransactionManager.instance()!!
    }


    abstract fun setupDatabaseObjects(forceDelete: Boolean = true)

    abstract fun deleteAllTables()

    abstract fun dataSource(): DataSource

    abstract val catalog: Catalog

    fun createTransactionManager(autoCommit: Boolean = true) {
        if (!SingletonTransactionManager.isConfigured() && !createFactory(autoCommit, 0))
            throw IllegalStateException("Could not connect")
    }

    private fun createFactory(
        autoCommit: Boolean,
        attempt: Int
    ): Boolean {
        if (attempt == 3)
            return false
        Thread.sleep(2000)
        try {
            SingletonTransactionManager.configurer()
                .catalog(catalog)
                .autoCommit(autoCommit)
                .customLogger(queryLogger)
                .dataSource(dataSource())
                .initialize()
            return true
        } catch (e: Exception) {
            logger.warn("Cannot connect: {}", e.message)
            return createFactory(autoCommit, attempt + 1)
        }
    }

}
