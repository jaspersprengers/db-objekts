package com.dbobjekts.component

import com.dbobjekts.api.Tuple3
import com.dbobjekts.api.Tuple4
import com.dbobjekts.testdb.AddressType
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.HobbyRow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate


class EntityBasedComponentTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup(){
            AcmeDB.deleteAllTables(AcmeDB.transactionManager)
        }
    }


    @Test
    fun `test entity-based insertion`() {
        AcmeDB.newTransaction { tr ->
            val hobby = HobbyRow("chess", "The game of champions")
            tr.insert(hobby)
         
            val empId = tr.insert(EmployeeRow(
                name = "John",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = "chess"
            ))

            tr.insert(CountryRow("nl", "Netherlands"))

            val addressId = tr.insert(AddressRow(street="Zuidhoek", postcode="5591HA", countryId = "nl"))
            tr.insert(EmployeeAddressRow(empId, addressId, AddressType.HOME))

            val (employee, address, addressType, country) =
                tr.select(Employee, Address, EmployeeAddress.kind, Country.name)
                    .where(Employee.id.eq(empId)).first()
            assertThat(employee.name).isEqualTo("John")
            assertThat(employee.salary).isEqualTo(300.5)
            assertThat(employee.married).isTrue()
            assertThat(employee.dateOfBirth).isEqualTo(LocalDate.of(1980, 3, 3))
            assertThat(employee.children).isEqualTo(2)
            assertThat(employee.hobbyId).isEqualTo("chess")

            assertThat(address.countryId).isEqualTo("nl")
            assertThat(address.street).isEqualTo("Zuidhoek")
            assertThat(address.postcode).isEqualTo("5591HA")

            assertThat(addressType).isEqualTo(AddressType.HOME)
            assertThat(country).isEqualTo("Netherlands")


                tr.select(Employee, Address, EmployeeAddress.kind, Country.name)
                    .where(Employee.id.eq(empId)).asList().forEach {
                        (emp, add, addType, country) ->
                        println("${emp.name}'s $addType address is ${add.street} ${add.postcode}, $country")
                    }



        }
    }

    @Test
    fun `update entity with non-pk row throws`(){
        AcmeDB.newTransaction { tr ->
            tr.insert(CountryRow("be", "Belgium"))
            val empId = tr.insert(EmployeeRow(
                name = "Jill",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = null
            ))
            val addressId = tr.insert(AddressRow(street="Sparrenlaan", postcode="5591HA", countryId = "be"))
            val ea = EmployeeAddressRow(empId, addressId, AddressType.HOME)
            tr.insert(ea)

            Assertions.assertThatThrownBy( { tr.update(ea.copy(kind = AddressType.WORK)) })
                .hasMessage("Sorry, but you cannot use entity-based update for table EmployeeAddress. There must be exactly one column marked as primary key.")
        }
    }

    @Test
    fun `test entity-based update`() {
        AcmeDB.newTransaction { tr ->
            val hobby = HobbyRow("go", "the game of Go")
            tr.insert(hobby)
            val empId = tr.insert(EmployeeRow(
                name = "John",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = "go"
            ))
            val retrieved: EmployeeRow = tr.select(Employee).where(Employee.id.eq(empId)).first()
            val updated = retrieved.copy(
                name = "Jake",
                salary = 320.5,
                married = false,
                dateOfBirth = LocalDate.of(1990, 3, 3),
                children = 1,
                hobbyId = null
            )
            tr.update(updated)
            val retrievedUpdated = tr.select(Employee).where(Employee.id.eq(empId)).first()
            assertThat(retrievedUpdated.name).isEqualTo("Jake")
            assertThat(retrievedUpdated.salary).isEqualTo(320.5)
            assertThat(retrievedUpdated.married).isFalse()
            assertThat(retrievedUpdated.dateOfBirth).isEqualTo(LocalDate.of(1990, 3, 3))
            assertThat(retrievedUpdated.children).isEqualTo(1)
            assertThat(retrievedUpdated.hobbyId).isNull()
        }
    }
}
