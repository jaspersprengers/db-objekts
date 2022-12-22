package com.dbobjekts.jdbc

import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.api.ResultRow
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.util.StatementLogger
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.VendorSpecificProperties
import org.slf4j.LoggerFactory
import java.sql.*

data class ConnectionAdapter(
    internal val jdbcConnection: Connection,
    internal val statementLog: StatementLogger,
    private val _catalog: Catalog,
    val vendor: Vendor
) {

    private val log = LoggerFactory.getLogger(ConnectionAdapter::class.java)

    fun catalog(): Catalog = _catalog

    fun isValid(): Boolean = !this.jdbcConnection.isClosed && this.jdbcConnection.isValid(2000)

    val vendorSpecificProperties: VendorSpecificProperties = vendor.properties

    internal var enforceNullabilityInResults: Boolean = false

    fun close() {
        try {
            jdbcConnection.close()
        } catch (e: SQLException) {
            log.error("Error trying to close connection.", e)
        }
    }

    fun commit() {
        if (!jdbcConnection.isClosed)
            jdbcConnection.commit()
    }

    fun rollback() {
        if (!jdbcConnection.isClosed)
            jdbcConnection.rollback()
    }

    fun prepareAndExecuteUpdate(sql: String, parameters: List<AnySqlParameter>): Long {
        statementLog.logStatement(sql, parameters)
        val statement = jdbcConnection.prepareStatement(sql)
        parameters.forEach { it.setValueOnStatement(statement) }
        return statement.executeUpdate().toLong()
    }

    fun <T : ResultRow<*>> prepareAndExecuteForSelect(
        sql: String,
        parameters: List<AnySqlParameter>,
        columnsToFetch: List<ColumnInResultRow>,
        selectResultSet: T
    ): T {
        val resultSetAdapter = JDBCResultSetAdapter(columnsToFetch, executeSelect(sql, parameters), enforceNullabilityInResults)
        selectResultSet.initialize(resultSetAdapter)
        selectResultSet.retrieveAll()
        return selectResultSet
    }

    fun <T, RS : ResultRow<T>> prepareAndExecuteForSelectWithRowIterator(
        sql: String,
        parameters: List<AnySqlParameter>,
        columnsToFetch: List<ColumnInResultRow>,
        selectResultSet: RS,
        iteratorFunction: (T) -> Boolean
    ) {
        val resultSetAdapter = JDBCResultSetAdapter(columnsToFetch, executeSelect(sql, parameters), enforceNullabilityInResults)
        selectResultSet.initialize(resultSetAdapter)
        resultSetAdapter.retrieveWithIterator(selectResultSet, iteratorFunction)
    }

    private fun executeSelect(
        sql: String,
        params: List<AnySqlParameter>
    ): ResultSet {
        val statement = jdbcConnection.prepareStatement(sql)
        params.forEach { it.setValueOnStatement(statement) }
        log.info(sql)
        statementLog.logStatement(sql, params)
        return statement.executeQuery()
    }

    fun prepareAndExecuteDeleteStatement(sql: String, parameters: List<AnySqlParameter>): Long {
        return prepareAndExecuteUpdate(sql, parameters)
    }

    fun fetchKey(sql: String): Long? {
        var stm: PreparedStatement? = null
        try {
            stm = jdbcConnection.prepareStatement(sql)
            val rs = stm.executeQuery()
            return if (!rs.next()) {
                log.error("Could not retrieve value from sequence. Resultset was empty.")
                null
            } else
                rs.getLong(1).let { if (it == 0L) null else it }
        } finally {
            stm?.close()
        }
    }

    fun executeInsertWithAutoGeneratedKey(sql: String, parameters: List<AnySqlParameter>): Long {
        val statement = jdbcConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        parameters.forEach { it.setValueOnStatement(statement) }
        statement.executeUpdate()
        return getAutoGeneratedKeyFromStatement(statement)
    }

    fun getAutoGeneratedKeyFromStatement(statement: PreparedStatement): Long {
        var genKey: ResultSet? = null
        try {
            genKey = statement.getGeneratedKeys()
            return if (genKey?.next() ?: false) {
                genKey.getLong(1)
            } else {
                throw IllegalStateException("could not retrieve generated key from PreparedStatement")
            }
        } finally {
            genKey?.close()
        }
    }

}
