package com.dbobjekts.springdemo

//import com.dbobjekts.mariadb.testdb.CatalogDefinition
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.demo.db.CatalogDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class DBConfiguration {

    /**
     * WARN: Don't call the method transactionManager(). This will conflict with the one in org.springframework.transaction
     */
    @Bean()
    open fun dbObjektsTransactionManager(dataSource: DataSource): TransactionManager {
        return TransactionManager.builder().withDataSource(dataSource).withCatalog(CatalogDefinition)
         .build()
    }

}
