package com.dbobjekts.integration

import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.jdbc.Transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SingletonTransactionLifecycleIntegrationTest {
    val employee = Employee

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            H2DB.createTransactionManager(autoCommit = false)
            H2DB.setupDatabaseObjects()
        }
    }

    fun validateRowExists(
        msg: String,
        transaction: Transaction,
        shouldOccur: Boolean
    ) {
        val ret = transaction.select(employee.name).where(employee.name.eq("Jared")).asList()
        assertEquals(if (shouldOccur) 1 else 0, ret.size, msg)
    }

    fun insertrow() {
        H2DB.joinTransaction { tr ->
            tr.insert(employee).name("Jared").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
        }
    }

    @AfterEach
    fun afterEachTest() {
        H2DB.newTransaction { tr ->
            tr.deleteFrom(employee).where(employee.name.eq("Jared"))
        }
    }

    @Test
    fun `Test separate transactions with explicit commit`() {
        insertrow()
        //a separate transaction cannot see this yet
        H2DB.newTransaction { tr -> validateRowExists("new transaction cannot read uncommitted write", tr, false) }
        //but this one can
        H2DB.joinTransaction { tr -> validateRowExists("same transaction can read", tr, true) }
        H2DB.getTransactionManager().commit()
        H2DB.newTransaction { tr -> validateRowExists("separate transaction can read committed insert", tr, true) }
    }

    @Test
    fun `Test separate transactions with explicit rollback`() {
        insertrow()
        //a separate transaction cannot see this yet
        H2DB.newTransaction { tr -> validateRowExists("new transaction cannot read uncommitted write", tr, false) }
        H2DB.getTransactionManager().rollback()
        H2DB.newTransaction { tr -> validateRowExists("insert has been rolled back", tr, false) }
    }

    @Test
    fun `Different threads use different transactions`() {
        var transation1Hash: Int = 0
        var transation2Hash: Int = 0
        var transation3Hash: Int = 0
        val t1 = Runnable {
            H2DB.joinTransaction { tr ->
                transation1Hash = tr.hashCode()
                tr.insert(employee).name("Jared").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
            }
        }
        val t2 = Runnable {
            Thread.sleep(50)
            H2DB.joinTransaction { tr ->
                transation2Hash = tr.hashCode()
                tr.insert(employee).name("Greg").salary(2000.0).dateOfBirth(LocalDate.of(1980, 12, 12)).execute()
            }
            Thread.sleep(50)
            H2DB.joinTransaction { tr ->
                transation3Hash = tr.hashCode()
                validateRowExists("separate transaction can read insert", tr, true)
            }
        }
        Thread(t1).start()
        Thread(t2).start()
        Thread.sleep(300)
        assertNotEquals(transation1Hash, transation2Hash)
        assertEquals(transation2Hash, transation3Hash)

    }

}

