package com.dbobjekts.integration

import com.dbobjekts.Tuple9
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.statement.customsql.ResultTypes
import org.junit.jupiter.api.*
import java.math.BigDecimal
import java.time.LocalDate

class CustomSQLIntegrationTest {


    companion object {

        val e = Employee
        val a = Address
        val h = Hobby
        var jane: Long = 0
        var arthur: Long = 0

        @BeforeAll
        @JvmStatic
        fun setup() {
            H2DB.setupDatabaseObjects()
            H2DB.newTransaction {
                val dob = LocalDate.of(1990, 12, 5)
                it.insert(Hobby).mandatoryColumns("chess", "chess").execute()
                arthur = it.insert(Employee).mandatoryColumns("Arthur", 300.50, dob).execute()
                jane = it.insert(Employee).mandatoryColumns("Jane", 300.50, LocalDate.of(1990, 12, 5)).hobbyId("chess").execute()

            }

        }
    }


    @Test
    fun `test select two columns from two tables`() {
        H2DB.newTransaction({
            val result: Tuple9<String, Float, BigDecimal, LocalDate, Long, Long?, Float, BigDecimal, Byte> =
                it.select("select id,name from employee where name = ?", "John")
                    .returning(ResultTypes.string().float().bigDecimal().date().long().longNil().float().bigDecimal().byte())
                    .first()
            /*assert(result.first == 300.50)
            assert(result.second == "Zuidhoek")*/
        })
    }


}
