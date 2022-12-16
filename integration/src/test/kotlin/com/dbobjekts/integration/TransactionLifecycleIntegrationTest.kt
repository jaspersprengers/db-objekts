package com.dbobjekts.integration

import com.dbobjekts.api.Transaction
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.jdbc.TransactionManagerImpl
import com.dbobjekts.util.HikariDataSourceFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate


class TransactionLifecycleIntegrationTest {
    val employee = Employee

    val transactionManager = TransactionManagerImpl.builder()
        .dataSource(HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver"))
        .catalog(H2DB.catalog)
        .autoCommit(false)
        .customLogger(H2DB.statementLogger)
        .build()

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            H2DB.setupDatabaseObjects()
        }
    }

    @Test
    fun validateRowExists(
        transaction: Transaction,
        shouldOccur: Boolean
    ) {
        val ret = transaction.select(employee.name).where(employee.name.eq("Jared")).asList()
        assertEquals(if (shouldOccur) 1 else 0, ret.size)
    }

    @Test
    fun insertrow() {
        transactionManager.newTransaction { tr ->
            tr.insert(employee).name("Jared").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
        }
    }

    @Test
    @AfterEach
    fun afterEachTest() {
        transactionManager.newTransaction { tr ->
            tr.deleteFrom(employee).where(employee.name.eq("Jared"))
        }
    }


}

