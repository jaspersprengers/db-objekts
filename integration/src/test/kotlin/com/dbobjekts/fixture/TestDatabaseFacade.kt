package com.dbobjekts.fixture

import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.StatementLogger
import org.slf4j.LoggerFactory
import javax.sql.DataSource

abstract class TestDatabaseFacade {

    private val logger = LoggerFactory.getLogger(TestDatabaseFacade::class.java)
    val statementLogger = StatementLogger()

    fun getTransactionManager(autoCommit: Boolean = true): TransactionManager {
        if (!TransactionManager.isConfigured())
            createTransactionManager(autoCommit)
        return TransactionManager.singletonInstance()
    }


    abstract fun setupDatabaseObjects(forceDelete: Boolean = true)

    abstract fun deleteAllTables()

    abstract fun createDataSource(): DataSource

    abstract val catalog: Catalog

    fun createTransactionManager(autoCommit: Boolean = true) {
        if (!TransactionManager.isConfigured() && !createFactory(autoCommit, 0))
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
            TransactionManager.builder()
                .dataSource(dataSource = createDataSource())
                .catalog(catalog)
                .customLogger(statementLogger)
                .autoCommit(autoCommit)
                .buildForSingleton()
            return true
        } catch (e: Exception) {
            logger.warn("Cannot connect: {}", e.message)
            return createFactory(autoCommit, attempt + 1)
        }
    }

}
