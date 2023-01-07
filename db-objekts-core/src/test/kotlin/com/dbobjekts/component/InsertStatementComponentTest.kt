package com.dbobjekts.component

import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class InsertStatementComponentTest {

    val e = Employee
    val h = Hobby

    companion object {
        val tm = AcmeDB.transactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `Insert employee record with null values where possible`() {
        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, LocalDate.of(1980, 1, 1)).execute()
            val jack: EmployeeRow = tr.select(e).where(e.id.eq(id)).first()
            assertThat(jack.married).isNull()
            assertThat(jack.children).isNull()
            assertThat(jack.hobbyId).isNull()
        }
    }

    @Test
    fun `Row-based insertion`() {
        tm { tr ->

            val row = EmployeeRow(
                name = "John",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = "c"
            )
            val johnsId = tr.insert(row)
            val addressId = tr.insert(AddressRow(street = "Zuidhoek", postcode = "5591HA", countryId = "nl"))
            tr.insert(EmployeeAddressRow(johnsId, addressId, AddressType.HOME))

            val (employee, address, addressType, country) =
                tr.select(Employee, Address, EmployeeAddress.kind, Country.name)
                    .where(Employee.id.eq(johnsId)).first()
            assertThat(employee.name).isEqualTo("John")
            assertThat(employee.salary).isEqualTo(300.5)
            assertThat(employee.married).isTrue()
            assertThat(employee.dateOfBirth).isEqualTo(LocalDate.of(1980, 3, 3))
            assertThat(employee.children).isEqualTo(2)
            assertThat(employee.hobbyId).isEqualTo("c")

            assertThat(address.countryId).isEqualTo("nl")
            assertThat(address.street).isEqualTo("Zuidhoek")
            assertThat(address.postcode).isEqualTo("5591HA")

            assertThat(addressType).isEqualTo(AddressType.HOME)
            assertThat(country).isEqualTo("Nederland")

        }
    }

}
