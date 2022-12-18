package com.dbobjekts.integration.h2

import com.dbobjekts.Tuple9
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.statement.customsql.ResultTypes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class CustomSQLIntegrationTest {

    @Test
    fun `test select two columns from two tables`() {
        H2DB.setupDatabaseObjects()
        H2DB.newTransaction({
            //it.insert(AllTypes)
            val dob = LocalDate.of(1990, 12, 5)
            it.insert(Hobby).mandatoryColumns("chess", "chess").execute()
            val arthur = it.insert(Employee).mandatoryColumns("Arthur", 300.50, dob).execute()
            val jane = it.insert(Employee).mandatoryColumns("John", 300.50, LocalDate.of(1990, 12, 5)).hobbyId("chess").execute()
            val result: Tuple9<String, Float, BigDecimal, LocalDate, Long, Long?, Float, BigDecimal, Byte> =
                it.select("select id,name from core.employee where name = ?", "John")
                    .returning(ResultTypes.string().float().bigDecimal().date().long().longNil().float().bigDecimal().byte())
                    .first()
            /*assert(result.first == 300.50)
            assert(result.second == "Zuidhoek")*/
        })
    }


}
