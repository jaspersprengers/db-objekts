package com.dbobjekts.component

import com.dbobjekts.api.Tuple3
import com.dbobjekts.testdb.acme.library.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


class QueryOverviewComponentTest {

    @Test
    fun `Library component test`() {

        val tm = AcmeDB.transactionManager

        tm { transaction ->

            val orwell: Long =
                transaction.insert(Author).mandatoryColumns("George Orwell").bio("Pseudonym of Eric Blair (1903-1950)").execute()
            transaction.update(Author)
                .bio("(1903-1950) Pseudonym of Eric Blair. One of the most influential English authors and journalist of the early 20th century.")
                .where(Author.id.eq(orwell))
            val rowling: Long = transaction.insert(Author).mandatoryColumns("Joanne Rowling").execute()

            transaction.insert(Book).mandatoryColumns("ISBN-1984", "Nineteen-eighty Four", orwell, LocalDate.of(1948, 1, 1)).execute()
            transaction.insert(Book).mandatoryColumns("ISBN-WIGAN", "The Road to Wigan Pier", orwell, LocalDate.of(1940, 1, 1)).execute()
            transaction.insert(Book)
                .mandatoryColumns("ISBN-PHILOSOPHER", "Harry Potter and the Philosopher's Stone", rowling, LocalDate.of(2000, 1, 1))
                .execute()

            val item19 = transaction.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1980, 5, 5)).execute()

            val phil1 = transaction.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005, 5, 5)).execute()
            val phil2 = transaction.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005, 5, 5)).execute()

            val john = transaction.insert(Member).mandatoryColumns("John").execute()
            val sally = transaction.insert(Member).mandatoryColumns("Sally").execute()

            val rows: List<Tuple3<String, String, Long>> =
                transaction.select(Book.title, Author.name, Item.id).where(Book.published.lt(LocalDate.of(1980, 1, 1))).asList()

            transaction.insert(Loan).mandatoryColumns(memberId = sally, itemId = item19, dateLoaned = LocalDate.now()).execute()
            transaction.insert(Loan).mandatoryColumns(memberId = sally, itemId = phil1, dateLoaned = LocalDate.now()).execute()
            transaction.insert(Loan).mandatoryColumns(memberId = john, itemId = phil2, dateLoaned = LocalDate.now()).execute()

            assertThat(
                transaction.select(Book.title, Item.id, Item.dateAcquired).where(Book.isbn.eq("ISBN-WIGAN")).firstOrNull()
            ).isNull()

            val (title, item) = transaction.select(Book.title, Item.id.nullable).where(Book.isbn.eq("ISBN-WIGAN")).useOuterJoins().first()
            assertThat(item).isNull()

            val record = transaction.select<LoanRow, ItemRow, BookRow, AuthorRow, MemberRow>(Loan, Item, Book, Author, Member)
                .orderAsc(Loan.itemId, Loan.memberId)
                .where(Loan.itemId.eq(10).and(Loan.memberId).eq(11))
                .asList().first()
            assertThat(record.v2.isbn).isEqualTo("ISBN-1984")
            assertThat(record.v3.title).isEqualTo("Nineteen-eighty Four")
            assertThat(record.v4.name).isEqualTo("George Orwell")
            assertThat(record.v5.name).isEqualTo("Sally")

        }

    }

}




