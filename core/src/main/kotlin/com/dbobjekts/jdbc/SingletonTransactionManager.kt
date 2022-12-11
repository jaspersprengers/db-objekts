package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.util.SLF4JQueryLogger
import org.slf4j.LoggerFactory

object SingletonTransactionManager {

    private var _instance: TransactionManager? = null
    private val logger = LoggerFactory.getLogger("GlobalTransactionManager")

     fun initialize(
         dataSource: DataSourceAdapter,
         catalog: Catalog,
         querySettings: TransactionSettings = TransactionSettings(),
         queryLogger: QueryLogger = SLF4JQueryLogger()
    ): Boolean {
        if (_instance != null)
            throw IllegalStateException("The SingletonTransactionManager has already been initialized. You must call invalidate() first to close any open connections before you can call initialize() again.")
        val tr = TransactionManager(dataSource, catalog, querySettings, queryLogger)
        return tr.test().also { _instance = tr }
    }

    fun instance(): TransactionManager? = _instance

    fun <T> newTransaction(fct: (Transaction) -> T): T = ensure().newTransaction(fct)

    fun <T> joinTransaction(fct: (Transaction) -> T): T = ensure().joinTransaction(fct)

    fun commit() = ensure().commit()

    fun rollback() = ensure().rollback()

    fun isConfigured(): Boolean = _instance != null

    fun querySettings(): TransactionSettings = ensure().transactionSettings

    fun queryLogger(): QueryLogger = ensure().queryLogger

    fun invalidate() {
        _instance?.close()
        _instance = null
    }

    private fun ensure(): TransactionManager = _instance ?: throw IllegalStateException(
        "Singleton TransactionManager has not been initialized yet or was invalidated. " +
            "You need to call TransactionManager.builder() and finish with a call to configureSingleton()"
    )

    fun configurer(): SingletonTransactionManagerBuilder = SingletonTransactionManagerBuilder

}
