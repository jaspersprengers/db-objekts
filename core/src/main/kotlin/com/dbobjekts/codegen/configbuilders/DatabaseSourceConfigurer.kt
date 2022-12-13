package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.SchemaName
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import org.slf4j.LoggerFactory
import java.nio.file.Paths


class DatabaseSourceConfigurer {

    private val logger = LoggerFactory.getLogger(DatabaseSourceConfigurer::class.java)

    internal val changeLogFiles: HashMap<SchemaName, String> = hashMapOf()
    internal val dataSourceConfigurer: DataSourceConfigurer = DataSourceConfigurer()
    internal var vendor: Vendor? = null

    fun vendor(vendorString: String): DatabaseSourceConfigurer {
        return vendor(Vendors.byName(vendorString))
    }

    fun vendor(vendor: Vendor): DatabaseSourceConfigurer {
        this.vendor = vendor
        return this
    }

    fun addLiquibaseChangelogFile(schemaName: String, path: String): DatabaseSourceConfigurer {
        changeLogFiles.put(SchemaName(schemaName), path)
        return this
    }

    fun configureDataSource(): DataSourceConfigurer {
        return dataSourceConfigurer
    }

    internal fun getChangeLogFilesPath(): String? = changeLogFiles.values.firstOrNull()?.let {
        Paths.get(it).toFile().getParentFile().getAbsolutePath()
    }

    internal fun validate() {
        require(vendor != null, { "Setting 'vendor' is mandatory. Choose one of h2, mysql, postgresql" })
        require(
            changeLogFiles.isNotEmpty() || dataSourceConfigurer.toDataSourceInfo() != null,
            { "No changelog files configured. Add at least one with conf.addLiquibaseChangelogFile(..)" })
        changeLogFiles.forEach { schema, path ->
            ValidateFile(path)
            logger.info("Using liquibase changelog file: $path for schema $schema")
        }
    }
}

class DataSourceConfigurer() {
    private var user: String? = null

    private var password: String? = null

    private var url: String? = null

    private var driver: String? = null

    fun user(u: String): DataSourceConfigurer {
        this.user = u
        return this
    }

    fun password(p: String): DataSourceConfigurer {
        this.password = p
        return this
    }

    fun url(u: String): DataSourceConfigurer {
        this.url = u
        return this
    }

    fun driverClassName(d: String): DataSourceConfigurer {
        this.driver = d
        return this
    }

    internal fun isConfigured(): Boolean = user != null && password != null && url != null

    internal fun toDataSourceInfo(): DataSourceInfo? {
        return if (isConfigured()) DataSourceInfo(user!!, password!!, url!!, driver)
        else null
    }

}
