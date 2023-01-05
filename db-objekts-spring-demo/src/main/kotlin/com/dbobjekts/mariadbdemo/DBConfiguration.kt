package com.dbobjekts.mariadbdemo

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.mariadb.testdb.CatalogDefinition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DBConfiguration {

    /**
     * WARN: Don't call the method transactionManager(). This will conflict with the one in org.springframework.transaction
     */
    @Bean()
    fun dbObjektsTransactionManager(dataSource: DataSource): TransactionManager {
        return TransactionManager.builder().withDataSource(dataSource).withCatalog(CatalogDefinition).build()
    }

}
