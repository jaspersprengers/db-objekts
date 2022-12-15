package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import org.slf4j.LoggerFactory


class DatabaseSourceConfigurer {

    private val logger = LoggerFactory.getLogger(DatabaseSourceConfigurer::class.java)

    internal val dataSourceConfigurer: DataSourceConfigurer = DataSourceConfigurer()
    internal var vendor: Vendor? = null

    fun vendor(vendorString: String): DatabaseSourceConfigurer {
        return vendor(Vendors.byName(vendorString))
    }

    fun vendor(vendor: Vendor): DatabaseSourceConfigurer {
        this.vendor = vendor
        return this
    }

    fun configureDataSource(): DataSourceConfigurer {
        return dataSourceConfigurer
    }

    internal fun validate() {
        require(vendor != null, { "Setting 'vendor' is mandatory. Choose one of h2, mysql, postgresql" })
        require(dataSourceConfigurer.toDataSourceInfo() != null,
            { "Could not create DataSource to inspect schemas. Make sure you configure url, username and password." })
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

    internal fun isConfigured(): Boolean = url != null

    internal fun toDataSourceInfo(): DataSourceInfo? {
        return if (url != null) DataSourceInfo(user!!, password, url!!, driver)
        else null
    }

}
