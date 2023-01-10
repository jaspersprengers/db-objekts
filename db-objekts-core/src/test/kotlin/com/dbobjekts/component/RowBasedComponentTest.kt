package com.dbobjekts.component

import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.testdb.acme.hr.HobbyRow
import com.dbobjekts.testdb.acme.library.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class RowBasedComponentTest {

    val e = Employee
    val h = Hobby
    val b = Book

    val emp = EmployeeRow(
        name = "Jill",
        salary = 300.5,
        married = true,
        dateOfBirth = LocalDate.of(1980, 3, 3),
        children = 2,
        hobbyId = null
    )

    companion object {
        val tm = AcmeDB.transactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `save Employee, auto generated PK`() {

        tm {
            val id = it.save(emp)
            val retrieved = it.select(e).where(e.id.eq(id)).first()
            // save row with updated value
            it.save(retrieved.copy(id = id, children = 3))
            val updated = it.select(e).where(e.id.eq(id)).first()
            assertThat(updated.children).isEqualTo(3)
        }
    }

    @Test
    fun `save book, non-auto generated PK`() {
        val author = AuthorRow(name = "Stephen King", bio = "")
        val date = LocalDate.of(1980, 1, 1)
        tm {
            val stephen = it.save(author)
            val book = BookRow(isbn = "ISBN", title = "The Shining", authorId = stephen, published = LocalDate.of(1978, 1, 1))
            it.save(book)
            val retrieved = it.select(b).where(b.isbn.eq("ISBN")).first()
            // save row with updated value
            it.save(retrieved.copy(published = date))
            val updated = it.select(b).where(b.isbn.eq("ISBN")).first()
            assertThat(updated.published).isEqualTo(date)

        /*    val review = BookReviewRow("ISBN", "Scary!")
            it.save(BookReviewRow("ISBN", "Scary!"))
            assertThat(it.select(BookReview.review).where(b.isbn.eq("ISBN")).first()).isEqualTo("Scary!")
            assertThatThrownBy {it.update(review)}.hasMessage("Sorry, but you cannot use row-based updates for table BookReview. At least one column must be marked as primary key.")
*/
        }
    }

    @Test
    fun `save Employee-Address, (composite key)`() {
        tm {
            val joan = it.save(emp.copy(name = "Joan"))
            val address = it.save(AddressRow(street = "Kerkstraat", postcode = "5590AA", countryId = "nl"))
            val ea = EmployeeAddressRow(joan, address, AddressType.HOME)
            it.save(ea)
            assertThat(it.select(EmployeeAddress.kind).where(e.name.eq("Joan")).first()).isEqualTo(AddressType.HOME)
            it.save(ea.copy(kind = AddressType.WORK))
            assertThat(it.select(EmployeeAddress.kind).where(e.name.eq("Joan")).first()).isEqualTo(AddressType.WORK)
        }
    }

}
