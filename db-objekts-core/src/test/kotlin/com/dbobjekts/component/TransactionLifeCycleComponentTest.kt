package com.dbobjekts.component

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.fixture.DateFixtures
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.vendors.Vendors
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID
import javax.sql.DataSource


class TransactionLifeCycleComponentTest {

    object MariaCatalog : Catalog(Vendors.MARIADB.name)

    @Test
    fun `Vendor mismatch in db and catalog`() {
        Assertions.assertThatThrownBy {
            TransactionManager.builder().withCatalog(MariaCatalog).withDataSource(AcmeDB.dataSource)
                .build()
        }.hasMessage("You provided a Catalog implementation that is associated with vendor MARIADB, but you connected to a H2 DataSource.")
    }

    @Test
    fun `rollback when autocommit is false`() {
        insertAndThrow(noAutoCommit, true)
    }

    @Test
    fun `rollback when autocommit is true has no effect`() {
        insertAndThrow(autoCommit, false)
    }

    @Test
    fun `manual rollback`() {
        val userId = UUID.randomUUID().toString()
        val id = noAutoCommit { tr ->
            tr.insert(Employee).mandatoryColumns(userId, 3000.0, DateFixtures.date).execute()
            tr.rollback()
            tr.select(Employee.id).where(Employee.name.eq(userId)).firstOrNull()
        }
        Assertions.assertThat(id).isNull()

    }

    private fun insertAndThrow(manager: TransactionManager, expectRollback: Boolean) {
        val userId = UUID.randomUUID().toString()
        Assertions.assertThatThrownBy {
            manager { tr ->
                tr.insert(Employee).mandatoryColumns(userId, 3000.0, DateFixtures.date).execute()
                if (true)
                    throw RuntimeException("throwing exception")
            }
        }.hasMessage("throwing exception")


        val id = manager { tr ->
            tr.select(Employee.id).where(Employee.name.eq(userId)).firstOrNull()
        }
        if (expectRollback)
            Assertions.assertThat(id).isNull()
        else
            Assertions.assertThat(id).isNotNull()
    }

    companion object {

        val autoCommit = newTransactionManager(true)
        val noAutoCommit = newTransactionManager(false)

        init {
            AcmeDB.createExampleCatalog(autoCommit)
        }

        fun newTransactionManager(autoCommit: Boolean): TransactionManager {
            val tm = TransactionManager.builder()
                .withCatalog(CatalogDefinition)
                .withDataSource(AcmeDB.dataSource)
                .withCustomConnectionProvider { ds: DataSource ->
                    val conn = ds.connection
                    conn.autoCommit = autoCommit
                    conn
                }
                .build()
            return tm
        }

    }

}
