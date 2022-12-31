package com.dbobjekts.component

import com.dbobjekts.testdb.acme.core.AddressRow
import com.dbobjekts.testdb.acme.core.EmployeeRow
import com.dbobjekts.testdb.acme.hr.HobbyRow
import org.junit.jupiter.api.Test
import java.time.LocalDate


class EntityBasedComponentTest {

    @Test
    fun `test entity-based round trip`() {
        AcmeDB.newTransaction { tr ->
            val hobby = HobbyRow("chess", "The game of champions")
            tr.insert(hobby)
            val employee = EmployeeRow(
                id = 0,
                name = "John",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 0,
                hobbyId = null
            )
            val empId = tr.save(employee)
            //val address = AddressRow()
        }

    }
}
