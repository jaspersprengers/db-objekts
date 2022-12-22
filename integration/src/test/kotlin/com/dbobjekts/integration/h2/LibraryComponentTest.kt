package com.dbobjekts.integration.h2

import com.dbobjekts.api.Tuple3
import com.dbobjekts.api.Tuple5
import com.dbobjekts.integration.h2.library.Author
import com.dbobjekts.integration.h2.library.Book
import com.dbobjekts.integration.h2.library.Item
import com.dbobjekts.integration.h2.library.Loan
import com.dbobjekts.integration.h2.library.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.ZonedDateTime


class LibraryComponentTest {

    @Test
    fun `integration test`() {

        H2DB.setupDatabaseObjects()
        H2DB.newTransaction {
            val orwell: Long = it.insert(Author).mandatoryColumns("George Orwell").execute()
            val rowling: Long = it.insert(Author).mandatoryColumns("Joanne Rowling").execute()

            it.update(Author)
                .bio("(1903-1950) Pseudonym of Eric Blair. One of the most influential English authors and journalist of the early 20th century.")
                .where(Author.id.eq(orwell))

            it.insert(Book).mandatoryColumns("ISBN-1984", "Nineteen-eighty Four", orwell, LocalDate.of(1948, 1, 1)).execute()
            it.insert(Book).mandatoryColumns("ISBN-WIGAN", "The Road to Wigan Pier", orwell, LocalDate.of(1940, 1, 1)).execute()

            val item19 = it.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1980, 5, 5)).execute()
            it.insert(Item).mandatoryColumns("ISBN-1984", LocalDate.of(1990, 5, 5)).execute()

            val ret = it.insert(Book)
                .mandatoryColumns("ISBN-PHILOSOPHER", "Harry Potter and the Philsopher's Stone", rowling, LocalDate.of(1999, 1, 1))
                .execute()

            it.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005, 5, 5)).execute()
            val phil1 = it.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005, 5, 5)).execute()
            val phil2 = it.insert(Item).mandatoryColumns("ISBN-PHILOSOPHER", LocalDate.of(2005, 5, 5)).execute()

            val john = it.insert(Member).mandatoryColumns("John Wayne").execute()
            val sally = it.insert(Member).mandatoryColumns("Sally Hawkins").execute()

            val rows: List<Tuple3<String, String, Long>> =
                it.select(Book.title, Author.name, Item.id).where(Book.published.lt(LocalDate.of(1980, 1, 1))).asList()
            rows.forEach { (title, name, id) ->
                println("$title by $name item $id")
            }

            it.insert(Loan).mandatoryColumns(memberId = sally, itemId = item19, dateLoaned = LocalDate.now()).execute()
            it.insert(Loan).mandatoryColumns(memberId = sally, itemId = phil1, dateLoaned = LocalDate.now()).execute()
            it.insert(Loan).mandatoryColumns(memberId = john, itemId = phil2, dateLoaned = LocalDate.now()).execute()

            assertThat(it.select(Book.title, Item.id, Item.dateAcquired).where(Book.isbn.eq("ISBN-WIGAN")).firstOrNull()).isNull()

            val bookAuthors: List<Tuple3<String, String, String?>> = it.select(Book.title, Author.name, Author.bio).asList()

            val itemsWigan = it.select(Book.title, Item.id.nullable).where(Book.isbn.eq("ISBN-WIGAN")).useOuterJoins().first()
            assertThat(itemsWigan.v2).isNull()


            val result: List<Tuple5<LocalDate, Long, String, String, String>> = it.select(Loan.dateLoaned, Item.id, Book.title, Author.name, Member.name).asList()
            /*.forEach { (dateLoaned, item, book, author, member) ->
                println("Item $item of $book by $author loaned to $member on $dateLoaned")
            }*/
            println(it.transactionExecutionLog().last().sql)

            it.select(Item.id, Item.isbn, Member.name, Loan.dateLoaned.nullable, Loan.dateReturned)
                .from(Loan.rightJoin(Item).innerJoin(Book).innerJoin(Member)).forEachRow { (item, isbn, member, loaned, returned) ->
                    println("$item $isbn $loaned by $member returned $returned")
                    true
                }


        }

    }

}




