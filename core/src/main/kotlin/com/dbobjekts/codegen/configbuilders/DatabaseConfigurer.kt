package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import org.slf4j.LoggerFactory
import javax.sql.DataSource


class DatabaseConfigurer {

    private val logger = LoggerFactory.getLogger(DatabaseConfigurer::class.java)

    internal val dataSourceConfigurer: DataSourceConfigurer = DataSourceConfigurer()
    internal var vendor: Vendor? = null
    internal var ds: DataSource? = null

    fun vendor(vendorString: String): DatabaseConfigurer {
        return vendor(Vendors.byName(vendorString))
    }

    fun vendor(vendor: Vendor): DatabaseConfigurer {
        this.vendor = vendor
        return this
    }

    fun withDataSource(datasource: DataSource): DataSourceConfigurer {
        this.ds = datasource
        return dataSourceConfigurer
    }

    fun configureDataSource(): DataSourceConfigurer {
        return dataSourceConfigurer
    }

    internal fun getDataSource(): DataSource {
        require(vendor != null, { "Setting 'vendor' is mandatory. Choose one of h2, mysql, postgresql" })
        val configuredDs = dataSourceConfigurer.toDataSourceInfo()
        if (configuredDs != null && ds != null) {
            throw IllegalStateException("You have provided an existing DataSource as well as configured a new one. Only one strategy is necessary.")
        }
        return ds ?: configuredDs ?: throw IllegalStateException("Could not create DataSource to inspect schemas.Make sure you configure url, username and password.")
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

    /**
     * Optional setting. Use it when JDBC cannot automatically resolve the appropriate driver from the URL.
     */
    fun driverClassName(d: String): DataSourceConfigurer {
        this.driver = d
        return this
    }

    internal fun toDataSourceInfo(): DataSource? {
        return if (url == null || user == null) null
        else HikariDataSourceFactory.create(
            url = url!!,
            username = user!!,
            password = password,
            driver = driver
        )
    }


}
