package com.dbobjekts.integration

import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.jdbc.Transaction
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.util.HikariDataSourceFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate


class TransactionLifecycleIntegrationTest {
    val employee = Employee

    val transactionManager = TransactionManager.builder()
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


    fun validateRowExists(
        transaction: Transaction,
        shouldOccur: Boolean
    ) {
        val ret = transaction.select(employee.name).where(employee.name.eq("Jared")).asList()
        assertEquals(if (shouldOccur) 1 else 0, ret.size)
    }

    fun insertrow() {
        transactionManager.joinTransaction { tr ->
            tr.insert(employee).name("Jared").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
        }
    }

    @AfterEach
    fun afterEachTest() {
        transactionManager.newTransaction { tr ->
            tr.deleteFrom(employee).where(employee.name.eq("Jared"))
        }
    }

    @Test
    fun `Test separate transactions with explicit commit`() {
        insertrow()
        //a separate transaction cannot see this yet
        transactionManager.newTransaction { tr -> validateRowExists(tr, false) }
        //but this one can
        transactionManager.joinTransaction { tr -> validateRowExists(tr, true) }
        transactionManager.commit()
        transactionManager.newTransaction { tr -> validateRowExists(tr, true) }
    }

    @Test
    fun `Test separate transactions with explicit rollback`() {
        insertrow()
        //a separate transaction cannot see this yet
        transactionManager.newTransaction { tr -> validateRowExists(tr, false) }
        //but this one can
        transactionManager.joinTransaction { tr -> validateRowExists(tr, true) }
        transactionManager.rollback()
        transactionManager.newTransaction { tr -> validateRowExists(tr, false) }
    }

    @Test
    fun `Different threads use different transactions`() {
        var transation1Hash: Int = 0
        var transation2Hash: Int = 0
        var transation3Hash: Int = 0
        val t1 = Runnable {
            transactionManager.joinTransaction { tr ->
                transation1Hash = tr.hashCode()
                tr.insert(employee).name("Jared").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
            }
        }
        val t2 = Runnable {
            Thread.sleep(50)
            transactionManager.joinTransaction { tr ->
                transation2Hash = tr.hashCode()
                tr.insert(employee).name("Greg").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
            }
            Thread.sleep(50)
            transactionManager.joinTransaction { tr ->
                transation3Hash = tr.hashCode()
                validateRowExists(tr, true)
            }
        }
        Thread(t1).start()
        Thread(t2).start()
        Thread.sleep(300)
        assertNotEquals(transation1Hash, transation2Hash)
        assertEquals(transation2Hash, transation3Hash)

    }

}

